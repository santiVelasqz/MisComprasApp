package com.smarvel.springboot.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarvel.springboot.backend.entities.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria,Long>{
	
	List<Categoria>findByNombreContainingIgnoreCase(String nombre);

}
