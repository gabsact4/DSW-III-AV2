package com.av1.av1.Modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class modeloCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String nome;
    
    @Column
    private String sobrenome;
    
    @Column
    private String nomeSocial;
    
    @Column
    private Date dataNascimento;
    
    @Column
    private Date dataCadastro;
    
    @Column(unique = true)
    private String CPF;
    
    @Column(unique = true)
    private String Email;
    
    @Column
    private String Telefone;
    
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<modeloDocumento> documentos = new ArrayList<>();
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private modeloEndereco endereco;
    
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<modeloTelefone> telefones = new ArrayList<>();
}