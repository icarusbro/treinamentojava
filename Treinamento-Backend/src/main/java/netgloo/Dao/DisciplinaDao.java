package netgloo.Dao;

import netgloo.models.Disciplina;
import netgloo.models.DisciplinaSearchForm;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by estagiocit on 17/02/2017.
 */
@Repository
@Transactional
public class DisciplinaDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Long create(Disciplina disciplina) {
        entityManager.persist(disciplina);
        return disciplina.getIdDisciplina();
    }

    public Disciplina getById(Long id) {
        return entityManager.find(Disciplina.class, id);
    }

    public void delete(Disciplina disciplina) {

        entityManager.remove(disciplina);
    }

    public void update(Disciplina disciplina) {
        entityManager.merge(disciplina);
    }

    public List<Disciplina> search(boolean nome, boolean codigo, DisciplinaSearchForm disciplinaSearchForm) {
        StringBuilder sb = new StringBuilder();

        sb.append("from Disciplina where 1 = 1");

        if (nome){
            sb.append(" and nome = :nome");
        }

        if(codigo){
            sb.append(" and codigo = :codigo");
        }

        Query query = entityManager.createQuery(sb.toString());

        if(nome)
            query = query.setParameter("nome",disciplinaSearchForm.getNome());
        if(codigo)
            query = query.setParameter("codigo",disciplinaSearchForm.getCodigo());

        return query.getResultList();
    }

    public List<Disciplina> getByProperty(String propriedade, String valor) {
        return entityManager.createQuery("from Disciplina where " + propriedade + " = :propriedade").setParameter("propriedade", valor).getResultList();

    }
}
