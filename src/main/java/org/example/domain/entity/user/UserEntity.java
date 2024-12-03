package org.example.domain.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.example.domain.entity.BaseEntity;
import org.springframework.lang.NonNull;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
@Builder
public class UserEntity extends BaseEntity {

    private String name;

    @Column(nullable = false)
    @NotBlank(message = "password cannot be empty!")
    @NonNull
    private String password;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "email cannot be empty!")
    @NonNull
    @Email(message = "enter valid email address!")
    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
