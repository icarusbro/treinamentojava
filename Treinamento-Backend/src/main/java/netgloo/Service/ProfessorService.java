package netgloo.Service;

import netgloo.Dao.AlunoDao;
import netgloo.Dao.EnderecoDao;
import netgloo.Dao.ProfessorDao;
import netgloo.Excecoes.EnderecoInvalidoException;
import netgloo.Excecoes.ProfessorInvalidoException;
import netgloo.Excecoes.ProfessorJaCadastradoException;
import netgloo.models.Aluno;
import netgloo.models.Endereco;
import netgloo.models.Professor;
import netgloo.models.ProfessorCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by estagiocit on 20/02/2017.
 */
@Service
public class ProfessorService {
    @Autowired
    ProfessorDao professorDao;
    @Autowired
    EnderecoDao enderecoDao;

    public boolean create(ProfessorCreateForm professorCreateForm) throws ProfessorJaCadastradoException, EnderecoInvalidoException, ProfessorInvalidoException {
       if (professorCreateForm.getCpf() != null &&
               professorDao.getByCpf(professorCreateForm.getCpf()) != null){

           throw new ProfessorJaCadastradoException("Professor já cadastrado.");
        }

        Professor professor = new Professor(
            professorCreateForm.getCpf(),
                professorCreateForm.getNome(),
                professorCreateForm.getDataNascimento(),
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

    public void search() {
        Professor professor = new Professor();
    }

    public boolean delete(Long id) throws ProfessorInvalidoException {
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
}
