package io.apicurio.svm;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record ErrorResponse(@JsonInclude(JsonInclude.Include.NON_NULL) String errorId, List<ErrorMessage> errors) {

    public ErrorResponse(String errorId, ErrorMessage errorMessage) {
        this(errorId, List.of(errorMessage));
    }

    public ErrorResponse(List<ErrorMessage> errors) {
        this(null, errors);
    }

    public record ErrorMessage(@JsonInclude(JsonInclude.Include.NON_NULL) String path, String message) {

        public ErrorMessage(String message) {
            this(null, message);
        }

    }

}
