package netgloo.Service;

import netgloo.Dao.DisciplinaDao;
import netgloo.Excecoes.DisciplinaInvalidaException;
import netgloo.Excecoes.DisciplinaJaCadastrada;
import netgloo.models.Disciplina;
import netgloo.models.DisciplinaSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by estagiocit on 17/02/2017.
 */

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaDao disciplinaDao;

    public boolean create(Disciplina disciplina) throws DisciplinaInvalidaException, DisciplinaJaCadastrada {
        List<Disciplina> disciplinas=disciplinaDao.getByProperty("codigo", disciplina.getCodigo());
        if(disciplinas !=null && disciplinas.size() > 0 ){
            throw new DisciplinaJaCadastrada("essa disciplina já existe");
        }
        if(disciplina.isValid()){
            disciplinaDao.create(disciplina);
            return true;
        } else{
            throw new DisciplinaInvalidaException("Disciplina Inválida");

        }
    }

    public boolean delete(Long idDisciplina) throws DisciplinaInvalidaException {

        Disciplina disciplina = disciplinaDao.getById(idDisciplina);

        if(disciplina != null) {
            disciplinaDao.delete(disciplina);
            return true;
        }else {
            throw new DisciplinaInvalidaException("Disciplina não encontrada");
        }
    }

    public boolean update(Disciplina disciplina) throws DisciplinaInvalidaException {

        Disciplina disciplinaBase = disciplinaDao.getById(disciplina.getIdDisciplina());

        if (disciplinaBase != null) {
            if(!disciplinaBase.getCodigo().equals(disciplina.getCodigo())){
                throw new DisciplinaInvalidaException("Codigo da disciplina nao pode ser modificado");
            }
            disciplinaDao.update(disciplina);
            return true;
        }else {
            throw new DisciplinaInvalidaException("Disciplina invalida");
        }
    }

    public List<Disciplina> search(DisciplinaSearchForm disciplinaSearchForm){
        boolean nome = disciplinaSearchForm.getNome() != null &&
                            !disciplinaSearchForm.getNome().isEmpty();
        boolean codigo = disciplinaSearchForm.getCodigo() != null &&
                !disciplinaSearchForm.getCodigo().isEmpty();

        return disciplinaDao.search(nome, codigo, disciplinaSearchForm);
    }
}
