package com.smarvel.springboot.backend.dto;

public class ProductoConSupermercadoDTO {

	private Long id;
    private String nombre;
    private boolean tieneSupermercado;
    
	public ProductoConSupermercadoDTO(Long id, String nombre, boolean tieneSupermercado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.tieneSupermercado = tieneSupermercado;
	}

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

	public boolean isTieneSupermercado() {
		return tieneSupermercado;
	}

	public void setTieneSupermercado(boolean tieneSupermercado) {
		this.tieneSupermercado = tieneSupermercado;
	}
    
	
    
}
