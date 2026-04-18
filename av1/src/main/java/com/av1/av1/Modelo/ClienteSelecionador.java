package com.av1.av1.Modelo;

import java.util.List;

import org.springframework.stereotype.Component;
 
@Component
public class ClienteSelecionador {
    public modeloCliente selecionar(List<modeloCliente> clientes, long id) {
        modeloCliente selecionado = null;
        for (modeloCliente cliente : clientes) {
            if (cliente.getId() == id) {
                selecionado = cliente;
                break;
            }
        }
        return selecionado;
    }
}