package com.smarvel.springboot.backend.dto;

public class SupermercadoConBorradoDTO {

	 private Long id;
	    private String nombre;
	    private String ciudad;
	    private boolean noBorrable;

	    public SupermercadoConBorradoDTO() {}

	    public SupermercadoConBorradoDTO(Long id, String nombre, String ciudad, boolean noBorrable) {
	        this.id = id;
	        this.nombre = nombre;
	        this.ciudad = ciudad;
	        this.noBorrable = noBorrable;
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

	    public String getCiudad() {
	        return ciudad;
	    }

	    public void setCiudad(String ciudad) {
	        this.ciudad = ciudad;
	    }

	    public boolean isNoBorrable() {
	        return noBorrable;
	    }

	    public void setNoBorrable(boolean noBorrable) {
	        this.noBorrable = noBorrable;
	    }
}
