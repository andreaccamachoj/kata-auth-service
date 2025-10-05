package co.com.bb.kata.model.login;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Login {
    private String email;
    private String password;
}