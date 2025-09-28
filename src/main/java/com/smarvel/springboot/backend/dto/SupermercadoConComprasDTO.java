package com.smarvel.springboot.backend.dto;

public class SupermercadoConComprasDTO {

	private Long id;
    private String nombre;
    private String ciudad;
    private boolean tieneCompras;
    
    public SupermercadoConComprasDTO(Long id, String nombre, String ciudad, boolean tieneCompras) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.tieneCompras = tieneCompras;
    }
    // getters y setters

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

	public boolean isTieneCompras() {
		return tieneCompras;
	}

	public void setTieneCompras(boolean tieneCompras) {
		this.tieneCompras = tieneCompras;
	}
    
    
}
