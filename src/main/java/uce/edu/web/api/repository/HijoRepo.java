package uce.edu.web.api.repository;

import java.util.List;

import uce.edu.web.api.repository.modelo.Hijo;

public interface HijoRepo {
    public List<Hijo> buscarPorEstudiante(Integer id);
    public List<Hijo> buscarPorProfesor(Integer id);

}
