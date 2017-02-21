package netgloo.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

/**
 * Created by estagiocit on 21/02/2017.
 */
public class AlunoDisciplinaKey implements Serializable{
    private Long idAluno;
    private Long idDisciplina;

    public AlunoDisciplinaKey() {
    }

    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public boolean isValid(){
        if(this.idAluno != null && this.idAluno > 0 &&
                this.idDisciplina != null && this.idDisciplina > 0 ){
            return true;
        }
        return false;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }
}
