package co.com.bb.kata.model.credential;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Credential {
    private Long idCredentials;
    private Long userAccountId;
    private String passwordHash;
}