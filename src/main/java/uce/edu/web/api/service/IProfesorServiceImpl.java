package uce.edu.web.api.service;

import java.net.URI;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.controller.EstudianteController;
import uce.edu.controller.ProfesorController;
import uce.edu.web.api.repository.IProfesorRepo;
import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.to.ProfesorTo;

@ApplicationScoped
public class IProfesorServiceImpl implements IProfesorService {

  @Inject
  private IProfesorRepo profesorRepo;

  @Override
  public List<Profesor> buscarTodos() {
    return this.profesorRepo.SeleccionarTodos();
  }

  @Override
  public void actualizarporId(Profesor profesor) {
    this.profesorRepo.actualizarPorId(profesor);

  }

  @Override
  public void actualizarParcialPorId(Profesor profesor) {
    this.profesorRepo.actualizarParcialPorId(profesor);
  }

  @Override
  public void borrarPorId(Integer id) {
    this.profesorRepo.borrarPorId(id);
  }

  @Override
  public void guardar(Profesor profesor) {
    this.profesorRepo.insertar(profesor);
  }

  @Override
  public List<Profesor> buscarTodosFiltro(String genero) {
    return this.profesorRepo.seleccionarTodosFiltro(genero);
  }

  @Override
  public ProfesorTo buscarPorId(Integer id, UriInfo urinfo) {
    Profesor p = this.profesorRepo.seleccionarPorId(id);
    ProfesorTo pTo = new ProfesorTo(p.getId(), p.getNombre(), p.getApellido(), p.getEdad(), p.getEspecialidad(),
        p.getGenero(), urinfo);
    return pTo;
  }

}
