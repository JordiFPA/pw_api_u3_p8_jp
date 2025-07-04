package uce.edu.controller;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.IProfesorService;

@Path("/profesores")

public class ProfesorController {

    @Inject
    private IProfesorService profesorService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarPorId(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK).entity(this.profesorService.buscarPorId(id)).build();

    }

    /*
     * @GET
     * 
     * @Path("")
     * 
     * @Produces(MediaType.APPLICATION_JSON)
     * public Response consultarTodos() {
     * return Response.status(Response.Status.OK)
     * .entity(this.profesorService.buscarTodos())
     * .build();
     * }
     */

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarTodosFiltro(@QueryParam("genero") String genero,
            @QueryParam("provincia") String provincia) {
        System.out.println(provincia);

        return Response.status(Response.Status.OK).entity(this.profesorService.buscarTodosFiltro(genero))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizarPorId(@RequestBody Profesor profesor, @PathParam("id") Integer id) {
        Profesor existente = this.profesorService.buscarPorId(id);
        if (existente != null) {
            existente.setNombre(profesor.getNombre());
            existente.setApellido(profesor.getApellido());
            existente.setEdad(profesor.getEdad());
            existente.setEspecialidad(profesor.getEspecialidad());
            existente.setGenero(profesor.getGenero());
        }
        this.profesorService.actualizarporId(existente);
        return Response.status(Response.Status.OK)
                .entity("Correcto")
                .build();
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response actualizarParcialPorId(@RequestBody Profesor profesor, @PathParam("id") Integer id) {
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
        } else if (profesor.getGenero() != null) {
            p.setGenero(profesor.getGenero());
        }
        this.profesorService.actualizarParcialPorId(p);
        return Response.status(Response.Status.OK).entity("").build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        this.profesorService.borrarPorId(id);
        return Response.status(Response.Status.NO_CONTENT)
                .entity("Profesor eliminado correctamente")
                .build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardar(@RequestBody Profesor profesor) {
        this.profesorService.guardar(profesor);
        return Response.status(Response.Status.CREATED)
                .entity("Profesor creado correctamente")
                .build();
    }

}
