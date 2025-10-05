package co.com.bb.kata.model.registeruser;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RegisterUser {
    private String firstName;
    private String lastName;
    private String email;
    private String identityDocument;
    private LocalDate birthDate;
    private String password;
    private String phone;
    private Long roleId;
}
