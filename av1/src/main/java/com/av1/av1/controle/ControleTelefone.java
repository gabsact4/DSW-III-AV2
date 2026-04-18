package com.av1.av1.controle;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
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

import com.av1.av1.Modelo.modeloTelefone;
import com.av1.av1.Repositorio.RepositorioTelefone;
import com.av1.av1.Servico.Atualiaza.TelefoneAtualizador;

@RestController
@RequestMapping("/telefone")
public class ControleTelefone {
    
    @Autowired
    private RepositorioTelefone repositorio;
    
    private EntityModel<modeloTelefone> adicionarLinks(modeloTelefone telefone) {
        EntityModel<modeloTelefone> resource = EntityModel.of(telefone);
        
        resource.add(linkTo(methodOn(ControleTelefone.class).obterTelefone(telefone.getId())).withSelfRel());
        resource.add(linkTo(methodOn(ControleTelefone.class).atualizarTelefone(telefone.getId(), null)).withRel("atualizar"));
        resource.add(linkTo(methodOn(ControleTelefone.class).excluirTelefone(telefone.getId())).withRel("excluir"));
        resource.add(linkTo(methodOn(ControleTelefone.class).obterTelefones()).withRel("todos"));
        
        if (telefone.getDdd() != null && !telefone.getDdd().isEmpty()) {
            resource.add(linkTo(methodOn(ControleTelefone.class).obterPorDdd(telefone.getDdd())).withRel("mesmo-ddd"));
        }
        
        return resource;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<modeloTelefone>> obterTelefone(@PathVariable long id) {
        Optional<modeloTelefone> telefone = repositorio.findById(id);
        if (telefone.isPresent()) {
            return new ResponseEntity<>(adicionarLinks(telefone.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/todos")
    public ResponseEntity<CollectionModel<EntityModel<modeloTelefone>>> obterTelefones() {
        List<modeloTelefone> telefones = repositorio.findAll();
        if (telefones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        List<EntityModel<modeloTelefone>> telefonesComLinks = telefones.stream()
                .map(this::adicionarLinks)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<modeloTelefone>> collectionModel = CollectionModel.of(telefonesComLinks);
        collectionModel.add(linkTo(methodOn(ControleTelefone.class).obterTelefones()).withSelfRel());
        collectionModel.add(linkTo(methodOn(ControleTelefone.class).cadastrarTelefone(null)).withRel("cadastrar"));
        
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
    
    @GetMapping("/ddd/{ddd}")
    public ResponseEntity<CollectionModel<EntityModel<modeloTelefone>>> obterPorDdd(@PathVariable String ddd) {
        List<modeloTelefone> telefones = repositorio.findByDdd(ddd);
        
        if (telefones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        List<EntityModel<modeloTelefone>> telefonesComLinks = telefones.stream()
                .map(this::adicionarLinks)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<modeloTelefone>> collectionModel = CollectionModel.of(telefonesComLinks);
        collectionModel.add(linkTo(methodOn(ControleTelefone.class).obterPorDdd(ddd)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ControleTelefone.class).obterTelefones()).withRel("todos-telefones"));
        
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
    
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarTelefone(@RequestBody modeloTelefone telefone) {
        try {
            telefone.setId(null);
            modeloTelefone novoTelefone = repositorio.save(telefone);
            return new ResponseEntity<>(adicionarLinks(novoTelefone), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar telefone: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarTelefone(@PathVariable long id, @RequestBody modeloTelefone atualizacao) {
        Optional<modeloTelefone> telefoneOpt = repositorio.findById(id);
        if (!telefoneOpt.isPresent()) {
            return new ResponseEntity<>("Telefone não encontrado", HttpStatus.NOT_FOUND);
        }
        
        try {
            modeloTelefone telefone = telefoneOpt.get();
            TelefoneAtualizador atualizador = new TelefoneAtualizador();
            atualizador.atualizar(telefone, atualizacao);
            repositorio.save(telefone);
            return new ResponseEntity<>(adicionarLinks(telefone), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar telefone: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirTelefone(@PathVariable long id) {
        Optional<modeloTelefone> telefone = repositorio.findById(id);
        if (!telefone.isPresent()) {
            return new ResponseEntity<>("Telefone não encontrado", HttpStatus.NOT_FOUND);
        }
        
        try {
            repositorio.delete(telefone.get());
            
            Link linkParaTodos = linkTo(methodOn(ControleTelefone.class).obterTelefones()).withRel("todos-telefones");
            
            return ResponseEntity.ok()
                    .body("Telefone excluído com sucesso. " + linkParaTodos.getRel() + ": " + linkParaTodos.getHref());
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao excluir telefone: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}