package uce.edu.web.api.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import uce.edu.web.api.repository.modelo.Profesor;
import jakarta.persistence.TypedQuery;

@Transactional
@ApplicationScoped

public class IProfesorRepoImpl implements IProfesorRepo {

    @Inject
    private EntityManager entityManager;

    @Override
    public Profesor seleccionarPorId(Integer id) {

        return this.entityManager.find(Profesor.class, id);
    }

    @Override
    public List<Profesor> SeleccionarTodos() {
        TypedQuery<Profesor> myQuery = this.entityManager.createQuery("SELECT p FROM Profesor p", Profesor.class);
        return myQuery.getResultList();

    }

}
