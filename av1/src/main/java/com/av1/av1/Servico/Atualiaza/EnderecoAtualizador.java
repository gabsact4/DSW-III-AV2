package com.av1.av1.Servico.Atualiaza;

import com.av1.av1.Modelo.modeloEndereco;
import com.av1.av1.Servico.StringVerificadorNulo;;

public class EnderecoAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();

    public void atualizar(modeloEndereco endereco, modeloEndereco atualizacao) {
        if (atualizacao != null) {
            if (!verificador.verificar(atualizacao.getEstado())) {
                endereco.setEstado(atualizacao.getEstado());
            }
            if (!verificador.verificar(atualizacao.getCidade())) {
                endereco.setCidade(atualizacao.getCidade());
            }
            if (!verificador.verificar(atualizacao.getBairro())) {
                endereco.setBairro(atualizacao.getBairro());
            }
            if (!verificador.verificar(atualizacao.getRua())) {
                endereco.setRua(atualizacao.getRua());
            }
            if (!verificador.verificar(atualizacao.getNumero())) {
                endereco.setNumero(atualizacao.getNumero());
            }
            if (!verificador.verificar(atualizacao.getCodigoPostal())) {
                endereco.setCodigoPostal(atualizacao.getCodigoPostal());
            }
            if (!verificador.verificar(atualizacao.getInformacoesAdicionais())) {
                endereco.setInformacoesAdicionais(atualizacao.getInformacoesAdicionais());
            }
        }
    }
}