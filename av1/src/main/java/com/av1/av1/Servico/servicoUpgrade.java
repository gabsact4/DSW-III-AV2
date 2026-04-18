package com.av1.av1.Servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.av1.av1.Modelo.modeloCliente;
import com.av1.av1.Repositorio.RepositorioCarros;

@Service
public class servicoUpgrade {
    
    @Autowired
    private RepositorioCarros repositorio;

    @GetMapping("cadastro/cliente")
    public void Cadastrar(modeloCliente cliente){
        repositorio.save(cliente);
    }
    
}
