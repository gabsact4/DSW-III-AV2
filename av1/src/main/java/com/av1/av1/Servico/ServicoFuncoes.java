package com.av1.av1.Servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.av1.av1.Modelo.modeloCliente;
import com.av1.av1.Repositorio.RepositorioCarros;

@Service
public class ServicoFuncoes {
    @Autowired
    private RepositorioCarros repositorio;

    public modeloCliente atualizar(modeloCliente cliente) {
        if (repositorio.existsById(cliente.getId())) {
            return repositorio.save(cliente);
        } else {
            throw new RuntimeException("ID não encontrado");
        }
    }
}