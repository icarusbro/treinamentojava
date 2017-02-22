package netgloo.Dao;

import netgloo.models.AlunoDisciplina;
import netgloo.models.ProfessorDisciplina;
import netgloo.models.ProfessorDisciplinaKey;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by estagiocit on 22/02/2017.
 */
@Repository
@Transactional
public class ProfessorDisciplinaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(ProfessorDisciplina professorDisciplina) {

        entityManager.persist(professorDisciplina);
    }

    public void delete(ProfessorDisciplina professorDisciplina) {

        entityManager.remove(professorDisciplina);
    }

    public ProfessorDisciplina getByProfessorDisciplina(ProfessorDisciplinaKey professorDisciplinaKey) {

        try {
            return (ProfessorDisciplina) entityManager.createQuery("FROM ProfessorDisciplina WHERE idProfessor = :idProfessor and idDisciplina = :idDisciplina")
                    .setParameter("idProfessor", professorDisciplinaKey.getIdProfessor())
                    .setParameter("idDisciplina", professorDisciplinaKey.getIdDisciplina())
                    .getSingleResult();
        }catch(Exception e){
            return null;
        }
    }

    public List<ProfessorDisciplina> getByDisciplina(Long idDisciplina) {
        return entityManager.createQuery("from ProfessorDisciplina where idDisciplina = :id")
                .setParameter("id",idDisciplina)
                .getResultList();
    }

    public void removeAll(List<ProfessorDisciplina> professorDisciplinaList) {
        for(ProfessorDisciplina professorDisciplina : professorDisciplinaList){
            entityManager.remove(professorDisciplina);
        }
    }
}
