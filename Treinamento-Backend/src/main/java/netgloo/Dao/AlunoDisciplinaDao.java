package netgloo.Dao;

import netgloo.models.AlunoDisciplina;
import netgloo.models.AlunoDisciplinaKey;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by estagiocit on 21/02/2017.
 */
@Repository
@Transactional
public class AlunoDisciplinaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create (AlunoDisciplina alunoDisciplina){
        entityManager.persist(alunoDisciplina);
    }

    public List<AlunoDisciplina> getAll() {
        return entityManager.createQuery("from AlunoDisciplina").getResultList();
    }

    public List<AlunoDisciplina> getByAluno(Long id) {
        return entityManager.createQuery("from AlunoDisciplina where idAluno = :id").setParameter("id", id).getResultList();
    }

    public AlunoDisciplina getByAlunoDisciplina(Long idAluno, Long idDisciplina) {

        try {
            return (AlunoDisciplina) entityManager.createQuery("FROM AlunoDisciplina WHERE idAluno = :idAluno and idDisciplina = :idDisciplina")
                    .setParameter("idAluno", idAluno)
                    .setParameter("idDisciplina", idDisciplina)
                    .getSingleResult();
        }catch(Exception e){
            return null;
        }


    }

    public void delete(AlunoDisciplina alunoDisciplina) {

        entityManager.remove(alunoDisciplina);

    }

    public void update(AlunoDisciplina alunoDisciplina) {
        entityManager.merge(alunoDisciplina);
    }

    public List<AlunoDisciplina> getByDisciplina(Long idDisciplina) {
        return entityManager.createQuery("from AlunoDisciplina where idDisciplina = :id").setParameter("id", idDisciplina).getResultList();
    }

    public void removeAll(List<AlunoDisciplina> alunoDisciplinaList) {
        for(AlunoDisciplina alunoDisciplina : alunoDisciplinaList){
            entityManager.remove(alunoDisciplina);
        }

    }
}
