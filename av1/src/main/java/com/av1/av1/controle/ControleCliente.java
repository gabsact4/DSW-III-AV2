package com.av1.av1.controle;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.av1.av1.Modelo.ClienteSelecionador;
import com.av1.av1.Modelo.DTO.clienteDTO;
import com.av1.av1.Modelo.modeloCliente;
import com.av1.av1.Repositorio.RepositorioCarros;
import com.av1.av1.Servico.Atualiaza.ClienteAtualizador;
import com.av1.av1.Servico.ServicoClienteCompleto;

@RestController
@RequestMapping("/cliente")
public class ControleCliente {
    
    @Autowired
    private RepositorioCarros repositorio;
    
    
    @Autowired
    private ServicoClienteCompleto servicoCompleto;
    
    @Autowired
    private ClienteSelecionador selecionador;
    
    private EntityModel<modeloCliente> adicionarLinks(modeloCliente cliente) {
        EntityModel<modeloCliente> resource = EntityModel.of(cliente);
        
        resource.add(linkTo(methodOn(ControleCliente.class).obterCliente(cliente.getId())).withSelfRel());
        resource.add(linkTo(methodOn(ControleCliente.class).atualizarCliente(cliente.getId(), null)).withRel("atualizar"));
        resource.add(linkTo(methodOn(ControleCliente.class).excluirCliente(cliente.getId())).withRel("excluir"));
        resource.add(linkTo(methodOn(ControleCliente.class).obterClientes()).withRel("todos"));
        
        return resource;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<modeloCliente>> obterCliente(@PathVariable long id) {
        List<modeloCliente> clientes = repositorio.findAll();
        modeloCliente cliente = selecionador.selecionar(clientes, id);
        if (cliente != null) {
            return new ResponseEntity<>(adicionarLinks(cliente), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/todos")
    public ResponseEntity<CollectionModel<EntityModel<modeloCliente>>> obterClientes() {
        List<modeloCliente> clientes = repositorio.findAll();
        if (clientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        List<EntityModel<modeloCliente>> clientesComLinks = clientes.stream()
                .map(this::adicionarLinks)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<modeloCliente>> collectionModel = CollectionModel.of(clientesComLinks);
        collectionModel.add(linkTo(methodOn(ControleCliente.class).obterClientes()).withSelfRel());
        collectionModel.add(linkTo(methodOn(ControleCliente.class).cadastrarCliente(null)).withRel("cadastrar"));
        
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
    
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarCliente(@RequestBody modeloCliente cliente) {
        try {
            cliente.setId(null);
            modeloCliente novoCliente = repositorio.save(cliente);
            return new ResponseEntity<>(adicionarLinks(novoCliente), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar cliente: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable long id, @RequestBody modeloCliente atualizacao) {
        List<modeloCliente> clientes = repositorio.findAll();
        modeloCliente cliente = selecionador.selecionar(clientes, id);
        if (cliente == null) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        
        try {
            ClienteAtualizador atualizador = new ClienteAtualizador();
            atualizador.atualizar(cliente, atualizacao);
            repositorio.save(cliente);
            return new ResponseEntity<>(adicionarLinks(cliente), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar cliente: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirCliente(@PathVariable long id) {
        List<modeloCliente> clientes = repositorio.findAll();
        modeloCliente cliente = selecionador.selecionar(clientes, id);
        if (cliente == null) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.NOT_FOUND);
        }
        
        try {
            repositorio.delete(cliente);
            return new ResponseEntity<>("Cliente excluído com sucesso", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao excluir cliente: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/cadastro-completo")
    public ResponseEntity<?> cadastrarClienteCompleto(@RequestBody clienteDTO dto) {
        try {
            modeloCliente cliente = servicoCompleto.cadastrarCompleto(dto);
            return new ResponseEntity<>(adicionarLinks(cliente), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar cliente: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/atualizar-completo/{id}")
    public ResponseEntity<?> atualizarClienteCompleto(@PathVariable Long id, @RequestBody clienteDTO dto) {
        try {
            modeloCliente cliente = servicoCompleto.atualizarCompleto(id, dto);
            return new ResponseEntity<>(adicionarLinks(cliente), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar cliente: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}