package com.av1.av1.Servico.Atualiaza;

import java.util.List;

import com.av1.av1.Modelo.modeloTelefone;
import com.av1.av1.Servico.StringVerificadorNulo;

public class TelefoneAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(modeloTelefone telefone, modeloTelefone atualizacao) {
        if (atualizacao != null) {
            if (!verificador.verificar(atualizacao.getDdd())) {
                telefone.setDdd(atualizacao.getDdd());
            }
            if (!verificador.verificar(atualizacao.getNumero())) {
                telefone.setNumero(atualizacao.getNumero());
            }
        }
    }

    public void atualizar(List<modeloTelefone> telefones, List<modeloTelefone> atualizacoes) {
        if (atualizacoes != null) {
            for (modeloTelefone atualizacao : atualizacoes) {
                for (modeloTelefone telefone : telefones) {
                    if (atualizacao.getId() != null && atualizacao.getId().equals(telefone.getId())) {
                        atualizar(telefone, atualizacao);
                        break;
                    }
                }
            }
        }
    }
}