package uce.edu.controller;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import java.util.ArrayList;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.repository.modelo.Hijo;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.IHijoService;
import uce.edu.web.api.service.IProfesorService;
import uce.edu.web.api.service.mapper.ProfesorMapper;
import uce.edu.web.api.service.to.ProfesorTo;

@Path("/profesores")
public class ProfesorController {

    @Inject
    private IProfesorService profesorService;
    @Inject
    private IHijoService hijoService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarPorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Profesor profesor = this.profesorService.buscarPorId(id);
        if (profesor == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No encontrado").build();
        }
        ProfesorTo profeTo = ProfesorMapper.toTo(profesor);
        profeTo.buildURI(uriInfo);
        return Response.status(227)
                .entity(profeTo)
                .build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    public Response consultarTodos(@QueryParam("genero") String genero,
            @QueryParam("provincia") String provincia,
            @Context UriInfo uriInfo) {
        System.out.println(provincia);

        List<Profesor> lista = this.profesorService.buscarTodosFiltro(genero);
        List<ProfesorTo> listaTo = new ArrayList<>();

        for (Profesor p : lista) {
            ProfesorTo profeTo = ProfesorMapper.toTo(p);
            profeTo.buildURI(uriInfo);
            listaTo.add(profeTo);
        }

        return Response.status(Response.Status.OK)
                .entity(listaTo)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarPorId(@RequestBody ProfesorTo profesorTo, @PathParam("id") Integer id) {
        Profesor profesor = ProfesorMapper.toEntity(profesorTo);
        profesor.setId(id);
        this.profesorService.actualizarporId(profesor);
        return Response.status(Response.Status.OK)
                .entity("Correcto")
                .build();
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarParcialPorId(@RequestBody ProfesorTo profesorTo, @PathParam("id") Integer id) {
        Profesor existente = this.profesorService.buscarPorId(id);
        if (existente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No encontrado").build();
        }
        if (profesorTo.getApellido() != null) {
            existente.setApellido(profesorTo.getApellido());
        }
        if (profesorTo.getNombre() != null) {
            existente.setNombre(profesorTo.getNombre());
        }
        if (profesorTo.getEdad() != 0) {
            existente.setEdad(profesorTo.getEdad());
        }
        if (profesorTo.getEspecialidad() != null) {
            existente.setEspecialidad(profesorTo.getEspecialidad());
        }
        if (profesorTo.getGenero() != null) {
            existente.setGenero(profesorTo.getGenero());
        }
        this.profesorService.actualizarParcialPorId(existente);

        ProfesorTo actualizado = ProfesorMapper.toTo(existente);
        return Response.status(Response.Status.OK)
                .entity(actualizado)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        this.profesorService.borrarPorId(id);
        return Response.status(Response.Status.OK)
                .entity("Profesor eliminado correctamente")
                .build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardar(@RequestBody ProfesorTo profesorTo, @Context UriInfo uriInfo) {
        Profesor profesor = ProfesorMapper.toEntity(profesorTo);
        this.profesorService.guardar(profesor);
        ProfesorTo creado = ProfesorMapper.toTo(profesor);
        creado.buildURI(uriInfo);
        return Response.status(Response.Status.CREATED)
                .entity(creado)
                .build();
    }

    @GET
    @Path("/{id}/hijos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {
        return this.hijoService.buscarPorProfesor(id);
    }
}