package netgloo.Dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import netgloo.models.Professor;
import netgloo.models.ProfessorSearchForm;
import netgloo.util.Utils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
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

    public void update(Professor professorNovo) {
        entityManager.merge(professorNovo);
    }

    public List<Professor> search(boolean nome, boolean dataNascimento, boolean sexo, ProfessorSearchForm professorSearchForm) {
        StringBuilder stb = new StringBuilder();
        stb.append("FROM Professor WHERE 1=1 ");

        if(nome)
            stb.append("AND nome = :nome ");
        if(dataNascimento)
            stb.append("AND dataNascimento= :dataNascimento ");
        if(sexo)
            stb.append("AND sexo = :sexo ");

        Query query = entityManager.createQuery(stb.toString());
        if(nome)
            query = query.setParameter("nome",professorSearchForm.getNome());
        if(dataNascimento)
            query = query.setParameter("dataNascimento", Utils.dataZerada(professorSearchForm.getDataNascimento()));
        if(sexo)
            query = query.setParameter("sexo",professorSearchForm.getSexo());

        return query.getResultList();

    }
}
