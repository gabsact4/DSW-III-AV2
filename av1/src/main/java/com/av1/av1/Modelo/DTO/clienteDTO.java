package com.av1.av1.Modelo.DTO;

import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class clienteDTO {
    
    @NotBlank(message = "este campo nao pode vir vazio")
    private String nome;
    
    @NotBlank(message = "este campo nao pode vir vazio")
    private String sobrenome;
    
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String CPF;
    
    @Email(message = "email invalido")
    private String Email;
    
    @Size(min = 11, max = 11, message = "Telefone deve ter 11 caracteres")
    private String Telefone;
    
    private Date dataNascimento;
    private Date dataCadastro;
    private String nomeSocial;
    
    private List<documentoDTO> documentos;
    private enderecoDTO endereco;
    private List<telefoneDTO> telefones;
}