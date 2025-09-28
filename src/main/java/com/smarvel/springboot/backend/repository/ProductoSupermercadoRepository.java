package com.smarvel.springboot.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smarvel.springboot.backend.entities.ProductoSupermercado;

public interface ProductoSupermercadoRepository extends JpaRepository<ProductoSupermercado, Long>{
	
	List<ProductoSupermercado> findBySupermercadoId(Long supermercadoId);

	List<ProductoSupermercado> findByProducto_NombreContainingIgnoreCase(String nombre);

	ProductoSupermercado findByProducto_IdAndSupermercado_Id(Long productoId, Long supermercadoId);

	List<ProductoSupermercado> findBySupermercadoIdAndProductoNombreContainingIgnoreCase(Long superId, String search);

	ProductoSupermercado findByProductoIdAndSupermercadoId(Long productoId, Long supermercadoId);
	boolean existsBySupermercadoId(Long supermercadoId);
	
	//List<ProductoSupermercado> searchPorNombreYSuper(Long supermercadoId, String nombre);
	@Query("SELECT ps FROM ProductoSupermercado ps WHERE ps.supermercado.id = :supermercadoId AND LOWER(ps.producto.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<ProductoSupermercado> searchPorNombreYSuper(@Param("supermercadoId") Long supermercadoId, @Param("nombre") String nombre);
	

}
