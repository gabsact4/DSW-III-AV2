package com.av1.av1.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.av1.av1.Modelo.modeloDocumento;

public interface RepositorioDocumento extends JpaRepository<modeloDocumento, Long> {
    List<modeloDocumento> findByTipo(String tipo);
    List<modeloDocumento> findByNumero(String numero);
}