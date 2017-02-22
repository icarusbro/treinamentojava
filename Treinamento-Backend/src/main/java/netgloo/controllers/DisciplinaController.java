package netgloo.controllers;

/**
 * Created by estagiocit on 17/02/2017.
 */

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import netgloo.Dao.DisciplinaDao;
import netgloo.Excecoes.AlunoDisciplinaInvalidoException;
import netgloo.Excecoes.AlunoInvalidoException;
import netgloo.Excecoes.DisciplinaInvalidaException;
import netgloo.Excecoes.DisciplinaJaCadastrada;
import netgloo.Service.DisciplinaService;
import netgloo.models.Disciplina;
import netgloo.models.DisciplinaSearchForm;
import netgloo.models.NotasDisciplina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Class DisciplinaController
 */
@Controller
@RequestMapping(value="/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService disciplinaService;

    @RequestMapping(value="/create")
    @ResponseBody
    public String create(@RequestBody Disciplina disciplina){

        try {
            disciplinaService.create(disciplina);
        } catch (DisciplinaInvalidaException e) {
            return e.getMessage();
        } catch (DisciplinaJaCadastrada e) {
            return e.getMessage();
        }
        return "Ok!";
    }

    @RequestMapping(value="/delete")
    @ResponseBody
    public String delete(Long id){
        try {
            disciplinaService.delete(id);
        } catch (DisciplinaInvalidaException e) {
            return e.getMessage();
        }catch (DataIntegrityViolationException e){
            return "Disciplina possui associações e não pode ser deletado!";
        }
        return "Ok!";
    }

    @RequestMapping(value="/update")
    @ResponseBody
    public String update(@RequestBody Disciplina disciplina){
        try {
            disciplinaService.update(disciplina);
        } catch (DisciplinaInvalidaException e) {
            return e.getMessage();
        }
        return "Ok!";
    }

    @RequestMapping(value="/search")
    @ResponseBody
    public List<Disciplina> search(@RequestBody DisciplinaSearchForm disciplinaSearchForm){
        return disciplinaService.search(disciplinaSearchForm);
    }

    @RequestMapping(value="/salvarNotas")
    @ResponseBody
    public String salvarNotas(@RequestBody NotasDisciplina notasDisciplina){
        try {
            disciplinaService.salvarNotas(notasDisciplina);
        } catch (AlunoInvalidoException e) {
            return e.getMessage();
        } catch (DisciplinaInvalidaException e) {
            return e.getMessage();
        } catch (AlunoDisciplinaInvalidoException e) {
            return e.getMessage();
        }
        return "Ok!";
    }

    @RequestMapping(value="/desvincular")
    @ResponseBody
    public String desvincular(Long idDisciplina) {
        try {
            disciplinaService.desvincular(idDisciplina);
            return "OK";
        } catch (DisciplinaInvalidaException e) {
            return e.getMessage();
        }
    }




}
;