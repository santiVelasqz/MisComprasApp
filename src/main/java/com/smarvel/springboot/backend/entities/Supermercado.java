package com.smarvel.springboot.backend.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="supermercados")
public class Supermercado {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	private String ciudad;
	
	@OneToMany(mappedBy = "supermercado")
    @JsonIgnore
    private List<ProductoSupermercado> productosSupermercados = new ArrayList<>();
	
	public Supermercado() {}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public List<ProductoSupermercado> getProductosSupermercados() {
		return productosSupermercados;
	}


	public void setProductosSupermercados(List<ProductoSupermercado> productosSupermercados) {
		this.productosSupermercados = productosSupermercados;
	}


	/*public List<ProductoSupermercado> getProductoSupermercado() {
		return productoSupermercado;
	}


	public void setProductoSupermercado(List<ProductoSupermercado> productoSupermercado) {
		this.productoSupermercado = productoSupermercado;
	}


	public List<Compra> getCompras() {
		return compras;
	}


	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}*/
	
	
	
	
	

}
