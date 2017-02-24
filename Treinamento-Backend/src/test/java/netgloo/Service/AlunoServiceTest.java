package netgloo.Service;

import netgloo.Dao.AlunoDao;
import netgloo.Dao.AlunoDisciplinaDao;
import netgloo.Dao.DisciplinaDao;
import netgloo.Dao.EnderecoDao;
import netgloo.Excecoes.*;
import netgloo.models.*;
import netgloo.util.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by estagiocit on 22/02/2017.
 */
public class AlunoServiceTest {
    @Mock
    AlunoDao alunoDao;
    @Mock
    EnderecoDao enderecoDao;
    @Mock
    DisciplinaDao disciplinaDao;
    @Mock
    AlunoDisciplinaDao alunoDisciplinaDao;

    @InjectMocks
    AlunoService alunoService = new AlunoService();

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createSuccess() throws Exception {

        AlunoCreateForm alunoCreateForm = new AlunoCreateForm();
        alunoCreateForm.setCpf(12345678909L);
        alunoCreateForm.setNome("teste");
        alunoCreateForm.setDataNascimento(new Date());
        alunoCreateForm.setSexo('M');
        alunoCreateForm.setEmail("teste@email.com");

        Aluno aluno = new Aluno(alunoCreateForm.getCpf(),
                alunoCreateForm.getNome(),
                Utils.dataZerada(alunoCreateForm.getDataNascimento()),
                alunoCreateForm.getSexo(),
                alunoCreateForm.getEmail());

        Mockito.when(alunoDao.getByCpf(12345678909l)).thenReturn(null);
        Mockito.doNothing().when(alunoDao).create(aluno);


        Assert.assertTrue(alunoService.create(alunoCreateForm));
    }

    @Test
    public void createSuccessEnd() throws Exception{
        AlunoCreateForm alunoCreateForm = new AlunoCreateForm();
        alunoCreateForm.setCpf(12345678909L);
        alunoCreateForm.setNome("teste");
        alunoCreateForm.setDataNascimento(new Date());
        alunoCreateForm.setSexo('M');
        alunoCreateForm.setEmail("teste@email.com");
        alunoCreateForm.setLogradouro("teste Logra");
        alunoCreateForm.setNumero("1234");
        alunoCreateForm.setCep(123456789);
        alunoCreateForm.setBairro("bairro teste");
        alunoCreateForm.setCidade("cidade");
        alunoCreateForm.setUf("uf");

        Aluno aluno = new Aluno(alunoCreateForm.getCpf(),
                alunoCreateForm.getNome(),
                Utils.dataZerada(alunoCreateForm.getDataNascimento()),
                alunoCreateForm.getSexo(),
                alunoCreateForm.getEmail());

        Endereco endereco = new Endereco(alunoCreateForm.getLogradouro(),
                alunoCreateForm.getNumero(),
                alunoCreateForm.getCep(),
                alunoCreateForm.getBairro(),
                alunoCreateForm.getCidade(),
                alunoCreateForm.getUf());

        Mockito.when(alunoDao.getByCpf(12345678909l)).thenReturn(null);
        Mockito.when(enderecoDao.create(endereco)).thenReturn(1l);
        Mockito.doNothing().when(alunoDao).create(aluno);
        Assert.assertTrue(alunoService.create(alunoCreateForm));
    }

    @Test(expected = CpfJaCadastradoException.class)
    public void createCpfCadastrado() throws Exception{
        AlunoCreateForm alunoCreateForm = new AlunoCreateForm();
        alunoCreateForm.setCpf(12345678909L);

        Mockito.when(alunoDao.getByCpf(12345678909l)).thenReturn(new Aluno());

        alunoService.create(alunoCreateForm);
        Assert.fail();
    }

    @Test(expected = EnderecoInvalidoException.class)
    public void createEnderecoInvalido() throws  Exception{
        AlunoCreateForm alunoCreateForm = new AlunoCreateForm();
        alunoCreateForm.setCpf(12345678909L);
        alunoCreateForm.setNome("teste");
        alunoCreateForm.setDataNascimento(new Date());
        alunoCreateForm.setSexo('M');
        alunoCreateForm.setEmail("teste@email.com");
        alunoCreateForm.setLogradouro("teste Logra");
        alunoCreateForm.setNumero("");
        alunoCreateForm.setCep(123456789);
        alunoCreateForm.setBairro("bairro teste");
        alunoCreateForm.setCidade("cidade");
        alunoCreateForm.setUf("uf");

        Aluno aluno = new Aluno(alunoCreateForm.getCpf(),
                alunoCreateForm.getNome(),
                Utils.dataZerada(alunoCreateForm.getDataNascimento()),
                alunoCreateForm.getSexo(),
                alunoCreateForm.getEmail());

        Endereco endereco = new Endereco(alunoCreateForm.getLogradouro(),
                alunoCreateForm.getNumero(),
                alunoCreateForm.getCep(),
                alunoCreateForm.getBairro(),
                alunoCreateForm.getCidade(),
                alunoCreateForm.getUf());

        Mockito.when(alunoDao.getByCpf(12345678909l)).thenReturn(null);

        alunoService.create(alunoCreateForm);

        Assert.fail();
    }

    @Test(expected = AlunoInvalidoException.class)
    public void createAlunoInvalido() throws  Exception{
        AlunoCreateForm alunoCreateForm = new AlunoCreateForm();
        alunoCreateForm.setCpf(12345678909L);
        alunoCreateForm.setNome("teste");
        alunoCreateForm.setDataNascimento(new Date());
        alunoCreateForm.setSexo('O');
        alunoCreateForm.setEmail("teste@email.com");
        alunoCreateForm.setLogradouro("teste Logra");
        alunoCreateForm.setNumero("");
        alunoCreateForm.setCep(123456789);
        alunoCreateForm.setBairro("bairro teste");
        alunoCreateForm.setCidade("cidade");
        alunoCreateForm.setUf("uf");

        Mockito.when(alunoDao.getByCpf(12345678909l)).thenReturn(null);

        alunoService.create(alunoCreateForm);

        Assert.fail();
    }

    @Test
    public void deleteSuccessSemEnd() throws  Exception{

        Aluno aluno = new Aluno();

        aluno.setIdAluno(2341l);
        aluno.setIdEndereco(null);
        Mockito.when(alunoDao.getById(aluno.getIdAluno())).thenReturn(aluno);
        Mockito.doNothing().when(alunoDao).delete(aluno);

        Assert.assertTrue(alunoService.delete(aluno.getIdAluno()));
    }

    @Test
    public void deleteSuccessComEnd() throws  Exception{

        Aluno aluno = new Aluno();

        aluno.setIdAluno(2341l);
        aluno.setIdEndereco(1L);
        Mockito.doNothing().when(enderecoDao).delete(aluno.getIdEndereco());
        Mockito.when(alunoDao.getById(aluno.getIdAluno())).thenReturn(aluno);
        Mockito.doNothing().when(alunoDao).delete(aluno);

        Assert.assertTrue(alunoService.delete(aluno.getIdAluno()));
    }

    @Test(expected = AlunoInvalidoException.class)
    public void deleteFailAlunoNaoExiste() throws  Exception{
        Long id = 1l;

        Mockito.when(alunoDao.getById(id)).thenReturn(null);

        alunoService.delete(id);
        Assert.fail();
    }

    @Test
    public void updateSuccessSemEndSemEnd() throws Exception{
        AlunoUpdateForm alunoUpdateForm = new AlunoUpdateForm();
        alunoUpdateForm.setIdAluno(1l);
        alunoUpdateForm.setCpf(12345678909L);
        alunoUpdateForm.setNome("teste");
        alunoUpdateForm.setDataNascimento(new Date());
        alunoUpdateForm.setSexo('M');
        alunoUpdateForm.setEmail("teste@email.com");

        Aluno aluno = new Aluno(alunoUpdateForm.getCpf(),
                alunoUpdateForm.getNome(),
                Utils.dataZerada(alunoUpdateForm.getDataNascimento()),
                alunoUpdateForm.getSexo(),
                alunoUpdateForm.getEmail());

        aluno.setIdAluno(1l);
        aluno.setIdEndereco(null);

        Mockito.when(alunoDao.getById(1l)).thenReturn(aluno);
        Mockito.doNothing().when(alunoDao).update(aluno);
        Assert.assertTrue(alunoService.update(alunoUpdateForm));
    }

    @Test
    public void updateSuccessSemEndComEnd() throws Exception{
        AlunoUpdateForm alunoUpdateForm = new AlunoUpdateForm();
        alunoUpdateForm.setIdAluno(1l);
        alunoUpdateForm.setCpf(12345678909L);
        alunoUpdateForm.setNome("teste");
        alunoUpdateForm.setDataNascimento(new Date());
        alunoUpdateForm.setSexo('M');
        alunoUpdateForm.setEmail("teste@email.com");
        alunoUpdateForm.setLogradouro("teste Logra");
        alunoUpdateForm.setNumero("123");
        alunoUpdateForm.setCep(123456789);
        alunoUpdateForm.setBairro("bairro teste");
        alunoUpdateForm.setCidade("cidade");
        alunoUpdateForm.setUf("uf");

        Aluno aluno = new Aluno(alunoUpdateForm.getCpf(),
                alunoUpdateForm.getNome(),
                Utils.dataZerada(alunoUpdateForm.getDataNascimento()),
                alunoUpdateForm.getSexo(),
                alunoUpdateForm.getEmail());
        Endereco endereco = new Endereco(alunoUpdateForm.getLogradouro(),
                alunoUpdateForm.getNumero(),
                alunoUpdateForm.getCep(),
                alunoUpdateForm.getBairro(),
                alunoUpdateForm.getCidade(),
                alunoUpdateForm.getUf());
        aluno.setIdAluno(1l);
        aluno.setIdEndereco(null);

        Mockito.when(alunoDao.getById(1l)).thenReturn(aluno);
        Mockito.when(enderecoDao.create(endereco)).thenReturn(1l);
        aluno.setIdEndereco(1l);
        Mockito.doNothing().when(alunoDao).update(aluno);
        Assert.assertTrue(alunoService.update(alunoUpdateForm));
    }

    @Test
    public void updateComEndComEnd() throws Exception {
        AlunoUpdateForm alunoUpdateForm = new AlunoUpdateForm();
        alunoUpdateForm.setIdAluno(1l);
        alunoUpdateForm.setCpf(12345678909L);
        alunoUpdateForm.setNome("teste");
        alunoUpdateForm.setDataNascimento(new Date());
        alunoUpdateForm.setSexo('M');
        alunoUpdateForm.setEmail("teste@email.com");
        alunoUpdateForm.setLogradouro("teste Logra");
        alunoUpdateForm.setNumero("123");
        alunoUpdateForm.setCep(123456789);
        alunoUpdateForm.setBairro("bairro teste");
        alunoUpdateForm.setCidade("cidade");
        alunoUpdateForm.setUf("uf");

        Aluno aluno = new Aluno(alunoUpdateForm.getCpf(),
                alunoUpdateForm.getNome(),
                Utils.dataZerada(alunoUpdateForm.getDataNascimento()),
                alunoUpdateForm.getSexo(),
                alunoUpdateForm.getEmail());
        Endereco endereco = new Endereco(alunoUpdateForm.getLogradouro(),
                alunoUpdateForm.getNumero(),
                alunoUpdateForm.getCep(),
                alunoUpdateForm.getBairro(),
                alunoUpdateForm.getCidade(),
                alunoUpdateForm.getUf());
        aluno.setIdAluno(1l);
        aluno.setIdEndereco(1l);
        endereco.setId(1l);

        Mockito.when(alunoDao.getById(1l)).thenReturn(aluno);
        Mockito.doNothing().when(enderecoDao).update(endereco);
        Mockito.doNothing().when(alunoDao).update(aluno);

        Assert.assertTrue(alunoService.update(alunoUpdateForm));

    }

    @Test
    public void updateComEndSemEnd() throws Exception {
        AlunoUpdateForm alunoUpdateForm = new AlunoUpdateForm();
        alunoUpdateForm.setIdAluno(1l);
        alunoUpdateForm.setCpf(12345678909L);
        alunoUpdateForm.setNome("teste");
        alunoUpdateForm.setDataNascimento(new Date());
        alunoUpdateForm.setSexo('M');
        alunoUpdateForm.setEmail("teste@email.com");

        Aluno aluno = new Aluno(alunoUpdateForm.getCpf(),
                alunoUpdateForm.getNome(),
                Utils.dataZerada(alunoUpdateForm.getDataNascimento()),
                alunoUpdateForm.getSexo(),
                alunoUpdateForm.getEmail());
        Endereco endereco = new Endereco("teste Logra",
                "123",
                123456789,
                "bairro teste",
                "cidade",
                "uf");
        aluno.setIdAluno(1l);
        aluno.setIdEndereco(1l);
        endereco.setId(1l);

        Mockito.when(alunoDao.getById(1l)).thenReturn(aluno);
        Mockito.doNothing().when(enderecoDao).delete(1l);
        aluno.setIdEndereco(null);
        Mockito.doNothing().when(alunoDao).update(aluno);

        Assert.assertTrue(alunoService.update(alunoUpdateForm));

    }

    @Test(expected = AlunoInvalidoException.class)
    public void updateAlunoNaoEncontrado() throws Exception{
        AlunoUpdateForm alunoUpdateForm = new AlunoUpdateForm();
        alunoUpdateForm.setIdAluno(1l);
        alunoUpdateForm.setCpf(12345678909L);
        alunoUpdateForm.setNome("teste");
        alunoUpdateForm.setDataNascimento(new Date());
        alunoUpdateForm.setSexo('M');
        alunoUpdateForm.setEmail("teste@email.com");

        Mockito.when(alunoDao.getById(2l)).thenReturn(null);
        alunoService.update(alunoUpdateForm);

        Assert.fail();
    }

    @Test(expected = EnderecoInvalidoException.class)
    public void EnderecoInvalido() throws Exception {

        AlunoUpdateForm alunoUpdateForm = new AlunoUpdateForm();
        alunoUpdateForm.setIdAluno(1l);
        alunoUpdateForm.setCpf(12345678909L);
        alunoUpdateForm.setNome("teste");
        alunoUpdateForm.setDataNascimento(new Date());
        alunoUpdateForm.setSexo('M');
        alunoUpdateForm.setEmail("teste@email.com");
        alunoUpdateForm.setLogradouro("Logradouro");

        Mockito.when(alunoDao.getById(1l)).thenReturn(new Aluno());
        alunoService.update(alunoUpdateForm);

        Assert.fail();
    }

    @Test(expected = AlunoInvalidoException.class)
    public void AlunoInvalido()throws Exception{

        AlunoUpdateForm alunoUpdateForm = new AlunoUpdateForm();
        alunoUpdateForm.setIdAluno(1l);
        alunoUpdateForm.setCpf(12345678909L);
        alunoUpdateForm.setNome("teste");
        alunoUpdateForm.setDataNascimento(new Date());
        alunoUpdateForm.setSexo('Q');
        alunoUpdateForm.setEmail("teste@email.com");

        Mockito.when(alunoDao.getById(1l)).thenReturn(new Aluno());
        alunoService.update(alunoUpdateForm);

        Assert.fail();
    }
    @Test
    public void searchSuccess() throws Exception{

        AlunoSearchForm alunoSearchForm = new AlunoSearchForm();
        alunoSearchForm.setNome("teste");
        alunoSearchForm.setDataNascimento(new Date());
        alunoSearchForm.setSexo('M');

        Mockito.when(alunoDao.search(true,true,
                true,alunoSearchForm)).thenReturn(new ArrayList<Aluno>());

        Assert.assertTrue(alunoService.search(alunoSearchForm).size() == 0);

    }

    @Test
    public void matricularSucesso()throws Exception{
        AlunoDisciplinaKey alunoDisciplinaKey = new AlunoDisciplinaKey();
        alunoDisciplinaKey.setIdAluno(1l);
        alunoDisciplinaKey.setIdDisciplina(1l);

        AlunoDisciplina alunoDisciplina = new AlunoDisciplina();
        alunoDisciplina.setAluno(new Aluno());
        alunoDisciplina.setDisciplina(new Disciplina());

        Mockito.when(alunoDao.getById(1l)).thenReturn(new Aluno());
        Mockito.when(disciplinaDao.getById(alunoDisciplinaKey.getIdDisciplina())).thenReturn(new Disciplina());
        Mockito.doNothing().when(alunoDisciplinaDao).create(alunoDisciplina);

        assertTrue(alunoService.matricular(alunoDisciplinaKey));
    }

    @Test(expected = AlunoNaoExisteExeption.class)
    public void AlunoNaoExiste()throws Exception{

        AlunoDisciplinaKey alunoDisciplinaKey = new AlunoDisciplinaKey();
        alunoDisciplinaKey.setIdAluno(1l);
        alunoDisciplinaKey.setIdDisciplina(1l);

        Mockito.when(alunoDao.getById(1l)).thenReturn(null);
        alunoService.matricular(alunoDisciplinaKey);

        Assert.fail();

    }

    @Test(expected = DisciplinaNaoExisteExeption.class)
    public void DisciplinaNaoExiste()throws Exception{

        AlunoDisciplinaKey alunoDisciplinaKey = new AlunoDisciplinaKey();
        alunoDisciplinaKey.setIdAluno(1l);
        alunoDisciplinaKey.setIdDisciplina(1l);

        Mockito.when(disciplinaDao.getById(1l)).thenReturn(null);
        Mockito.when(alunoDao.getById(1l)).thenReturn(new Aluno());
        alunoService.matricular(alunoDisciplinaKey);

        Assert.fail();

    }

    @Test(expected = DadosInvalidaException.class)
    public void dadosInvalidos() throws Exception{

        AlunoDisciplinaKey alunoDisciplinaKey = new AlunoDisciplinaKey();
        alunoDisciplinaKey.setIdAluno(-7L);

        alunoService.matricular(alunoDisciplinaKey);
        Assert.fail();
    }

    @Test
    public void desmatricularComSucesso() throws Exception{
        AlunoDisciplinaKey alunoDisciplinaKey = new AlunoDisciplinaKey();
        alunoDisciplinaKey.setIdAluno(1l);
        alunoDisciplinaKey.setIdDisciplina(1l);

        AlunoDisciplina alunoDisciplina = new AlunoDisciplina();

        Mockito.when(alunoDisciplinaDao.getByAlunoDisciplina(1l, 1l)).thenReturn(alunoDisciplina);
        Mockito.doNothing().when(alunoDisciplinaDao).delete(alunoDisciplina);

        Assert.assertTrue(alunoService.desmatricular(alunoDisciplinaKey));
    }

    @Test (expected = MatriculaNaoExisteExeption.class)
    public void desmatricularAlunoNaoMatriculado() throws Exception{
        AlunoDisciplinaKey alunoDisciplinaKey = new AlunoDisciplinaKey();
        alunoDisciplinaKey.setIdAluno(1l);
        alunoDisciplinaKey.setIdDisciplina(1l);

        AlunoDisciplina alunoDisciplina = new AlunoDisciplina();

        Mockito.when(alunoDisciplinaDao.getByAlunoDisciplina(1l, 1l)).thenReturn(null);
        alunoService.desmatricular(alunoDisciplinaKey);

        Assert.fail();
    }

    @Test (expected = DadosInvalidaException.class)
    public void desmatricularDadosInvalidos() throws Exception{
        AlunoDisciplinaKey alunoDisciplinaKey = new AlunoDisciplinaKey();
        alunoDisciplinaKey.setIdAluno(1l);
        alunoDisciplinaKey.setIdDisciplina(-1l);

        alunoService.desmatricular(alunoDisciplinaKey);

        Assert.fail();
    }

    @Test
    public void enviarNotaSucess() throws Exception{
        Long id = 1l;
        List<AlunoDisciplina> alunoDisciplinaList = new ArrayList<>();
        alunoDisciplinaList.add(
                new AlunoDisciplina(
                        new Disciplina("Teste"),
                        8l
                )
        );
        Aluno aluno = new Aluno();
        aluno.setIdAluno(id);
        Mockito.when(alunoDao.getById(id)).thenReturn(aluno);
        Mockito.when(alunoDisciplinaDao.getByAluno(1l)).thenReturn(alunoDisciplinaList);

        Assert.assertEquals("Nome da Matéria: " + "Teste - Nota: 8\n", alunoService.enviarNota(1l));
    }

    @Test(expected = DadosInvalidaException.class)
    public void enviarNotaDadosInvalidos() throws Exception{

        alunoService.enviarNota(null);
        Assert.fail();

    }

    @Test(expected = AlunoInvalidoException.class)
    public void enviarNotaAlunoNaoExiste() throws Exception{

        Mockito.when(alunoDao.getById(1l)).thenReturn(null);
        alunoService.enviarNota(1l);
        Assert.fail();

    }
}

