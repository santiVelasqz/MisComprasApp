package com.smarvel.springboot.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarvel.springboot.backend.entities.Supermercado;

public interface SupermercadoRepository extends JpaRepository<Supermercado, Long> {

	List<Supermercado> findByNombreContainingIgnoreCaseOrCiudadContainingIgnoreCase(String nombre, String ciudad);
}
