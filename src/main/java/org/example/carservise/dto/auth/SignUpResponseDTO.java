package org.example.carservise.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SignUpResponseDTO {

    private Long id;

    private String username;

    private LocalDateTime registeredAt;
}