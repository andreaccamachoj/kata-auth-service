package co.com.bb.kata.model.login;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserLogged {
    private String token;
    private String userName;
    private String email;
    private String role;
    private Long userId;
}
