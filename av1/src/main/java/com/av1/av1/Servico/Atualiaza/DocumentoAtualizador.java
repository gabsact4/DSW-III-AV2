package com.av1.av1.Servico.Atualiaza;
import java.util.List;

import com.av1.av1.Modelo.modeloDocumento;
import com.av1.av1.Servico.StringVerificadorNulo;

public class DocumentoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(modeloDocumento documento, modeloDocumento atualizacao) {
        if (atualizacao != null) {
            if (!verificador.verificar(atualizacao.getTipo())) {
                documento.setTipo(atualizacao.getTipo());
            }
            if (!verificador.verificar(atualizacao.getNumero())) {
                documento.setNumero(atualizacao.getNumero());
            }
        }
    }

    public void atualizar(List<modeloDocumento> documentos, List<modeloDocumento> atualizacoes) {
        if (atualizacoes != null) {
            for (modeloDocumento atualizacao : atualizacoes) {
                for (modeloDocumento documento : documentos) {
                    if (atualizacao.getId() != null && atualizacao.getId().equals(documento.getId())) {
                        atualizar(documento, atualizacao);
                    }
                }
            }
        }
    }
}