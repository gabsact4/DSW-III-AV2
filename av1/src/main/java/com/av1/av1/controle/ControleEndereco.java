package com.av1.av1.controle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

import com.av1.av1.Modelo.modeloEndereco;
import com.av1.av1.Repositorio.RepositorioEndereco;
import com.av1.av1.Servico.Atualiaza.EnderecoAtualizador;

@RestController
@RequestMapping("/endereco")
public class ControleEndereco {
    
    @Autowired
    private RepositorioEndereco repositorio;
    
    private EntityModel<modeloEndereco> adicionarLinks(modeloEndereco endereco) {
        EntityModel<modeloEndereco> resource = EntityModel.of(endereco);
        
        resource.add(linkTo(methodOn(ControleEndereco.class).obterEndereco(endereco.getId())).withSelfRel());
        resource.add(linkTo(methodOn(ControleEndereco.class).atualizarEndereco(endereco.getId(), null)).withRel("atualizar"));
        resource.add(linkTo(methodOn(ControleEndereco.class).excluirEndereco(endereco.getId())).withRel("excluir"));
        resource.add(linkTo(methodOn(ControleEndereco.class).obterEnderecos()).withRel("todos"));
        if (endereco.getCidade() != null && !endereco.getCidade().isEmpty()) {
            resource.add(linkTo(methodOn(ControleEndereco.class).obterPorCidade(endereco.getCidade())).withRel("mesma-cidade"));
        }
        
        return resource;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<modeloEndereco>> obterEndereco(@PathVariable long id) {
        Optional<modeloEndereco> endereco = repositorio.findById(id);
        if (endereco.isPresent()) {
            return new ResponseEntity<>(adicionarLinks(endereco.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/todos")
    public ResponseEntity<CollectionModel<EntityModel<modeloEndereco>>> obterEnderecos() {
        List<modeloEndereco> enderecos = repositorio.findAll();
        if (enderecos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        List<EntityModel<modeloEndereco>> enderecosComLinks = enderecos.stream()
                .map(this::adicionarLinks)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<modeloEndereco>> collectionModel = CollectionModel.of(enderecosComLinks);
        
        collectionModel.add(linkTo(methodOn(ControleEndereco.class).obterEnderecos()).withSelfRel());
        
        collectionModel.add(linkTo(methodOn(ControleEndereco.class).cadastrarEndereco(null)).withRel("cadastrar"));
        
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
    
    @GetMapping("/cidade/{cidade}")
    public ResponseEntity<CollectionModel<EntityModel<modeloEndereco>>> obterPorCidade(@PathVariable String cidade) {
        List<modeloEndereco> enderecos = repositorio.findByCidade(cidade);
        
        if (enderecos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        List<EntityModel<modeloEndereco>> enderecosComLinks = enderecos.stream()
                .map(this::adicionarLinks)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<modeloEndereco>> collectionModel = CollectionModel.of(enderecosComLinks);
        
        collectionModel.add(linkTo(methodOn(ControleEndereco.class).obterPorCidade(cidade)).withSelfRel());
        
        collectionModel.add(linkTo(methodOn(ControleEndereco.class).obterEnderecos()).withRel("todos-enderecos"));
        
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
    
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody modeloEndereco endereco) {
        try {
            endereco.setId(null);
            modeloEndereco novoEndereco = repositorio.save(endereco);
            return new ResponseEntity<>(adicionarLinks(novoEndereco), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar endereço: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarEndereco(@PathVariable long id, @RequestBody modeloEndereco atualizacao) {
        Optional<modeloEndereco> enderecoOpt = repositorio.findById(id);
        if (!enderecoOpt.isPresent()) {
            return new ResponseEntity<>("Endereço não encontrado", HttpStatus.NOT_FOUND);
        }
        
        try {
            modeloEndereco endereco = enderecoOpt.get();
            EnderecoAtualizador atualizador = new EnderecoAtualizador();
            atualizador.atualizar(endereco, atualizacao);
            repositorio.save(endereco);
            return new ResponseEntity<>(adicionarLinks(endereco), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar endereço: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirEndereco(@PathVariable long id) {
        Optional<modeloEndereco> endereco = repositorio.findById(id);
        if (!endereco.isPresent()) {
            return new ResponseEntity<>("Endereço não encontrado", HttpStatus.NOT_FOUND);
        }
        
        try {
            repositorio.delete(endereco.get());
            
            Link linkParaTodos = linkTo(methodOn(ControleEndereco.class).obterEnderecos()).withRel("todos-enderecos");
            
            return ResponseEntity.ok()
                    .body("Endereço excluído com sucesso. " + linkParaTodos.getRel() + ": " + linkParaTodos.getHref());
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao excluir endereço: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}