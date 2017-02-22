package netgloo.models;

import java.io.Serializable;

/**
 * Created by estagiocit on 21/02/2017.
 */
public class ProfessorDisciplinaKey implements Serializable{
    private Long idProfessor;
    private Long idDisciplina;

    public ProfessorDisciplinaKey() {
    }

    public Long getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Long idProfessor) {
        this.idProfessor = idProfessor;
    }

    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public boolean isValid(){
        if(this.idProfessor != null && this.idProfessor > 0 &&
                this.idDisciplina != null && this.idDisciplina > 0 ){
            return true;
        }
        return false;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }
}
