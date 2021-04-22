package app.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ErrorResponse {
    private String message;
    private Map<String, String> errors;
    private String instance;
}
