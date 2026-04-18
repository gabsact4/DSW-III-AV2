package com.av1.av1.Servico.Atualiaza;
import java.util.ArrayList;
import java.util.List;

import com.av1.av1.Modelo.modeloCliente;
import com.av1.av1.Modelo.modeloDocumento;
import com.av1.av1.Modelo.modeloTelefone;
import com.av1.av1.Servico.StringVerificadorNulo;

public class ClienteAtualizador {
    private StringVerificadorNulo verificador = new StringVerificadorNulo();
    private EnderecoAtualizador enderecoAtualizador = new EnderecoAtualizador();
    private DocumentoAtualizador documentoAtualizador = new DocumentoAtualizador();
    private TelefoneAtualizador telefoneAtualizador = new TelefoneAtualizador();

    private void atualizarDados(modeloCliente cliente, modeloCliente atualizacao) {
        if (!verificador.verificar(atualizacao.getNome())) {
            cliente.setNome(atualizacao.getNome());
        }
        if (!verificador.verificar(atualizacao.getSobrenome())) {
            cliente.setSobrenome(atualizacao.getSobrenome());
        }
        if (!verificador.verificar(atualizacao.getNomeSocial())) {
            cliente.setNomeSocial(atualizacao.getNomeSocial());
        }
        if (atualizacao.getDataCadastro() != null) {
            cliente.setDataCadastro(atualizacao.getDataCadastro());
        }
        if (atualizacao.getDataNascimento() != null) {
            cliente.setDataNascimento(atualizacao.getDataNascimento());
        }
        if (!verificador.verificar(atualizacao.getCPF())) {
            cliente.setCPF(atualizacao.getCPF());
        }
        if (!verificador.verificar(atualizacao.getEmail())) {
            cliente.setEmail(atualizacao.getEmail());
        }
        if (!verificador.verificar(atualizacao.getTelefone())) {
            cliente.setTelefone(atualizacao.getTelefone());
        }
    }

    private void atualizarEndereco(modeloCliente cliente, modeloCliente atualizacao) {
        if (atualizacao.getEndereco() != null) {
            if (cliente.getEndereco() != null) {
                enderecoAtualizador.atualizar(cliente.getEndereco(), atualizacao.getEndereco());
            } else {
                cliente.setEndereco(atualizacao.getEndereco());
            }
        }
    }

    private void atualizarDocumentos(modeloCliente cliente, modeloCliente atualizacao) {
        if (atualizacao.getDocumentos() != null) {
            List<modeloDocumento> documentosParaRemover = new ArrayList<>();
            for (modeloDocumento docExistente : cliente.getDocumentos()) {
                boolean encontrado = false;
                for (modeloDocumento docAtualizacao : atualizacao.getDocumentos()) {
                    if (docExistente.getId() != null && docExistente.getId().equals(docAtualizacao.getId())) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    documentosParaRemover.add(docExistente);
                }
            }
            cliente.getDocumentos().removeAll(documentosParaRemover);
            
            for (modeloDocumento docAtualizacao : atualizacao.getDocumentos()) {
                boolean encontrado = false;
                for (modeloDocumento docExistente : cliente.getDocumentos()) {
                    if (docExistente.getId() != null && docExistente.getId().equals(docAtualizacao.getId())) {
                        documentoAtualizador.atualizar(docExistente, docAtualizacao);
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado && docAtualizacao.getId() == null) {
                    cliente.getDocumentos().add(docAtualizacao);
                }
            }
        }
    }

    private void atualizarTelefones(modeloCliente cliente, modeloCliente atualizacao) {
        if (atualizacao.getTelefones() != null) {
            List<modeloTelefone> telefonesParaRemover = new ArrayList<>();
            for (modeloTelefone telExistente : cliente.getTelefones()) {
                boolean encontrado = false;
                for (modeloTelefone telAtualizacao : atualizacao.getTelefones()) {
                    if (telExistente.getId() != null && telExistente.getId().equals(telAtualizacao.getId())) {
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    telefonesParaRemover.add(telExistente);
                }
            }
            cliente.getTelefones().removeAll(telefonesParaRemover);
            
            for (modeloTelefone telAtualizacao : atualizacao.getTelefones()) {
                boolean encontrado = false;
                for (modeloTelefone telExistente : cliente.getTelefones()) {
                    if (telExistente.getId() != null && telExistente.getId().equals(telAtualizacao.getId())) {
                        telefoneAtualizador.atualizar(telExistente, telAtualizacao);
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado && telAtualizacao.getId() == null) {
                    cliente.getTelefones().add(telAtualizacao);
                }
            }
        }
    }

    public void atualizar(modeloCliente cliente, modeloCliente atualizacao) {
        atualizarDados(cliente, atualizacao);
        atualizarEndereco(cliente, atualizacao);
        atualizarDocumentos(cliente, atualizacao);
        atualizarTelefones(cliente, atualizacao);
    }
}