package com.av1.av1.Modelo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class documentoDTO {
    private Long id;
    
    @NotBlank(message = "campo obrigatorio")
    private String tipo;
    
    @Size(min = 11, max = 11, message = "numero deve conter 11 caracteres")
    private String numero;
}