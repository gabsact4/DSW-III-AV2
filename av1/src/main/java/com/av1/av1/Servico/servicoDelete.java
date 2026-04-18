package com.av1.av1.Servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.av1.av1.Repositorio.RepositorioCarros;

@Service
public class servicoDelete {
    @Autowired
    private RepositorioCarros repositorio;

    public void deletar(Long id) {
        repositorio.deleteById(id);
    }
}