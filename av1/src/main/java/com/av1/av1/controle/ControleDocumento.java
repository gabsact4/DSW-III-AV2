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

import com.av1.av1.Modelo.modeloDocumento;
import com.av1.av1.Repositorio.RepositorioDocumento;
import com.av1.av1.Servico.Atualiaza.DocumentoAtualizador;

@RestController
@RequestMapping("/documento")
public class ControleDocumento {
    
    @Autowired
    private RepositorioDocumento repositorio;
    
    private EntityModel<modeloDocumento> adicionarLinks(modeloDocumento documento) {
        EntityModel<modeloDocumento> resource = EntityModel.of(documento);
        
        resource.add(linkTo(methodOn(ControleDocumento.class).obterDocumento(documento.getId())).withSelfRel());
        resource.add(linkTo(methodOn(ControleDocumento.class).atualizarDocumento(documento.getId(), null)).withRel("atualizar"));
        resource.add(linkTo(methodOn(ControleDocumento.class).excluirDocumento(documento.getId())).withRel("excluir"));
        resource.add(linkTo(methodOn(ControleDocumento.class).obterDocumentos()).withRel("todos"));
        
        if (documento.getTipo() != null) {
            resource.add(linkTo(methodOn(ControleDocumento.class).obterPorTipo(documento.getTipo())).withRel("mesmo-tipo"));
        }
        
        return resource;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<modeloDocumento>> obterDocumento(@PathVariable long id) {
        Optional<modeloDocumento> documento = repositorio.findById(id);
        if (documento.isPresent()) {
            return new ResponseEntity<>(adicionarLinks(documento.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/todos")
    public ResponseEntity<CollectionModel<EntityModel<modeloDocumento>>> obterDocumentos() {
        List<modeloDocumento> documentos = repositorio.findAll();
        if (documentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        List<EntityModel<modeloDocumento>> documentosComLinks = documentos.stream()
                .map(this::adicionarLinks)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<modeloDocumento>> collectionModel = CollectionModel.of(documentosComLinks);
        collectionModel.add(linkTo(methodOn(ControleDocumento.class).obterDocumentos()).withSelfRel());
        collectionModel.add(linkTo(methodOn(ControleDocumento.class).cadastrarDocumento(null)).withRel("cadastrar"));
        
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
    
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<CollectionModel<EntityModel<modeloDocumento>>> obterPorTipo(@PathVariable String tipo) {
        List<modeloDocumento> documentos = repositorio.findByTipo(tipo);
        
        if (documentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        List<EntityModel<modeloDocumento>> documentosComLinks = documentos.stream()
                .map(this::adicionarLinks)
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<modeloDocumento>> collectionModel = CollectionModel.of(documentosComLinks);
        collectionModel.add(linkTo(methodOn(ControleDocumento.class).obterPorTipo(tipo)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ControleDocumento.class).obterDocumentos()).withRel("todos-documentos"));
        
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }
    
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarDocumento(@RequestBody modeloDocumento documento) {
        try {
            documento.setId(null);
            modeloDocumento novoDocumento = repositorio.save(documento);
            return new ResponseEntity<>(adicionarLinks(novoDocumento), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao cadastrar documento: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarDocumento(@PathVariable long id, @RequestBody modeloDocumento atualizacao) {
        Optional<modeloDocumento> documentoOpt = repositorio.findById(id);
        if (!documentoOpt.isPresent()) {
            return new ResponseEntity<>("Documento não encontrado", HttpStatus.NOT_FOUND);
        }
        
        try {
            modeloDocumento documento = documentoOpt.get();
            DocumentoAtualizador atualizador = new DocumentoAtualizador();
            atualizador.atualizar(documento, atualizacao);
            repositorio.save(documento);
            return new ResponseEntity<>(adicionarLinks(documento), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao atualizar documento: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirDocumento(@PathVariable long id) {
        Optional<modeloDocumento> documento = repositorio.findById(id);
        if (!documento.isPresent()) {
            return new ResponseEntity<>("Documento não encontrado", HttpStatus.NOT_FOUND);
        }
        
        try {
            repositorio.delete(documento.get());
            
            Link linkParaTodos = linkTo(methodOn(ControleDocumento.class).obterDocumentos()).withRel("todos-documentos");
            
            return ResponseEntity.ok()
                    .body("Documento excluído com sucesso. " + linkParaTodos.getRel() + ": " + linkParaTodos.getHref());
        } catch (Exception e) {
            return new ResponseEntity<>("Erro ao excluir documento: " + e.getMessage(), 
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}