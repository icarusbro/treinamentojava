package netgloo.controllers;

import netgloo.Excecoes.*;
import netgloo.Service.AlunoService;
import netgloo.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/aluno")
public class AlunoController {
    @Autowired
    AlunoService servico;
    @RequestMapping(value="/create",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> create(@RequestBody AlunoCreateForm aluno){
        try {
            servico.create(aluno);
            return ResponseEntity.status(HttpStatus.OK).body("OK");

        } catch (CpfJaCadastradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (EnderecoInvalidoException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        } catch (AlunoInvalidoException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @RequestMapping(value="/search")
    @ResponseBody
    public List<Aluno> search(@RequestBody AlunoSearchForm aluno){
        return servico.search(aluno);
    }

    @RequestMapping(value="/delete")
    @ResponseBody
    public String delete(Long id){
        try {
            if(servico.delete(id)){
                return "Ok";
            }
        } catch (AlunoInvalidoException e) {
            return e.getMessage();
        }catch (DataIntegrityViolationException e){
            return "Aluno possui associação com disciplina, e não pode ser deletado.";
        }
        return "Error!";
    }

    @RequestMapping(value="/update")
    @ResponseBody
    public String update(@RequestBody AlunoUpdateForm alunoUpdateForm){
        try {
            if(servico.update(alunoUpdateForm)){
                return "Ok";
            }
        } catch (AlunoInvalidoException e) {
            return e.getMessage();
        } catch (EnderecoInvalidoException e) {
            return e.getMessage();
        }
        return "Error";
    }
    @RequestMapping(value="/matricular")
    @ResponseBody
    public String matricular(@RequestBody AlunoDisciplinaKey alunoKey){
        try {
            servico.matricular(alunoKey);
            return "ok";
        } catch (AlunoNaoExisteExeption e) {
            return e.getMessage();
        } catch (DisciplinaNaoExisteExeption e) {
            return e.getMessage();
        } catch (AlunoInvalidoException e){
            return e.getMessage();
        }catch (Exception e){
            return "Ocorreu um erro SQL" + e.getMessage();
        }
    }

    @RequestMapping(value="/retrieve")
    @ResponseBody
    public List<Disciplina> retriveAll(Long id){

        return servico.getAllDisciplinas(id);

    }

    @RequestMapping(value="/desmatricular")
    @ResponseBody
    public String desmatricular(@RequestBody AlunoDisciplinaKey alunoDisciplinaKey){
        try {
            servico.desmatricular(alunoDisciplinaKey);
            return "Ok";
        } catch (DisciplinaInvalidaException e) {
            return  e.getMessage();
        } catch (MatriculaNaoExisteExeption e) {
            return  e.getMessage();
        } catch (DadosInvalidaException e) {
            return  e.getMessage();
        }
    }

    @RequestMapping(value="/enviarNota")
    @ResponseBody
    public String enviarNota(Long idAluno){
        try {
            return servico.enviarNota(idAluno);
        } catch (AlunoInvalidoException e) {
            return e.getMessage();
        } catch (DadosInvalidaException e) {
            return  e.getMessage();
        }
    }
}
