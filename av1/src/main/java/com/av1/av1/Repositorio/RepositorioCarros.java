package com.av1.av1.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.av1.av1.Modelo.modeloCliente;

public interface RepositorioCarros extends JpaRepository<modeloCliente, Long> {
}