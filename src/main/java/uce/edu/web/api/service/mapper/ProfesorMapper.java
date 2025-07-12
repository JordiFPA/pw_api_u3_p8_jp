package uce.edu.web.api.service.mapper;

import uce.edu.web.api.repository.modelo.Profesor;
import uce.edu.web.api.service.to.ProfesorTo;

public class ProfesorMapper {
    public static ProfesorTo toTo(Profesor profesor) {
        ProfesorTo pTo = new ProfesorTo();
        pTo.setId(profesor.getId());
        pTo.setNombre(profesor.getNombre());
        pTo.setApellido(profesor.getApellido());
        pTo.setEdad(profesor.getEdad());
        pTo.setEspecialidad(profesor.getEspecialidad());
        pTo.setGenero(profesor.getGenero());
        return pTo;
    }

    public static Profesor toEntity(ProfesorTo profesorTo) {
        Profesor p = new Profesor();
        p.setId(profesorTo.getId());
        p.setNombre(profesorTo.getNombre());
        p.setApellido(profesorTo.getApellido());
        p.setEdad(profesorTo.getEdad());
        p.setEspecialidad(profesorTo.getEspecialidad());
        p.setGenero(profesorTo.getGenero());
        return p;
    }
}