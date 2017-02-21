package netgloo.Dao;

import com.sun.javafx.binding.StringFormatter;
import netgloo.models.Aluno;
import netgloo.models.AlunoSearchForm;
import netgloo.models.User;
import org.hibernate.annotations.Table;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by estagiocit on 20/02/2017.
 */
@Repository
@Transactional
public class AlunoDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Aluno aluno) {
        entityManager.persist(aluno);
        return;
    }

    public void delete(Aluno aluno) {
        if (entityManager.contains(aluno))
            entityManager.remove(aluno);
        else
            entityManager.remove(entityManager.merge(aluno));
        return;
    }

    public List<Aluno> getByName(String nome) {

        return (List<Aluno>) entityManager.createQuery(
                "from ALUNOS where nome like :%nome%")
                .setParameter("nome", nome)
                .getResultList();
    }

    public Aluno getBySex(char sex) {

        return (Aluno) entityManager.createQuery(
                "from ALUNOS where sexo = :sexo")
                .setParameter("sexo", sex)
                .getSingleResult();
    }

    public Aluno getByCpf(Long cpf) {
        try {
            return (Aluno) entityManager.createQuery(
                    "from Aluno where cpf = :cpf")
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    public List<Aluno> getByDate(Date data) {

        return (List<Aluno>) entityManager.createQuery(
                "from ALUNOS where data like :data")
                .setParameter("data", data)
                .getResultList();
    }
/*
    public List<Aluno> getAllSubject(Long id){

        return (List<Aluno>) entityManager.createQuery(
                "from DISCIPLINAS where id = :id")
                .setParameter("id", id)
                .getResultList();
    } */

    public void update(Aluno aluno) {
        entityManager.createQuery(
                    "UPDATE Aluno SET " +
                            "nome = :nome," +
                            "dataNascimento = :dataNascimento," +
                            "sexo = :sexo," +
                            "email = :email, " +
                            "idEndereco = :idEndereco" +
                            " WHERE idAluno = :id").
                    setParameter("nome", aluno.getNome()).
                    setParameter("dataNascimento", aluno.getDataNascimento()).
                    setParameter("sexo", aluno.getSexo()).
                    setParameter("email", aluno.getEmail()).
                    setParameter("idEndereco", aluno.getIdEndereco()).
                    setParameter("id", aluno.getIdAluno()).executeUpdate();
    }


    public Aluno getById(long id) {
        return entityManager.find(Aluno.class, id);
    }

    public List<Aluno> search(boolean nome, boolean dataNascimento, boolean sexo, AlunoSearchForm alunoSearchForm) {
        StringBuilder stb = new StringBuilder();
        stb.append("FROM Aluno WHERE 1=1 ");

        if(nome)
            stb.append("AND nome = :nome ");
        if(dataNascimento)
            stb.append("AND dataNascimento= :dataNascimento ");
        if(sexo)
            stb.append("AND sexo = :sexo ");

        Query query = entityManager.createQuery(stb.toString());
        if(nome)
            query = query.setParameter("nome",alunoSearchForm.getNome());
        if(dataNascimento)
            query = query.setParameter("dataNascimento",alunoSearchForm.getDataNascimento());
        if(sexo)
            query = query.setParameter("sexo",alunoSearchForm.getSexo());

        return query.getResultList();


    }
}
