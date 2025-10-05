package co.com.bb.kata.api.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidateTokenResponse {
    private boolean valid;
    private String subject;
    private String role;
}