package uce.edu.controller;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.repository.modelo.Estudiante;
import uce.edu.web.api.repository.modelo.Hijo;
import uce.edu.web.api.service.IEstudianteService;
import uce.edu.web.api.service.to.EstudianteTo;

@Path("/estudiantes")
public class EstudianteController extends BaseControlador {

    @Inject
    private IEstudianteService estudianteService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Consultar estudiante por ID", description = "Este endpoint permite consultar un estudiante por su ID.")
    public Response consultarPorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {

        EstudianteTo estu = this.estudianteService.buscarPorId(id, uriInfo);

        return Response.status(227)
                .entity(estu)
                .build();
    }

    @GET
    @Path("")

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

    public Response actualizarPorId(@RequestBody Estudiante estudiante, @PathParam("id") Integer id) {
        estudiante.setId(id);
        this.estudianteService.actualizarporId(estudiante);
        return Response.status(Response.Status.OK)
                .entity("Correcto")
                .build();
    }

    /*
     * @PATCH
     * 
     * @Path("/{id}")
     * public Response actualizarParcialPorId(@RequestBody Estudiante
     * estudiante, @PathParam("id") Integer id) {
     * Estudiante existente = this.estudianteService.buscarPorId(id);
     * if (existente == null) {
     * return Response.status(Response.Status.NOT_FOUND).build();
     * }
     * if (estudiante.getApellido() != null) {
     * existente.setApellido(estudiante.getApellido());
     * }
     * if (estudiante.getNombre() != null) {
     * existente.setNombre(estudiante.getNombre());
     * }
     * if (estudiante.getFechaNacimiento() != null) {
     * existente.setFechaNacimiento(estudiante.getFechaNacimiento());
     * }
     * if (estudiante.getGenero() != null) {
     * existente.setGenero(estudiante.getGenero());
     * }
     * this.estudianteService.actualizarParcialPorId(existente);
     * return Response.status(Response.Status.OK)
     * .entity("Correcto")
     * .build();
     * }
     */

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
    public Response guardar(@RequestBody Estudiante estudiante) {
        this.estudianteService.guardar(estudiante);
        return Response.status(Response.Status.CREATED)
                .entity("Estudiante creado correctamente")
                .build();
    }

    @GET
    @Path("/{id}/hijos")
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {
        Hijo h1 = new Hijo();
        h1.setNombre("Fernandito");
        Hijo h2 = new Hijo();
        h2.setNombre("Jordicito");
        List<Hijo> hijos = new ArrayList<>();
        hijos.add(h1);
        hijos.add(h2);

        return hijos;
    }

}