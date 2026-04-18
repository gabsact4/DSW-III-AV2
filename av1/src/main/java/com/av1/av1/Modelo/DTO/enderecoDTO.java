package com.av1.av1.Modelo.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class enderecoDTO {
    private Long id;
    private String estado;
    
    @NotBlank(message = "campo obrigatorio")
    private String cidade;
    
    private String bairro;
    
    @NotBlank(message = "campo obrigatorio")
    private String rua;
    
    @NotBlank(message = "campo obrigatorio")
    private String numero;
    
    private String codigoPostal;
    private String informacoesAdicionais;
}