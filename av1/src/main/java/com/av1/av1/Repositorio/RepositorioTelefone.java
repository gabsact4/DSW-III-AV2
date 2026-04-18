package com.av1.av1.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.av1.av1.Modelo.modeloTelefone;

public interface RepositorioTelefone extends JpaRepository<modeloTelefone, Long> {
    List<modeloTelefone> findByDdd(String ddd);
    List<modeloTelefone> findByNumero(String numero);
}