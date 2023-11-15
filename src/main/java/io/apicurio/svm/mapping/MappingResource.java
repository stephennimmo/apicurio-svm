package io.apicurio.svm.mapping;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.Objects;

@Path("/mappings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "mapping", description = "Mapping Operations")
public class MappingResource {

    @GET
    @APIResponse(
            responseCode = "200",
            description = "Get All Mappings",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(type = SchemaType.ARRAY, implementation = Mapping.class)
            )
    )
    public List<Mapping> get(@QueryParam("sourceSystemId") Integer sourceSystemId, @QueryParam("targetSystemId") Integer targetSystemId) {
        if (Objects.nonNull(sourceSystemId) && Objects.nonNull(targetSystemId)) {
            return this.findBySourceSystemIdAndTargetSystemId(sourceSystemId, targetSystemId);
        }
        return Mapping.listAll();
    }

    private List<Mapping> findBySourceSystemIdAndTargetSystemId(int sourceSystemId, int targetSystemId) {
        return Mapping.list("sourceSystem.systemId = ?1 and targetSystem.systemId = ?2", sourceSystemId, targetSystemId);
    }

}
