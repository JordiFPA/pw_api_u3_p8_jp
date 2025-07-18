package uce.edu.web.api.service;

import java.util.List;

import uce.edu.web.api.repository.modelo.Hijo;

public interface IHijoService {
    public List<Hijo> buscarPorEstudiante(Integer id);
    public List<Hijo> buscarPorProfesor(Integer id);
}
