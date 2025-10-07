package co.com.bb.kata.model.validatetoken;

import lombok.*;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ValidateToken {
    private boolean valid;
    private String subject;
    private String role;
    private Long userId;
}