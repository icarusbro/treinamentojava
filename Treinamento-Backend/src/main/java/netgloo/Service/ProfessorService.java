package netgloo.Service;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import netgloo.Dao.*;
import netgloo.Excecoes.*;
import netgloo.models.*;
import netgloo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by estagiocit on 20/02/2017.
 */
@Service
public class ProfessorService {
    @Autowired
    ProfessorDao professorDao;
    @Autowired
    EnderecoDao enderecoDao;
    @Autowired
    DisciplinaDao disciplinaDao;
    @Autowired
    ProfessorDisciplinaDao professorDisciplinaDao;

    public boolean create(ProfessorCreateForm professorCreateForm) throws ProfessorJaCadastradoException, EnderecoInvalidoException, ProfessorInvalidoException {
       if (professorCreateForm.getCpf() != null &&
               professorDao.getByCpf(professorCreateForm.getCpf()) != null){

           throw new ProfessorJaCadastradoException("Professor já cadastrado.");
        }

        Professor professor = new Professor(
            professorCreateForm.getCpf(),
                professorCreateForm.getNome(),
                Utils.dataZerada(professorCreateForm.getDataNascimento()),
                        professorCreateForm.getSexo(),
                        professorCreateForm.getBiografia(),
                        professorCreateForm.getEmail());

        Endereco endereco;
        Long idEndereco = null;

        if(professor.isValid()){
            if(temEndereco(professorCreateForm)){
                endereco = new Endereco(professorCreateForm.getLogradouro(),
                        professorCreateForm.getNumero(),
                        professorCreateForm.getCep(),
                        professorCreateForm.getBairro(),
                        professorCreateForm.getCidade(),
                        professorCreateForm.getUf());

                if (endereco.isValid()) {
                    idEndereco = enderecoDao.create(endereco);

                }else{

                    throw new EnderecoInvalidoException("Endereco inválido");
                }
            }
            professor.setIdEndereco(idEndereco);
            professorDao.create(professor);
        } else {
            throw new ProfessorInvalidoException("Professor inválido.");
        }
        return true;
    }

    private boolean temEndereco(ProfessorCreateForm professorCreateForm) {
        return professorCreateForm.getLogradouro() != null ||
                professorCreateForm.getNumero() != null ||
                professorCreateForm.getCep() != 0 ||
                professorCreateForm.getBairro() != null ||
                professorCreateForm.getCidade() != null ||
                professorCreateForm.getUf() != null;
    }

    private boolean temEndereco(ProfessorUpdateForm professorUpdateForm) {
        return professorUpdateForm.getLogradouro() != null ||
                professorUpdateForm.getNumero() != null ||
                professorUpdateForm.getCep() != 0 ||
                professorUpdateForm.getBairro() != null ||
                professorUpdateForm.getCidade() != null ||
                professorUpdateForm.getUf() != null;
    }

    public List<Professor> search(ProfessorSearchForm professorSearchForm) {

        boolean nome = (professorSearchForm.getNome()!=null && !professorSearchForm.getNome().isEmpty());
        boolean dataNascimento = (professorSearchForm.getDataNascimento()!=null);
        boolean sexo = (professorSearchForm.getSexo() != null);

        return professorDao.search(nome, dataNascimento, sexo, professorSearchForm);
    }

    public boolean delete(Long id) throws ProfessorInvalidoException,DataIntegrityViolationException {
        Professor professor = professorDao.getById(id);
        if(professor != null){
            if(professor.getIdEndereco() != null){
                enderecoDao.delete(professor.getIdEndereco());
            }
            professorDao.delete(professor);
            return true;
        } else {
            throw new ProfessorInvalidoException("Professor não existe");
        }
    }

    public boolean update(ProfessorUpdateForm professorUpdateForm) throws ProfessorInvalidoException, EnderecoInvalidoException {
        if(professorDao.getById(professorUpdateForm.getIdProfessor())== null){
            throw new ProfessorInvalidoException("Professor não encontrado");
        }

        Professor professorBase = professorDao.getById(professorUpdateForm.getIdProfessor());
        Professor professorNovo = new Professor(
                professorUpdateForm.getCpf(),
                professorUpdateForm.getNome(),
                Utils.dataZerada(professorUpdateForm.getDataNascimento()),
                professorUpdateForm.getSexo(),
                professorUpdateForm.getBiografia(),
                professorUpdateForm.getEmail());

        professorNovo.setId(professorUpdateForm.getIdProfessor());

        Endereco endereco;
        Long idEndereco = null;

        if (professorNovo.isValid()){
            if(temEndereco(professorUpdateForm)){
                endereco = new Endereco(
                        professorUpdateForm.getLogradouro(),
                        professorUpdateForm.getNumero(),
                        professorUpdateForm.getCep(),
                        professorUpdateForm.getBairro(),
                        professorUpdateForm.getCidade(),
                        professorUpdateForm.getUf()
                );

                if (endereco.isValid()){
                    if(professorBase.getIdEndereco() == null){
                        idEndereco = enderecoDao.create(endereco);
                        professorNovo.setIdEndereco(idEndereco);
                    }else{
                        endereco.setId(professorBase.getIdEndereco());
                        enderecoDao.update(endereco);
                        professorNovo.setIdEndereco(endereco.getId());
                    }
                }else{
                    throw  new EnderecoInvalidoException("Endereço inválido.");
                }
            }else{
                if(professorBase.getIdEndereco() != null){
                    enderecoDao.delete(professorBase.getIdEndereco());
                }
                professorNovo.setIdEndereco(null);
            }
            professorDao.update(professorNovo);
        } else {
            throw new ProfessorInvalidoException("Professor inválido.");
        }
        return true;
    }

    public boolean matricular(ProfessorDisciplinaKey professorDisciplinaKey) throws DisciplinaNaoExisteExeption, ProfessorNaoExisteException, ProfessorInvalidoException, DadosInvalidaException {

        if(professorDisciplinaKey.isValid()){
            Professor professor = professorDao.getById(professorDisciplinaKey.getIdProfessor());
            if (professor == null) {
                throw new ProfessorNaoExisteException("Professor informado não existe");
            }

            Disciplina disciplina = disciplinaDao.getById(professorDisciplinaKey.getIdDisciplina());
            if (disciplina == null) {
                throw new DisciplinaNaoExisteExeption("Disciplina informado não existe");
            }

            ProfessorDisciplina professorDisciplina = new ProfessorDisciplina();
            professorDisciplina.setProfessor(professor);
            professorDisciplina.setDisciplina(disciplina);

            professorDisciplinaDao.create(professorDisciplina);
            return true;

        } else {
            throw new DadosInvalidaException("Dados Inválidos");
        }

    }

    public boolean desmatricular(ProfessorDisciplinaKey professorDisciplinaKey) throws DisciplinaInvalidaException, MatriculaNaoExisteExeption, DadosInvalidaException {
        if(!professorDisciplinaKey.isValid())
            throw new DadosInvalidaException("Dados Inválidos");

        ProfessorDisciplina professorDisciplina = professorDisciplinaDao.getByProfessorDisciplina(professorDisciplinaKey);

        if(professorDisciplina != null){

            professorDisciplinaDao.delete(professorDisciplina);
            return true;
        }
        throw new MatriculaNaoExisteExeption("Matricula informada não existe") ;

    }
}
