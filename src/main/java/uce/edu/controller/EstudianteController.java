package uce.edu.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import jakarta.annotation.security.RolesAllowed;
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
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.repository.modelo.Hijo;
import uce.edu.web.api.service.IEstudianteService;
import uce.edu.web.api.service.IHijoService;
import uce.edu.web.api.service.mapper.EstudianteMapper;
import uce.edu.web.api.service.to.EstudianteTo;

@Path("/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstudianteController extends BaseControlador {

    @Inject
    private IEstudianteService estudianteService;
    @Inject
    private IHijoService hijoService;

    @Inject
    JsonWebToken jwt;

    @Inject
    @Claim("sub")
    ClaimValue<String> subject;

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    @Operation(summary = "Consultar estudiante por ID", description = "Este endpoint permite consultar un estudiante por su ID.")
    public Response consultarPorId(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        EstudianteTo estu = EstudianteMapper.toTo(this.estudianteService.buscarPorId(id));

        estu.buildURI(uriInfo);
        return Response.status(227)
                .entity(estu)
                .build();
    }

    @GET
    @Path("")
    @Operation(summary = "Consultar estudiante", description = "Consulta todos los estudiantes registrados en el sistema")
    public Response consultarTodos(@QueryParam("genero") String genero,
            @QueryParam("provincia") String provincia,
            @Context UriInfo uriInfo) {
        System.out.println(provincia);
        List<EstudianteTo> estudiantes = this.estudianteService.buscarTodos(genero).stream().map(EstudianteMapper::toTo)
                .collect(Collectors.toList());
        return Response.status(Response.Status.OK)
                .entity(estudiantes)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response actualizarPorId(@RequestBody EstudianteTo estudianteTo, @PathParam("id") Integer id) {
        estudianteTo.setId(id);
        this.estudianteService.actualizarporId(EstudianteMapper.toEntity(estudianteTo));
        return Response.status(Response.Status.OK)
                .entity("Correcto")
                .build();
    }

    @PATCH
    @Path("/{id}")
    public Response actualizarParcialPorId(@RequestBody EstudianteTo estudianteTo, @PathParam("id") Integer id) {
        estudianteTo.setId(id);
        EstudianteTo existente = EstudianteMapper.toTo(this.estudianteService.buscarPorId(id));
        if (existente == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No encontrado").build();
        }
        if (estudianteTo.getApellido() != null) {
            existente.setApellido(estudianteTo.getApellido());
        }
        if (estudianteTo.getNombre() != null) {
            existente.setNombre(estudianteTo.getNombre());
        }
        if (estudianteTo.getFechaNacimiento() != null) {
            existente.setFechaNacimiento(estudianteTo.getFechaNacimiento());
        }
        if (estudianteTo.getGenero() != null) {
            existente.setGenero(estudianteTo.getGenero());
        }
        this.estudianteService.actualizarParcialPorId(EstudianteMapper.toEntity(existente));

        return Response.status(Response.Status.OK)
                .entity(existente)
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
    public Response guardar(@RequestBody EstudianteTo estudianteTo) {
        this.estudianteService.guardar(EstudianteMapper.toEntity(estudianteTo));
        return Response.status(Response.Status.CREATED)
                .entity("Estudiante creado correctamente")
                .build();
    }

    @GET
    @Path("/{id}/hijos")
    public List<Hijo> obtenerHijosPorId(@PathParam("id") Integer id) {
        return this.hijoService.buscarPorEstudiante(id);
    }

}