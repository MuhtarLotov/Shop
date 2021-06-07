package team.alabs.shop.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.alabs.shop.model.ExceptionDto;

import java.util.Collections;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ExceptionDto> badRequestHandleException(Exception e) {
        String traceId = UUID.randomUUID().toString();
        log.error(String.format("Exception id: %s caught. ", traceId), e);

        ExceptionDto exception = ExceptionDto.builder()
                .message(e.getMessage())
                .traceId(traceId)
                .debugInfo(Collections.singletonMap("stackTrace", ExceptionUtils.getStackTrace(e)))
                .build();
        return ResponseEntity.badRequest().body(exception);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ExceptionDto> ForbiddenHandleException(Exception e) {
        String traceId = UUID.randomUUID().toString();
        log.error(String.format("Exception id: %s caught. ", traceId), e);

        ExceptionDto exception = ExceptionDto.builder()
                .message(e.getMessage())
                .traceId(traceId)
                .debugInfo(Collections.singletonMap("stackTrace", ExceptionUtils.getStackTrace(e)))
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exception);
    }


}