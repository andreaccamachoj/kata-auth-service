package co.com.bb.kata.model.tokensession;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TokenSession {

    private Long idTokenSession;
    private Long userAccountEntityId;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Boolean isActive;

}