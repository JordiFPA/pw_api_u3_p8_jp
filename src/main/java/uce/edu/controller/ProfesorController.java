package uce.edu.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.IProfesorService;

@Path("/profesores")
public class ProfesorController {

    @Inject
    private IProfesorService profesorService;

    @GET
    @Path("/{id}")
    public Profesor consultarPorId(@PathParam("id") Integer id) {
        return this.profesorService.buscarPorId(id);
    }

    @GET
    @Path("")
    public List<Profesor> consultarTodos() {
        return this.profesorService.buscarTodos();
    }

    @PUT
    @Path("/{id}")
    public void actualizarPorId(@RequestBody Profesor profesor, @PathParam("id") Integer id) {
        Profesor existente = this.profesorService.buscarPorId(id);
        if (existente != null) {
            existente.setNombre(profesor.getNombre());
            existente.setApellido(profesor.getApellido());
            existente.setEdad(profesor.getEdad());
            existente.setEspecialidad(profesor.getEspecialidad());
            this.profesorService.actualizarporId(existente);
        }
    }

    @PATCH
    @Path("/{id}")
    public void actualizarParcialPorId(@RequestBody Profesor profesor, @PathParam("id") Integer id) {
        profesor.setId(id);
        Profesor p = this.profesorService.buscarPorId(id);
        if (profesor.getApellido() != null) {
            p.setApellido(profesor.getApellido());
        } else if (profesor.getNombre() != null) {
            p.setNombre(profesor.getNombre());
        } else if (profesor.getEdad() != 0) {
            p.setEdad(profesor.getEdad());
        } else if (profesor.getEspecialidad() != null) {
            p.setEspecialidad(profesor.getEspecialidad());
        }

        this.profesorService.actualizarParcialPorId(p);
    }

    @DELETE
    @Path("/{id}")
    public void eliminarPorId(@PathParam("id") Integer id) {
        this.profesorService.borrarPorId(id);
    }

    @POST
    @Path("")
    public void insertar(@RequestBody Profesor profesor) {
        this.profesorService.guardar(profesor);
    }

}
