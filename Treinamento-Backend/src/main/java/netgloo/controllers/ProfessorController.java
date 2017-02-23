package netgloo.controllers;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import netgloo.Excecoes.*;
import netgloo.Service.ProfessorService;
import netgloo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by estagiocit on 20/02/2017.
 */
@Controller
@RequestMapping(value="/professor")
public class ProfessorController {

    @Autowired
    ProfessorService professorService;

    @RequestMapping(value="/create")
    @ResponseBody
    public String create(@RequestBody ProfessorCreateForm professorCreateForm) {
        try {
            professorService.create(professorCreateForm);
        } catch (ProfessorJaCadastradoException e) {
            return e.getMessage();
        } catch (EnderecoInvalidoException e) {
            return e.getMessage();
        } catch (ProfessorInvalidoException e) {
            return e.getMessage();
        }
        return "Ok!";
    }

    @RequestMapping(value="/delete")
    @ResponseBody
    public String delete(Long id){
        try{
            professorService.delete(id);
                return "Ok";
        } catch (ProfessorInvalidoException e) {
            return e.getMessage();
        }catch (DataIntegrityViolationException e){
            return "Professor possui associação com disciplina, e não pode ser deletado.";
        }
    }

    @RequestMapping(value="/update")
    @ResponseBody
    public String update (@RequestBody ProfessorUpdateForm professorUpdateForm) {
        try {
            professorService.update(professorUpdateForm);
        } catch (ProfessorInvalidoException e) {
            return e.getMessage();
        } catch (EnderecoInvalidoException e) {
            return e.getMessage();
        }
        return "Ok";
    }

    @RequestMapping(value="/search")
    @ResponseBody
    public List<Professor> search(@RequestBody ProfessorSearchForm professorSearchForm){
            return professorService.search(professorSearchForm);
    }

    @RequestMapping(value="/matricular")
    @ResponseBody
    public String matricular(@RequestBody ProfessorDisciplinaKey professorDisciplinaKey) {
        try {
            professorService.matricular(professorDisciplinaKey);
            return "ok";
        } catch (DisciplinaNaoExisteExeption e) {
            return e.getMessage();
        } catch (ProfessorNaoExisteException e) {
            return e.getMessage();
        } catch (ProfessorInvalidoException e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value="/desmatricular")
    @ResponseBody
    public String desmatricular(@RequestBody ProfessorDisciplinaKey professorDisciplinaKey) {
        try {
            professorService.desmatricular(professorDisciplinaKey);
            return "ok";
        } catch (MatriculaNaoExisteExeption e) {
            return e.getMessage();
        } catch (DisciplinaInvalidaException e) {
            return e.getMessage();
        }
    }
}
