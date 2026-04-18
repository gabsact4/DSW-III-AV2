package com.av1.av1.Repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.av1.av1.Modelo.modeloEndereco;

public interface RepositorioEndereco extends JpaRepository<modeloEndereco, Long> {
    List<modeloEndereco> findByCidade(String cidade);
    List<modeloEndereco> findByEstado(String estado);
}