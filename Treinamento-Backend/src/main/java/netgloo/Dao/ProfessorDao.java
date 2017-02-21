package netgloo.Dao;

import netgloo.models.Professor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.concurrent.ExecutionException;

/**
 * Created by estagiocit on 20/02/2017.
 */
@Repository
@Transactional
public class ProfessorDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Long create(Professor professor) {
        entityManager.persist(professor);
        return professor.getId();
    }

    public Professor getByCpf(Long cpf) {
        try {
            return (Professor) entityManager.createQuery("From Professor Where cpf = :cpf")
                    .setParameter("cpf", cpf).getSingleResult();

        }catch (Exception e) {
            return null;
        }
    }

    public Professor getById(long id) {
        return entityManager.find(Professor.class, id);
    }

    public void delete(Professor professor) {
        entityManager.remove(professor);
    }
}
