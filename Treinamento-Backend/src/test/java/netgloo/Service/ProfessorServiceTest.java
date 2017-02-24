package netgloo.Service;

import netgloo.Dao.DisciplinaDao;
import netgloo.Dao.EnderecoDao;
import netgloo.Dao.ProfessorDao;
import netgloo.Dao.ProfessorDisciplinaDao;
import netgloo.Excecoes.*;
import netgloo.models.*;
import netgloo.util.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by estagiocit on 23/02/2017.
 */
public class ProfessorServiceTest {

    @Mock
    ProfessorDao professorDao;
    @Mock
    EnderecoDao enderecoDao;
    @Mock
    DisciplinaDao disciplinaDao;
    @Mock
    ProfessorDisciplinaDao professorDisciplinaDao;

    @InjectMocks
    ProfessorService professorService = new ProfessorService();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createSuccess() throws Exception {
        ProfessorCreateForm professorCreateForm = new ProfessorCreateForm();

        professorCreateForm.setCpf(99999988l);
        professorCreateForm.setNome("Tonho");
        professorCreateForm.setDataNascimento(new Date());
        professorCreateForm.setSexo('M');
        professorCreateForm.setEmail("teste@email.com");
        professorCreateForm.setBiografia("Biografia teste");


        Professor professor = new Professor(professorCreateForm.getCpf(),
                professorCreateForm.getNome(),
                Utils.dataZerada(professorCreateForm.getDataNascimento()),
                professorCreateForm.getSexo(),
                professorCreateForm.getEmail(),
                professorCreateForm.getBiografia());

        Mockito.when(professorDao.getByCpf(99999988l)).thenReturn(null);
        Mockito.when(professorDao.create(professor)).thenReturn(1l);

        Assert.assertTrue(professorService.create(professorCreateForm));
    }

    @Test
    public void createSuccessEnd() throws Exception{
        ProfessorCreateForm professorCreateForm = new ProfessorCreateForm();

        professorCreateForm.setCpf(99999988l);
        professorCreateForm.setNome("Tonho");
        professorCreateForm.setDataNascimento(new Date());
        professorCreateForm.setSexo('M');
        professorCreateForm.setEmail("teste@email.com");
        professorCreateForm.setBiografia("Biografia teste");
        professorCreateForm.setLogradouro("teste Logra");
        professorCreateForm.setNumero("33");
        professorCreateForm.setCep(123456789);
        professorCreateForm.setBairro("bairro teste");
        professorCreateForm.setCidade("cidade");
        professorCreateForm.setUf("uf");

        Endereco endereco = new Endereco(professorCreateForm.getLogradouro(),
                professorCreateForm.getNumero(),
                professorCreateForm.getCep(),
                professorCreateForm.getBairro(),
                professorCreateForm.getCidade(),
                professorCreateForm.getUf());


        Professor professor = new Professor(professorCreateForm.getCpf(),
                professorCreateForm.getNome(),
                Utils.dataZerada(professorCreateForm.getDataNascimento()),
                professorCreateForm.getSexo(),
                professorCreateForm.getEmail(),
                professorCreateForm.getBiografia());

        professor.setIdEndereco(1l);
        Mockito.when(professorDao.getByCpf(99999988l)).thenReturn(null);
        Mockito.when(professorDao.create(professor)).thenReturn(1l);
        Mockito.when(enderecoDao.create(endereco)).thenReturn(1l);


        Assert.assertTrue(professorService.create(professorCreateForm));
    }

    @Test(expected = ProfessorJaCadastradoException.class)
    public void createCpfJaCadastrado() throws Exception{

        ProfessorCreateForm professorCreateForm = new ProfessorCreateForm();
        professorCreateForm.setCpf(1l);
        Mockito.when(professorDao.getByCpf(1l)).thenReturn(new Professor());
        professorService.create(professorCreateForm);
        Assert.fail();

    }

    @Test(expected = EnderecoInvalidoException.class)
    public void createEnderecoInvalido() throws Exception{

        ProfessorCreateForm professorCreateForm = new ProfessorCreateForm();
        professorCreateForm.setCpf(12345678909L);
        professorCreateForm.setNome("teste");
        professorCreateForm.setDataNascimento(new Date());
        professorCreateForm.setSexo('M');
        professorCreateForm.setBiografia("Biografia");
        professorCreateForm.setEmail("teste@email.com");
        professorCreateForm.setLogradouro("teste Logra");
        professorCreateForm.setNumero("");

        Professor professor = new Professor(professorCreateForm.getCpf(),
                professorCreateForm.getNome(),
                Utils.dataZerada(professorCreateForm.getDataNascimento()),
                professorCreateForm.getSexo(),
                professorCreateForm.getEmail(),
                professorCreateForm.getBiografia());

        Mockito.when(professorDao.getByCpf(12345678909L)).thenReturn(null);

        professorService.create(professorCreateForm);

        Assert.fail();
    }

    @Test(expected = ProfessorInvalidoException.class)
    public void createProfessorInvalido() throws Exception{

        ProfessorCreateForm professorCreateForm = new ProfessorCreateForm();
        professorCreateForm.setCpf(12345678909L);
        professorCreateForm.setNome("teste");
        professorCreateForm.setDataNascimento(new Date());
        professorCreateForm.setSexo('O');
        professorCreateForm.setEmail("teste@email.com");
        professorCreateForm.setLogradouro("teste Logra");
        professorCreateForm.setNumero("");

        Professor professor = new Professor(professorCreateForm.getCpf(),
                professorCreateForm.getNome(),
                Utils.dataZerada(professorCreateForm.getDataNascimento()),
                professorCreateForm.getSexo(),
                professorCreateForm.getEmail(),
                professorCreateForm.getBiografia());

        Mockito.when(professorDao.getByCpf(12345678909L)).thenReturn(null);

        professorService.create(professorCreateForm);

        Assert.fail();
    }

    @Test
    public void deleteSuccessSemEnd() throws  Exception{

        Professor professor = new Professor();

        professor.setId(1l);
        professor.setIdEndereco(null);
        Mockito.when(professorDao.getById(professor.getId())).thenReturn(professor);
        Mockito.doNothing().when(professorDao).delete(professor);

        Assert.assertTrue(professorService.delete(professor.getId()));
    }

    @Test
    public void deleteSuccessComEnd() throws  Exception{

        Professor professor = new Professor();

        professor.setId(1l);
        professor.setIdEndereco(1l);
        Mockito.when(professorDao.getById(professor.getId())).thenReturn(professor);
        Mockito.doNothing().when(enderecoDao).delete(professor.getIdEndereco());
        Mockito.doNothing().when(professorDao).delete(professor);

        Assert.assertTrue(professorService.delete(professor.getId()));
    }

    @Test(expected = ProfessorInvalidoException.class)
    public void deleteProfessorNaoExiste() throws Exception{
        professorService.delete(1l);
        Mockito.when(professorDao.getById(1L)).thenReturn(null);
        Assert.fail();
    }

    @Test
    public void updateSemEndSemEnd () throws  Exception{
        ProfessorUpdateForm professorUpdateForm = new ProfessorUpdateForm();
        professorUpdateForm.setIdProfessor(1l);
        professorUpdateForm.setCpf(12345678909L);
        professorUpdateForm.setNome("teste");
        professorUpdateForm.setDataNascimento(new Date());
        professorUpdateForm.setSexo('M');
        professorUpdateForm.setEmail("teste@email.com");
        professorUpdateForm.setBiografia("Biografia");

        Professor professor = new Professor(professorUpdateForm.getCpf(),
                professorUpdateForm.getNome(),
                Utils.dataZerada(professorUpdateForm.getDataNascimento()),
                professorUpdateForm.getSexo(),
                professorUpdateForm.getEmail(),
                professorUpdateForm.getBiografia());

        professor.setId(1l);
        professor.setIdEndereco(null);

        Mockito.when(professorDao.getById(1l)).thenReturn(professor);
        Mockito.doNothing().when(professorDao).update(professor);

        Assert.assertTrue(professorService.update(professorUpdateForm));
    }

    @Test
    public void updateSemEndComEnd () throws  Exception{
        ProfessorUpdateForm professorUpdateForm = new ProfessorUpdateForm();
        professorUpdateForm.setIdProfessor(1l);
        professorUpdateForm.setCpf(12345678909L);
        professorUpdateForm.setNome("teste");
        professorUpdateForm.setDataNascimento(new Date());
        professorUpdateForm.setSexo('M');
        professorUpdateForm.setEmail("teste@email.com");
        professorUpdateForm.setLogradouro("teste Logra");
        professorUpdateForm.setNumero("123");
        professorUpdateForm.setCep(123456789);
        professorUpdateForm.setBiografia("biografia");
        professorUpdateForm.setBairro("bairro teste");
        professorUpdateForm.setCidade("cidade");
        professorUpdateForm.setUf("uf");

        Professor professor = new Professor(professorUpdateForm.getCpf(),
                professorUpdateForm.getNome(),
                Utils.dataZerada(professorUpdateForm.getDataNascimento()),
                professorUpdateForm.getSexo(),
                professorUpdateForm.getEmail(),
                professorUpdateForm.getBiografia());
        Endereco endereco = new Endereco(professorUpdateForm.getLogradouro(),
                professorUpdateForm.getNumero(),
                professorUpdateForm.getCep(),
                professorUpdateForm.getBairro(),
                professorUpdateForm.getCidade(),
                professorUpdateForm.getUf());
        professor.setId(1l);
        professor.setIdEndereco(null);

        Mockito.when(professorDao.getById(1l)).thenReturn(professor);
        Mockito.when(enderecoDao.create(endereco)).thenReturn(1l);
        professor.setIdEndereco(1l);
        Mockito.doNothing().when(professorDao).update(professor);
        Assert.assertTrue(professorService.update(professorUpdateForm));
    }

    @Test
    public void updateComEndSemEnd() throws Exception {
        ProfessorUpdateForm professorUpdateForm = new ProfessorUpdateForm();
        professorUpdateForm.setIdProfessor(1l);
        professorUpdateForm.setCpf(12345678909L);
        professorUpdateForm.setNome("teste");
        professorUpdateForm.setDataNascimento(new Date());
        professorUpdateForm.setSexo('M');
        professorUpdateForm.setEmail("teste@email.com");
        professorUpdateForm.setBiografia("biografia");

        Professor professor = new Professor(professorUpdateForm.getCpf(),
                professorUpdateForm.getNome(),
                Utils.dataZerada(professorUpdateForm.getDataNascimento()),
                professorUpdateForm.getSexo(),
                professorUpdateForm.getEmail(),
                professorUpdateForm.getBiografia());
        Endereco endereco = new Endereco("teste Logra",
                "123",
                123456789,
                "bairro teste",
                "cidade",
                "uf");
        professor.setId(1l);
        professor.setIdEndereco(1l);
        endereco.setId(1l);

        Mockito.when(professorDao.getById(1l)).thenReturn(professor);
        Mockito.doNothing().when(enderecoDao).delete(1l);
        professor.setIdEndereco(null);
        Mockito.doNothing().when(professorDao).update(professor);

        Assert.assertTrue(professorService.update(professorUpdateForm));

    }

    @Test
    public void updateComEndComEnd() throws Exception {
        ProfessorUpdateForm professorUpdateForm = new ProfessorUpdateForm();
        professorUpdateForm.setIdProfessor(1l);
        professorUpdateForm.setCpf(12345678909L);
        professorUpdateForm.setNome("teste");
        professorUpdateForm.setDataNascimento(new Date());
        professorUpdateForm.setSexo('M');
        professorUpdateForm.setEmail("teste@email.com");
        professorUpdateForm.setLogradouro("teste Logra");
        professorUpdateForm.setBiografia("biografia");
        professorUpdateForm.setNumero("123");
        professorUpdateForm.setCep(123456789);
        professorUpdateForm.setBairro("bairro teste");
        professorUpdateForm.setCidade("cidade");
        professorUpdateForm.setUf("uf");

        Professor professor = new Professor(professorUpdateForm.getCpf(),
                professorUpdateForm.getNome(),
                Utils.dataZerada(professorUpdateForm.getDataNascimento()),
                professorUpdateForm.getSexo(),
                professorUpdateForm.getEmail(),
                professorUpdateForm.getBiografia());
        Endereco endereco = new Endereco(professorUpdateForm.getLogradouro(),
                professorUpdateForm.getNumero(),
                professorUpdateForm.getCep(),
                professorUpdateForm.getBairro(),
                professorUpdateForm.getCidade(),
                professorUpdateForm.getUf());
        professor.setId(1l);
        professor.setIdEndereco(1l);
        endereco.setId(1l);

        Mockito.when(professorDao.getById(1l)).thenReturn(professor);
        Mockito.doNothing().when(enderecoDao).update(endereco);
        Mockito.doNothing().when(professorDao).update(professor);

        Assert.assertTrue(professorService.update(professorUpdateForm));

    }

    @Test(expected = ProfessorInvalidoException.class)
    public void updateProfessorNaoEncontrado() throws Exception{
        ProfessorUpdateForm professorUpdateForm = new ProfessorUpdateForm();
        professorUpdateForm.setIdProfessor(1l);
        professorUpdateForm.setCpf(12345678909L);
        professorUpdateForm.setNome("teste");
        professorUpdateForm.setDataNascimento(new Date());
        professorUpdateForm.setSexo('M');
        professorUpdateForm.setBiografia("Biografia");
        professorUpdateForm.setEmail("teste@email.com");

        Mockito.when(professorDao.getById(2l)).thenReturn(null);
        professorService.update(professorUpdateForm);

        Assert.fail();
    }

    @Test(expected = EnderecoInvalidoException.class)
    public void EnderecoInvalido() throws Exception {

        ProfessorUpdateForm professorUpdateForm = new ProfessorUpdateForm();
        professorUpdateForm.setIdProfessor(1l);
        professorUpdateForm.setCpf(12345678909L);
        professorUpdateForm.setNome("teste");
        professorUpdateForm.setDataNascimento(new Date());
        professorUpdateForm.setSexo('M');
        professorUpdateForm.setBiografia("Biografia");
        professorUpdateForm.setEmail("teste@email.com");
        professorUpdateForm.setLogradouro("Logradouro");

        Mockito.when(professorDao.getById(1l)).thenReturn(new Professor());
        professorService.update(professorUpdateForm);

        Assert.fail();
    }

    @Test(expected = ProfessorInvalidoException.class)
    public void ProfessorInvalido()throws Exception{

        ProfessorUpdateForm professorUpdateForm = new ProfessorUpdateForm();
        professorUpdateForm.setIdProfessor(1l);
        professorUpdateForm.setCpf(12345678909L);
        professorUpdateForm.setNome("teste");
        professorUpdateForm.setDataNascimento(new Date());
        professorUpdateForm.setSexo('Q');
        professorUpdateForm.setEmail("teste@email.com");

        Mockito.when(professorDao.getById(1l)).thenReturn(new Professor());
        professorService.update(professorUpdateForm);

        Assert.fail();
    }

    @Test
    public void matricularSuccess() throws Exception{
        ProfessorDisciplinaKey professorDisciplinaKey = new ProfessorDisciplinaKey();
        professorDisciplinaKey.setIdProfessor(1l);
        professorDisciplinaKey.setIdDisciplina(1l);

        ProfessorDisciplina professorDisciplina = new ProfessorDisciplina();
        professorDisciplina.setProfessor(new Professor());
        professorDisciplina.setDisciplina(new Disciplina());

        Mockito.when(professorDao.getById(1l)).thenReturn(new Professor());
        Mockito.when(disciplinaDao.getById(1l)).thenReturn(new Disciplina());
        Mockito.doNothing().when(professorDisciplinaDao).create(professorDisciplina);

        Assert.assertTrue(professorService.matricular(professorDisciplinaKey));
    }

    @Test(expected = ProfessorNaoExisteException.class)
    public void matricularProfessorInvalido() throws Exception{
        ProfessorDisciplinaKey professorDisciplinaKey = new ProfessorDisciplinaKey();
        professorDisciplinaKey.setIdProfessor(1l);
        professorDisciplinaKey.setIdDisciplina(1l);

        Mockito.when(professorDao.getById(1l)).thenReturn(null);
        professorService.matricular(professorDisciplinaKey);

        Assert.fail();
    }

    @Test(expected = DisciplinaNaoExisteExeption.class)
    public void matricularDisciplinaInvalido() throws Exception{
        ProfessorDisciplinaKey professorDisciplinaKey = new ProfessorDisciplinaKey();
        professorDisciplinaKey.setIdProfessor(1l);
        professorDisciplinaKey.setIdDisciplina(1l);

        Mockito.when(disciplinaDao.getById(1l)).thenReturn(null);
        Mockito.when(professorDao.getById(1l)).thenReturn(new Professor());
        professorService.matricular(professorDisciplinaKey);

        Assert.fail();
    }

    @Test(expected = DadosInvalidaException.class)
    public void dadosInvalidos() throws Exception{
        ProfessorDisciplinaKey professorDisciplinaKey = new ProfessorDisciplinaKey();
        professorDisciplinaKey.setIdProfessor(1l);
        professorDisciplinaKey.setIdDisciplina(-1l);

        professorService.matricular(professorDisciplinaKey);

        Assert.fail();
    }

    @Test
    public void searchSuccess() throws Exception {

        ProfessorSearchForm professorSearchForm = new ProfessorSearchForm();
        professorSearchForm.setNome("teste");
        professorSearchForm.setDataNascimento(new Date());
        professorSearchForm.setSexo('M');

        Mockito.when(professorDao.search(true,true,
                true,professorSearchForm)).thenReturn(new ArrayList<Professor>());

        Assert.assertTrue(professorService.search(professorSearchForm).size() == 0);
    }

    @Test
    public void desmatricularSuccess() throws Exception{

        ProfessorDisciplinaKey professorDisciplinaKey = new ProfessorDisciplinaKey();
        professorDisciplinaKey.setIdProfessor(1l);
        professorDisciplinaKey.setIdDisciplina(1l);

        ProfessorDisciplina professorDisciplina = new ProfessorDisciplina();

        Mockito.when(professorDisciplinaDao.getByProfessorDisciplina(professorDisciplinaKey)).thenReturn(professorDisciplina);
        Mockito.doNothing().when(professorDisciplinaDao).delete(professorDisciplina);

        Assert.assertTrue(professorService.desmatricular(professorDisciplinaKey));

    }

    @Test (expected = MatriculaNaoExisteExeption.class)
    public void desmatricularProfessorNaoMatriculado() throws Exception{
        ProfessorDisciplinaKey professorDisciplinaKey = new ProfessorDisciplinaKey();
        professorDisciplinaKey.setIdProfessor(1l);
        professorDisciplinaKey.setIdDisciplina(1l);

        ProfessorDisciplina profesorDisciplina = new ProfessorDisciplina();

        Mockito.when(professorDao.getById(1l)).thenReturn(null);
        professorService.desmatricular(professorDisciplinaKey);

        Assert.fail();
    }


    @Test (expected = DadosInvalidaException.class)
    public void desmatricularProfessorDadosInvalidor() throws Exception{

        ProfessorDisciplinaKey professorDisciplinaKey = new ProfessorDisciplinaKey();
        professorDisciplinaKey.setIdProfessor(1l);
        professorDisciplinaKey.setIdDisciplina(-1l);

        professorService.desmatricular(professorDisciplinaKey);

        Assert.fail();
    }


}
