package com.smarvel.springboot.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.smarvel.springboot.backend.entities.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long>{
	
	List<Marca>findByNombreContainingIgnoreCase(String nombre);

}
