package team.alabs.wso3.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ExceptionDto {

    private String message;
    private String traceId;
    private Map<String, String> debugInfo;
}