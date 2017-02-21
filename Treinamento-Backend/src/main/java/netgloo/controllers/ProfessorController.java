package netgloo.controllers;

import netgloo.Excecoes.EnderecoInvalidoException;
import netgloo.Excecoes.ProfessorInvalidoException;
import netgloo.Excecoes.ProfessorJaCadastradoException;
import netgloo.Service.ProfessorService;
import netgloo.models.Professor;
import netgloo.models.ProfessorCreateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        }
    }
}
