package app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@RequiredArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    private final String type;
    private final Integer id;
}
