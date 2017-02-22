package netgloo.Dao;

import netgloo.models.Endereco;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by estagiocit on 20/02/2017.
 */
@Repository
@Transactional
public class EnderecoDao {
    @PersistenceContext
    private EntityManager entityManager;

    public Long create(Endereco endereco) {
        entityManager.persist(endereco);
        return endereco.getId();
    }

    public void update(Endereco endereco) {
        entityManager.merge(endereco);
        return;
    }

    public void delete(Long idEndereco) {
        Endereco endereco = this.getById(idEndereco);

        if(endereco != null){
            entityManager.remove(endereco);
        }
    }

    public Endereco getById(long id) {
        return entityManager.find(Endereco.class, id);
    }
}
