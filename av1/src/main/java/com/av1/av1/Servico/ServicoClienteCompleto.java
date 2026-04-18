package com.av1.av1.Servico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.av1.av1.Modelo.DTO.clienteDTO;
import com.av1.av1.Modelo.DTO.documentoDTO;
import com.av1.av1.Modelo.DTO.telefoneDTO;
import com.av1.av1.Modelo.modeloCliente;
import com.av1.av1.Modelo.modeloDocumento;
import com.av1.av1.Modelo.modeloEndereco;
import com.av1.av1.Modelo.modeloTelefone;
import com.av1.av1.Repositorio.RepositorioCarros;
import com.av1.av1.Servico.Atualiaza.ClienteAtualizador;

@Service
public class ServicoClienteCompleto {
    
    @Autowired
    private RepositorioCarros repositorioCliente;
    
    public modeloCliente cadastrarCompleto(clienteDTO dto) {
        modeloCliente cliente = new modeloCliente();
        
        cliente.setNome(dto.getNome());
        cliente.setSobrenome(dto.getSobrenome());
        cliente.setCPF(dto.getCPF());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefone(dto.getTelefone());
        cliente.setDataNascimento(dto.getDataNascimento());
        cliente.setDataCadastro(new Date());
        cliente.setNomeSocial(dto.getNomeSocial());
        
        if (dto.getEndereco() != null) {
            modeloEndereco endereco = new modeloEndereco();
            endereco.setRua(dto.getEndereco().getRua());
            endereco.setNumero(dto.getEndereco().getNumero());
            endereco.setCidade(dto.getEndereco().getCidade());
            endereco.setEstado(dto.getEndereco().getEstado());
            endereco.setBairro(dto.getEndereco().getBairro());
            endereco.setCodigoPostal(dto.getEndereco().getCodigoPostal());
            endereco.setInformacoesAdicionais(dto.getEndereco().getInformacoesAdicionais());
            cliente.setEndereco(endereco);
        }
        
        if (dto.getDocumentos() != null) {
            List<modeloDocumento> documentos = new ArrayList<>();
            for (documentoDTO docDTO : dto.getDocumentos()) {
                modeloDocumento documento = new modeloDocumento();
                documento.setTipo(docDTO.getTipo());
                documento.setNumero(docDTO.getNumero());
                documentos.add(documento);
            }
            cliente.setDocumentos(documentos);
        }
        
        if (dto.getTelefones() != null) {
            List<modeloTelefone> telefones = new ArrayList<>();
            for (telefoneDTO telDTO : dto.getTelefones()) {
                modeloTelefone telefone = new modeloTelefone();
                telefone.setDdd(telDTO.getDdd());
                telefone.setNumero(telDTO.getNumero());
                telefones.add(telefone);
            }
            cliente.setTelefones(telefones);
        }
        
        return repositorioCliente.save(cliente);
    }
    
    public modeloCliente atualizarCompleto(Long id, clienteDTO dto) {
        Optional<modeloCliente> clienteOpt = repositorioCliente.findById(id);
        if (!clienteOpt.isPresent()) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        
        modeloCliente cliente = clienteOpt.get();
        modeloCliente atualizacao = new modeloCliente();
        
        atualizacao.setNome(dto.getNome());
        atualizacao.setSobrenome(dto.getSobrenome());
        atualizacao.setNomeSocial(dto.getNomeSocial());
        atualizacao.setDataNascimento(dto.getDataNascimento());
        atualizacao.setCPF(dto.getCPF());
        atualizacao.setEmail(dto.getEmail());
        atualizacao.setTelefone(dto.getTelefone());
        
        if (dto.getEndereco() != null) {
            modeloEndereco endereco = new modeloEndereco();
            if (dto.getEndereco().getId() != null) {
                endereco.setId(dto.getEndereco().getId());
            }
            endereco.setRua(dto.getEndereco().getRua());
            endereco.setNumero(dto.getEndereco().getNumero());
            endereco.setCidade(dto.getEndereco().getCidade());
            endereco.setEstado(dto.getEndereco().getEstado());
            endereco.setBairro(dto.getEndereco().getBairro());
            endereco.setCodigoPostal(dto.getEndereco().getCodigoPostal());
            endereco.setInformacoesAdicionais(dto.getEndereco().getInformacoesAdicionais());
            atualizacao.setEndereco(endereco);
        }
        
        if (dto.getDocumentos() != null) {
            List<modeloDocumento> documentos = new ArrayList<>();
            for (documentoDTO docDTO : dto.getDocumentos()) {
                modeloDocumento documento = new modeloDocumento();
                if (docDTO.getId() != null) {
                    documento.setId(docDTO.getId());
                }
                documento.setTipo(docDTO.getTipo());
                documento.setNumero(docDTO.getNumero());
                documentos.add(documento);
            }
            atualizacao.setDocumentos(documentos);
        }
        
        if (dto.getTelefones() != null) {
            List<modeloTelefone> telefones = new ArrayList<>();
            for (telefoneDTO telDTO : dto.getTelefones()) {
                modeloTelefone telefone = new modeloTelefone();
                if (telDTO.getId() != null) {
                    telefone.setId(telDTO.getId());
                }
                telefone.setDdd(telDTO.getDdd());
                telefone.setNumero(telDTO.getNumero());
                telefones.add(telefone);
            }
            atualizacao.setTelefones(telefones);
        }
        
        ClienteAtualizador atualizador = new ClienteAtualizador();
        atualizador.atualizar(cliente, atualizacao);
        
        return repositorioCliente.save(cliente);
    }
    
    public modeloCliente buscarPorId(Long id) {
        return repositorioCliente.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
    }
    
    public List<modeloCliente> buscarTodos() {
        return repositorioCliente.findAll();
    }
}