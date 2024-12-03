package org.example.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.domain.entity.user.UserRole;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadDto {
    UUID id;
    String name;
    String password;
    String email;
    UserRole role;
    LocalDateTime createdDate;
    LocalDateTime updatedDate;
}