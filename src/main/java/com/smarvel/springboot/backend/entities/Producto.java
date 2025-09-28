package com.smarvel.springboot.backend.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="productos")
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@OneToMany(mappedBy = "producto")
    @JsonIgnore
    private List<ProductoSupermercado> productosSupermercados = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="categoria_id") // Mejor poner _id para claridad en la base de datos
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name="marca_id") // Mejor poner _id para claridad en la base de datos
    private Marca marca;

   
    
    

	
	public Producto() {}

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

	/*public List<ProductoSupermercado> getProductoSupermercado() {
		return productoSupermercado;
	}

	public void setProductoSupermercado(List<ProductoSupermercado> productoSupermercado) {
		this.productoSupermercado = productoSupermercado;
	}*/

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public List<ProductoSupermercado> getProductosSupermercados() {
		return productosSupermercados;
	}

	public void setProductosSupermercados(List<ProductoSupermercado> productosSupermercados) {
		this.productosSupermercados = productosSupermercados;
	}

	/*public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}*/
	
	
	
	

	
	
	

}
