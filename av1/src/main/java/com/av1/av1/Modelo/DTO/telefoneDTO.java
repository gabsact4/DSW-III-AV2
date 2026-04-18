package com.av1.av1.Modelo.DTO;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class telefoneDTO {
    private Long id;
    
    @Size(min = 2, max = 3, message = "DDD deve ter 2-3 digitos")
    private String ddd;
    
    @Size(min = 8, max = 9, message = "numero deve ter 8-9 digitos")
    private String numero;
}