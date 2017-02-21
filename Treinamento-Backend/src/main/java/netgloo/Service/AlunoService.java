package netgloo.Service;

import netgloo.Dao.AlunoDao;
import netgloo.Dao.AlunoDisciplinaDao;
import netgloo.Dao.DisciplinaDao;
import netgloo.Dao.EnderecoDao;
import netgloo.Excecoes.*;
import netgloo.models.*;
import netgloo.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by estagiocit on 20/02/2017.
 */

@Service
public class AlunoService {
    @Autowired
    AlunoDao alunoDao;
    @Autowired
    EnderecoDao enderecoDao;
    @Autowired
    DisciplinaDao disciplinaDao;
    @Autowired
    AlunoDisciplinaDao alunoDisciplinaDao;

    public boolean create(AlunoCreateForm alunoCreateForm) throws CpfJaCadastradoException, EnderecoInvalidoException, AlunoInvalidoException {

        if(alunoDao.getByCpf(alunoCreateForm.getCpf()) != null){
            throw new CpfJaCadastradoException("CPF já cadastrado: " + alunoCreateForm.getCpf());

        }

        Aluno aluno = new Aluno(alunoCreateForm.getCpf(),
                                alunoCreateForm.getNome(),
                                Utils.dataZerada(alunoCreateForm.getDataNascimento()),
                                alunoCreateForm.getSexo(),
                                alunoCreateForm.getEmail());

        Endereco endereco;
        Long idEndereco = null;

        if(aluno.isValid()){
            if(temEndereco(alunoCreateForm)){
                endereco = new Endereco(alunoCreateForm.getLogradouro(),
                        alunoCreateForm.getNumero(),
                        alunoCreateForm.getCep(),
                        alunoCreateForm.getBairro(),
                        alunoCreateForm.getCidade(),
                        alunoCreateForm.getUf());

                if (endereco.isValid()) {
                    idEndereco = enderecoDao.create(endereco);

                }else{

                    throw new EnderecoInvalidoException("Endereco inválido");
                }

            }
            aluno.setIdEndereco(idEndereco);
            alunoDao.create(aluno);
        } else {
            throw new AlunoInvalidoException("Aluno invalido");
        }

        return  true;
    }

    private boolean temEndereco(AlunoCreateForm alunoCreateForm) {

        return alunoCreateForm.getLogradouro() != null ||
                alunoCreateForm.getNumero() != null ||
                alunoCreateForm.getCep() != 0 ||
                alunoCreateForm.getBairro() != null ||
                alunoCreateForm.getCidade() != null ||
                alunoCreateForm.getUf() != null;
    }

    private boolean temEndereco(AlunoUpdateForm alunoUpdateForm) {

        return alunoUpdateForm.getLogradouro() != null ||
                alunoUpdateForm.getNumero() != null ||
                alunoUpdateForm.getCep() != 0 ||
                alunoUpdateForm.getBairro() != null ||
                alunoUpdateForm.getCidade() != null ||
                alunoUpdateForm.getUf() != null;
    }

    public boolean delete(Long id) throws AlunoInvalidoException {
        Aluno aluno = alunoDao.getById(id);
        if(aluno != null){
            if(aluno.getIdEndereco() != null){
                enderecoDao.delete(aluno.getIdEndereco());
            }
            alunoDao.delete(aluno);
            return true;
        } else{

            throw new AlunoInvalidoException("Aluno não existe");
        }
    }

    public boolean update(AlunoUpdateForm alunoUpdateForm) throws AlunoInvalidoException, EnderecoInvalidoException {

        if(alunoDao.getById(alunoUpdateForm.getIdAluno()) == null){

            throw new AlunoInvalidoException("Cpf não encontrado");
        }

        Aluno alunoBase = alunoDao.getById(alunoUpdateForm.getIdAluno());

        Aluno aluno = new Aluno(alunoUpdateForm.getCpf(),
                                alunoUpdateForm.getNome(),
                                Utils.dataZerada(alunoUpdateForm.getDataNascimento()),
                                alunoUpdateForm.getSexo(),
                                alunoUpdateForm.getEmail());

        aluno.setIdAluno(alunoUpdateForm.getIdAluno());
        aluno.setIdEndereco(alunoBase.getIdEndereco());

        Endereco endereco;
        Long idEndereco = null;

        if(aluno.isValid()){
            if(temEndereco(alunoUpdateForm)){
                endereco = new Endereco(alunoUpdateForm.getLogradouro(),
                        alunoUpdateForm.getNumero(),
                        alunoUpdateForm.getCep(),
                        alunoUpdateForm.getBairro(),
                        alunoUpdateForm.getCidade(),
                        alunoUpdateForm.getUf());

                if (endereco.isValid()) {
                    if(alunoBase.getIdEndereco() == null){
                        idEndereco = enderecoDao.create(endereco);
                        aluno.setIdEndereco(idEndereco);
                    }else{
                        endereco.setId(alunoBase.getIdEndereco());
                        enderecoDao.update(endereco);
                    }
                }else{

                    throw new EnderecoInvalidoException("Endereço invalido");
                }

            }else{
                if(alunoBase.getIdEndereco() != null){
                    enderecoDao.delete(alunoBase.getIdEndereco());
                }
                aluno.setIdEndereco(null);
            }
            alunoDao.update(aluno);
        }else{
            throw new AlunoInvalidoException("Aluno invalido");
        }

        return  true;
    }

    public List<Aluno> search (AlunoSearchForm alunoSearchForm){

        boolean nome = (alunoSearchForm.getNome()!=null && !alunoSearchForm.getNome().isEmpty());
        boolean dataNascimento = (alunoSearchForm.getDataNascimento()!=null);
        boolean sexo = (alunoSearchForm.getSexo() != null);

        return alunoDao.search(nome, dataNascimento, sexo, alunoSearchForm);
    }

    public void matricular(AlunoDisciplinaKey alunoKey) throws AlunoNaoExisteExeption, DisciplinaNaoExisteExeption {

        if(alunoKey.isValid()){

            Aluno aluno = alunoDao.getById(alunoKey.getIdAluno());

            if (aluno == null) {
                throw new AlunoNaoExisteExeption("Aluno informado não existe");
            }

            Disciplina disciplina = disciplinaDao.getById(alunoKey.getIdDisciplina());
            if (disciplina == null) {
                throw new DisciplinaNaoExisteExeption("Disciplina informado não existe");
            }

            AlunoDisciplina alunoDisciplina = new AlunoDisciplina();
            alunoDisciplina.setAluno(aluno);
            alunoDisciplina.setDisciplina(disciplina);

            alunoDisciplinaDao.create(alunoDisciplina);


        }

    }


    public List<Disciplina> getAllDisciplinas(Long id) {
        List<AlunoDisciplina> alunoDisciplinaList = alunoDisciplinaDao.getByAluno(id);
        if (alunoDisciplinaList !=null && !alunoDisciplinaList.isEmpty()){
            List<Disciplina> disciplinaList = new ArrayList<>();
            for (AlunoDisciplina alunoDisciplina: alunoDisciplinaList) {
                disciplinaList.add(alunoDisciplina.getDisciplina());

            }
        return disciplinaList;
        } else{
            return null;
        }

    }

    public boolean desmatricular(AlunoDisciplinaKey alunoDisciplinaKey) throws DisciplinaInvalidaException, MatriculaNaoExisteExeption {
        if(!alunoDisciplinaKey.isValid())
            throw new DisciplinaInvalidaException("Aluno nao matriculado na disciplina");
        AlunoDisciplina alunoDisciplina = alunoDisciplinaDao.getByAlunoDisciplina(alunoDisciplinaKey);

        if(alunoDisciplina != null){

            alunoDisciplinaDao.delete(alunoDisciplina);
            return true;
        }
        throw new MatriculaNaoExisteExeption("Matricula informada não existe") ;

    }
}
