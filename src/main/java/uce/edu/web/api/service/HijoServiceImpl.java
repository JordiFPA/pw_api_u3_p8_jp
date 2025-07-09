package uce.edu.web.api.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import uce.edu.web.api.repository.HijoRepo;
import uce.edu.web.api.repository.modelo.Hijo;

@ApplicationScoped
public class HijoServiceImpl implements IHijoService {
    @Inject
    private HijoRepo hijorepo;

    @Override
    public List<Hijo> buscarPorEstudiante(Integer id) {
        return this.hijorepo.buscarPorEstudiante(id);
    }

}
