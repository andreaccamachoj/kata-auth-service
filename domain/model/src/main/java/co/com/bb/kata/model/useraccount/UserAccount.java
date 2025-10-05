package co.com.bb.kata.model.useraccount;

import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserAccount {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String identityDocument;
    private LocalDate birthDate;
    private String phone;
    private Long roleId;

//    private Role role;
//
//    private Credentials credentials;
//
//    private List<TokenSession> tokenSessions;
}
