package netgloo.Service;

import netgloo.Dao.AlunoDao;
import netgloo.Dao.AlunoDisciplinaDao;
import netgloo.Dao.DisciplinaDao;
import netgloo.Dao.ProfessorDisciplinaDao;
import netgloo.Excecoes.*;
import netgloo.models.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by estagiocit on 24/02/2017.
 */
public class DisciplinaServiceTest {
    @Mock
    DisciplinaDao disciplinaDao;

    @Mock
    AlunoDao alunoDao;

    @Mock
    AlunoDisciplinaDao alunoDisciplinaDao;

    @Mock
    ProfessorDisciplinaDao professorDisciplinaDao;

    @InjectMocks
    DisciplinaService disciplinaService = new DisciplinaService();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createComSucesso() throws Exception{

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("12345");
        disciplina.setNome("nome");
        disciplina.setSala("sala");
        disciplina.setDescricao("sjpdsaod");

        Mockito.when(disciplinaDao.getByProperty("codigo",disciplina.getCodigo())).thenReturn(null);
        Mockito.when(disciplinaDao.create(disciplina)).thenReturn(1l);
        Assert.assertTrue(disciplinaService.create(disciplina));
    }

    @Test(expected = DisciplinaJaCadastrada.class)
    public void createDisciplinaExistente() throws Exception{
        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("12345");
        disciplina.setNome("nome");
        disciplina.setSala("sala");
        disciplina.setDescricao("sjpdsaod");

        List<Disciplina> disciplinaList = new ArrayList<Disciplina>();
        disciplinaList.add(new Disciplina());

        Mockito.when(disciplinaDao.getByProperty("codigo",disciplina.getCodigo())).thenReturn(disciplinaList);
        disciplinaService.create(disciplina);
        Assert.fail();
    }

    @Test(expected = DisciplinaInvalidaException.class)
    public void createDisciplinaInvalida() throws Exception{
        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("12345");
        disciplina.setNome("nome");
        disciplina.setDescricao("sjpdsaod");

        Mockito.when(disciplinaDao.getByProperty("codigo",disciplina.getCodigo())).thenReturn(null);
        disciplinaService.create(disciplina);
        Assert.fail();
    }

    @Test
    public void deleteSucesso() throws Exception{

        Mockito.when(disciplinaDao.getById(1l)).thenReturn(new Disciplina());

        Assert.assertTrue(disciplinaService.delete(1L));
    }

    @Test(expected = DisciplinaInvalidaException.class)
    public void deleteDisciplinaInexistente() throws Exception{

        Mockito.when(disciplinaDao.getById(1l)).thenReturn(null);
        disciplinaService.delete(1l);
        Assert.fail();
    }

    @Test
    public void updateSuccess() throws Exception{
        Disciplina disciplinaBase = new Disciplina();
        disciplinaBase.setCodigo("2131");

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("2131");
        disciplina.setNome("nome");
        disciplina.setDescricao("sjpdsaod");
        disciplina.setIdDisciplina(1l);

        Mockito.when(disciplinaDao.getById(1l)).thenReturn(disciplinaBase);
        Mockito.doNothing().when(disciplinaDao).update(disciplina);

        Assert.assertTrue(disciplinaService.update(disciplina));
    }

    @Test(expected = DisciplinaInvalidaException.class)
    public void updateDisciplinaInvalida() throws Exception{
        Disciplina disciplinaBase = new Disciplina();
        disciplinaBase.setCodigo("2131");

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("2131");
        disciplina.setNome("nome");
        disciplina.setDescricao("sjpdsaod");
        disciplina.setIdDisciplina(1l);

        Mockito.when(disciplinaDao.getById(1l)).thenReturn(null);
        Mockito.doNothing().when(disciplinaDao).update(disciplina);
        disciplinaService.update(disciplina);
        Assert.fail();
    }

    @Test(expected = DisciplinaInvalidaException.class)
    public void updateDisciplinaCodigoNaoAlteravel() throws Exception{
        Disciplina disciplinaBase = new Disciplina();
        disciplinaBase.setCodigo("2131");

        Disciplina disciplina = new Disciplina();
        disciplina.setCodigo("2132");
        disciplina.setNome("nome");
        disciplina.setDescricao("sjpdsaod");
        disciplina.setIdDisciplina(1l);

        Mockito.when(disciplinaDao.getById(1l)).thenReturn(disciplinaBase);
        disciplinaService.update(disciplina);
        Assert.fail();
    }

    @Test
    public void salvarNotaSucesso () throws Exception {
        NotasDisciplina notasDisciplina = new NotasDisciplina();
        notasDisciplina.setIdAluno(1l);
        notasDisciplina.setIdDisciplina(2l);
        notasDisciplina.setNota(10l);

        Mockito.when(alunoDao.getById(notasDisciplina.getIdAluno())).thenReturn(new Aluno());
        Mockito.when(disciplinaDao.getById(notasDisciplina.getIdDisciplina())).thenReturn(new Disciplina());

        AlunoDisciplina alunoDisciplina = new AlunoDisciplina();
        Mockito.when(alunoDisciplinaDao.getByAlunoDisciplina(notasDisciplina.getIdAluno(), notasDisciplina.getIdDisciplina())).thenReturn(alunoDisciplina);

        Mockito.doNothing().when(alunoDisciplinaDao).update(alunoDisciplina);
        Assert.assertTrue(disciplinaService.salvarNotas(notasDisciplina));

    }

    @Test(expected = DadosInvalidaException.class)
    public void salvarNotaAlunoDisciplinaInvalido () throws Exception{

        NotasDisciplina notasDisciplina = new NotasDisciplina();
        notasDisciplina.setIdAluno(1l);
        notasDisciplina.setIdDisciplina(-2l);
        notasDisciplina.setNota(-10l);
        disciplinaService.salvarNotas(notasDisciplina);
        Assert.fail();
    }

    @Test(expected = AlunoInvalidoException.class)
    public void salvarNotaAlunoInvalido() throws Exception{

        NotasDisciplina notasDisciplina = new NotasDisciplina();
        notasDisciplina.setIdAluno(1l);
        notasDisciplina.setIdDisciplina(2l);
        notasDisciplina.setNota(10l);

        Mockito.when(alunoDao.getById(1l)).thenReturn(null);
        disciplinaService.salvarNotas(notasDisciplina);

        Assert.fail();

    }


    @Test(expected = DisciplinaInvalidaException.class)
    public void salvarNotaDisciplinaInexistente() throws Exception{

        NotasDisciplina notasDisciplina = new NotasDisciplina();
        notasDisciplina.setIdAluno(1l);
        notasDisciplina.setIdDisciplina(2l);
        notasDisciplina.setNota(10l);

        Mockito.when(alunoDao.getById(1l)).thenReturn(new Aluno());
        Mockito.when(disciplinaDao.getById(1l)).thenReturn(null);
        disciplinaService.salvarNotas(notasDisciplina);

        Assert.fail();

    }

    @Test(expected = AlunoDisciplinaInvalidoException.class)
    public void salvarNotaAlunoInexistente() throws Exception{

        NotasDisciplina notasDisciplina = new NotasDisciplina();
        notasDisciplina.setIdAluno(1l);
        notasDisciplina.setIdDisciplina(2l);
        notasDisciplina.setNota(10l);

        Mockito.when(alunoDao.getById(1l)).thenReturn(new Aluno());
        Mockito.when(disciplinaDao.getById(2l)).thenReturn(new Disciplina());
        Mockito.when(alunoDisciplinaDao.getByAlunoDisciplina(1l, 1l)).thenReturn(null);
        disciplinaService.salvarNotas(notasDisciplina);

        Assert.fail();

    }

    @Test
    public void searchSuccess() throws Exception{

        DisciplinaSearchForm disciplinaSearchForm = new DisciplinaSearchForm();
        disciplinaSearchForm.setNome("gustavo");
        disciplinaSearchForm.setCodigo("12345");

        Mockito.when(disciplinaDao.search(true,true,disciplinaSearchForm)).thenReturn(new ArrayList<Disciplina>());
        Assert.assertTrue(disciplinaService.search(disciplinaSearchForm).size() == 0);

    }

    @Test
    public void desvincularSuccess() throws Exception {

        Mockito.when(disciplinaDao.getById(1l)).thenReturn(new Disciplina());
        Mockito.when(alunoDisciplinaDao.getByDisciplina(1l)).thenReturn(new ArrayList<AlunoDisciplina>());
        Mockito.doNothing().when(alunoDisciplinaDao).removeAll(new ArrayList<AlunoDisciplina>());

        Mockito.when(professorDisciplinaDao.getByDisciplina(1l)).thenReturn(new ArrayList<ProfessorDisciplina>());
        Mockito.doNothing().when(professorDisciplinaDao).removeAll(new ArrayList<ProfessorDisciplina>());

        Assert.assertTrue(disciplinaService.desvincular(1l));
    }

    @Test(expected = DisciplinaInvalidaException.class)
    public void desvincularIdNaoExiste() throws Exception {
        Mockito.when(disciplinaDao.getById(1l)).thenReturn(null);
        disciplinaService.desvincular(1l);
        Assert.fail();
    }

    @Test(expected = DisciplinaInvalidaException.class)
    public void desvincularIdNulo() throws Exception {
        disciplinaService.desvincular(null);
        Assert.fail();
    }
}

