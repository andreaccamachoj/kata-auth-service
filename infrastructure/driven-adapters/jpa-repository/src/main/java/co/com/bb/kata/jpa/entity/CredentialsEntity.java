package co.com.bb.kata.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credentials", schema = "auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CredentialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_credentials")
    private Long idCredentials;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_credentials_user"))
    private UserAccountEntity userAccountEntity;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;
}