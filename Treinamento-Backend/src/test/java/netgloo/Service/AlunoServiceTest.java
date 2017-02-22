package netgloo.Service;

import netgloo.Dao.AlunoDao;
import netgloo.Dao.AlunoDisciplinaDao;
import netgloo.Dao.DisciplinaDao;
import netgloo.Dao.EnderecoDao;
import netgloo.Excecoes.AlunoInvalidoException;
import netgloo.Excecoes.CpfJaCadastradoException;
import netgloo.Excecoes.EnderecoInvalidoException;
import netgloo.models.Aluno;
import netgloo.models.AlunoCreateForm;
import netgloo.models.AlunoUpdateForm;
import netgloo.models.Endereco;
import netgloo.util.Utils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

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
}