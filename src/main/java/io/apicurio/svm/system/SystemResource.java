package io.apicurio.svm.system;

import io.apicurio.svm.Role;
import io.apicurio.svm.exception.ErrorResponse;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@Path("/systems")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "system", description = "System Operations")
@Authenticated
public class SystemResource {

    @GET
    @APIResponse(
            responseCode = "200",
            description = "Get All Systems",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = System.class)
            )
    )
    @RolesAllowed({Role.ROLE_SVM_ADMIN, Role.ROLE_SVM_SYSTEM_READ})
    public List<System> get() {
        return System.listAll();
    }

    @GET
    @Path("/{systemId}")
    @APIResponse(
            responseCode = "200",
            description = "Get System by systemId",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = System.class)
            )
    )
    @APIResponse(
            responseCode = "404",
            description = "System does not exist for systemId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @RolesAllowed({Role.ROLE_SVM_ADMIN, Role.ROLE_SVM_SYSTEM_READ})
    public Response getById(@Parameter(name = "systemId", required = true) @PathParam("systemId") Integer systemId) {
        return Response.ok(System.findByIdOptional(systemId).orElseThrow(RuntimeException::new)).build();
    }

    @POST
    @APIResponse(
            responseCode = "201",
            description = "System Created",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = System.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid System",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "System already exists for systemId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Transactional
    @RolesAllowed({Role.ROLE_SVM_ADMIN, Role.ROLE_SVM_SYSTEM_WRITE})
    public Response post(@NotNull @Valid System system, @Context UriInfo uriInfo) {
        if (system.systemId != null) {
            ErrorResponse errorResponse = new ErrorResponse(null, new ErrorResponse.ErrorMessage("post.system.systemId", "POST System generates the systemId"));
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        System.persist(system);
        URI uri = uriInfo.getAbsolutePathBuilder().path(system.systemId.toString()).build();
        return Response.created(uri).entity(system).build();
    }

    @PUT
    @Path("/{systemId}")
    @APIResponse(
            responseCode = "204",
            description = "System updated",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.OBJECT, implementation = System.class)
            )
    )
    @APIResponse(
            responseCode = "400",
            description = "Invalid System",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "System object does not have systemId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "400",
            description = "Path variable systemId does not match System.systemId",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @APIResponse(
            responseCode = "404",
            description = "No System found for systemId provided",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    @Transactional
    @RolesAllowed({Role.ROLE_SVM_ADMIN, Role.ROLE_SVM_SYSTEM_WRITE})
    public Response put(@Parameter(name = "systemId", required = true) @PathParam("systemId") Integer systemId, @NotNull @Valid System system) {
        if (!Objects.equals(systemId, system.systemId)) {
            ErrorResponse errorResponse = new ErrorResponse(null, new ErrorResponse.ErrorMessage("post.system.systemId", "Path variable customerId does not match Customer.customerId"));
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }
        System existing = (System) System.findByIdOptional(systemId).orElseThrow(() -> new BadRequestException("No System found for systemId provided"));
        existing.name = system.name;
        System.persist(existing);
        return Response.status(Response.Status.NO_CONTENT).build();
    }

}
