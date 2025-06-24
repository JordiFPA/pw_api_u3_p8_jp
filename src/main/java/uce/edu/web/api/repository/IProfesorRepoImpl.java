package uce.edu.web.api.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import uce.edu.web.api.repository.modelo.Profesor;

@Transactional
@ApplicationScoped

public class IProfesorRepoImpl implements IProfesorRepo {

    @Inject
    private EntityManager entityManager;

    @Override
    public Profesor seleccionarPorId(Integer id) {

        return this.entityManager.find(Profesor.class, id);
    }

}
