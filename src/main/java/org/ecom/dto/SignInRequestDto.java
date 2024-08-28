package org.ecom.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder

public class SignInRequestDto {
    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Password is required!")
    private String password;
    
    private long id;
    
    private String username;
    
    private String token;
    private String type;
    private List<String> roles; 
    
   

}