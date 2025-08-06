package com.blogging.subtxt.dto.request;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email must not be blank")
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "Username must not be blank")
    @Pattern(
            regexp = "^[a-z0-9_.-]{3,20}$",
            message = "Username must be 3–20 characters, lowercase, and can only contain letters, digits, underscores (_), dots (.), or hyphens (-)"
    )
    @NotBlank(message = "Username must not be blank")
    private String username;

}

