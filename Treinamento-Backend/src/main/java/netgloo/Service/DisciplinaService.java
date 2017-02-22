package netgloo.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import netgloo.Dao.AlunoDao;
import netgloo.Dao.AlunoDisciplinaDao;
import netgloo.Dao.DisciplinaDao;
import netgloo.Dao.ProfessorDisciplinaDao;
import netgloo.Excecoes.AlunoDisciplinaInvalidoException;
import netgloo.Excecoes.AlunoInvalidoException;
import netgloo.Excecoes.DisciplinaInvalidaException;
import netgloo.Excecoes.DisciplinaJaCadastrada;
import netgloo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by estagiocit on 17/02/2017.
 */

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaDao disciplinaDao;

    @Autowired
    AlunoDao alunoDao;

    @Autowired
    AlunoDisciplinaDao alunoDisciplinaDao;

    @Autowired
    ProfessorDisciplinaDao professorDisciplinaDao;

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

    public boolean delete(Long idDisciplina) throws DisciplinaInvalidaException,DataIntegrityViolationException {

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

    public void salvarNotas(NotasDisciplina notasDisciplina) throws AlunoInvalidoException, DisciplinaInvalidaException, AlunoDisciplinaInvalidoException {
        if(notasDisciplina.isValid()){
            if(alunoDao.getById(notasDisciplina.getIdAluno()) == null){
                throw new AlunoInvalidoException("Aluno nao existe");
            }
            if(disciplinaDao.getById(notasDisciplina.getIdDisciplina()) == null){
                throw new DisciplinaInvalidaException("Disciplina nao existe");
            }
            AlunoDisciplina alunoDisciplina = alunoDisciplinaDao.getByAlunoDisciplina(notasDisciplina.getIdAluno(), notasDisciplina.getIdDisciplina());
            if(alunoDisciplina == null){
                throw new AlunoDisciplinaInvalidoException("Aluno não cadastrado nesta disciplina.");
            }

            alunoDisciplina.setNota(notasDisciplina.getNota());
            alunoDisciplinaDao.update(alunoDisciplina);


        } else {
            throw new AlunoDisciplinaInvalidoException("Dados inválidos");
        }
    }

    public void desvincular(Long idDisciplina) throws DisciplinaInvalidaException {
        if (idDisciplina == null)
            throw new DisciplinaInvalidaException("Disciplina inválida");
        if(disciplinaDao.getById(idDisciplina) == null)
            throw new DisciplinaInvalidaException("Disciplina inválida");

        List<AlunoDisciplina> alunoDisciplinaList = alunoDisciplinaDao.getByDisciplina(idDisciplina);
        alunoDisciplinaDao.removeAll(alunoDisciplinaList);


        List<ProfessorDisciplina> professorDisciplinaList = professorDisciplinaDao.getByDisciplina(idDisciplina);
        professorDisciplinaDao.removeAll(professorDisciplinaList);

    }
}
