package uce.edu.web.api.service.to;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import jakarta.ws.rs.core.UriInfo;
import uce.edu.controller.ProfesorController;

public class ProfesorTo {
    private int id;
    private String nombre;
    private String apellido;
    private int edad;
    private String especialidad;
    private String genero;
    public Map<String, String> _links = new HashMap<>();

    public ProfesorTo(int id, String nombre, String apellido, int edad, String especialidad, String genero,
            UriInfo uriInfo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.especialidad = especialidad;
        this.genero = genero;

        URI todosHijos = uriInfo.getBaseUriBuilder().path(ProfesorController.class)
                .path(ProfesorController.class, "obtenerHijosPorId").build(id);
        _links.put("hijos", todosHijos.toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Map<String, String> get_links() {
        return _links;
    }

    public void set_links(Map<String, String> _links) {
        this._links = _links;
    }

}
