package uce.edu.controller;

import org.eclipse.microprofile.openapi.annotations.Operation;
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
import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.service.IEstudianteService;

@Path("/estudiantes")
public class EstudianteController {

    @Inject
    private IEstudianteService estudianteService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar estudiante por ID", description = "Este endpoint permite consultar un estudiante por su ID.")
    public Response consultarPorId(@PathParam("id") Integer id) {
        return Response.status(Response.Status.OK)
                .entity(this.estudianteService.buscarPorId(id))
                .build();
    }

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar estudiante", description = "Consulta todos los estudiantes registrados en el sistema")
    public Response consultarTodos(@QueryParam("genero") String genero,
            @QueryParam("provincia") String provincia) {
        System.out.println(provincia);
        return Response.status(Response.Status.OK)
                .entity(this.estudianteService.buscarTodos(genero))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarPorId(@RequestBody Estudiante estudiante, @PathParam("id") Integer id) {
        estudiante.setId(id);
        this.estudianteService.actualizarporId(estudiante);
        return Response.status(Response.Status.OK)
                .entity("Correcto")
                .build();
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarParcialPorId(@RequestBody Estudiante estudiante, @PathParam("id") Integer id) {
        Estudiante existente = this.estudianteService.buscarPorId(id);
        if (existente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (estudiante.getApellido() != null) {
            existente.setApellido(estudiante.getApellido());
        }
        if (estudiante.getNombre() != null) {
            existente.setNombre(estudiante.getNombre());
        }
        if (estudiante.getFechaNacimiento() != null) {
            existente.setFechaNacimiento(estudiante.getFechaNacimiento());
        }
        if (estudiante.getGenero() != null) {
            existente.setGenero(estudiante.getGenero());
        }
        this.estudianteService.actualizarParcialPorId(existente);
        return Response.status(Response.Status.OK)
                .entity("Correcto")
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarPorId(@PathParam("id") Integer id) {
        this.estudianteService.borrarPorId(id);
        return Response.status(Response.Status.OK)
                .entity("Eliminado correctamente")
                .build();
    }

    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response guardar(@RequestBody Estudiante estudiante) {
        this.estudianteService.guardar(estudiante);
        return Response.status(Response.Status.CREATED)
                .entity("Estudiante creado correctamente")
                .build();
    }
}