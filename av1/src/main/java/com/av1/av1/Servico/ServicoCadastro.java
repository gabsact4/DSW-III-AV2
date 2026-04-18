package com.av1.av1.Servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.av1.av1.Modelo.modeloCliente;
import com.av1.av1.Repositorio.RepositorioCarros;

@Service
public class ServicoCadastro {
    @Autowired
    private RepositorioCarros repositorio;

    public modeloCliente cadastrar(modeloCliente cliente) {
        return repositorio.save(cliente);
    }
}