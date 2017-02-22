package netgloo.models;

/**
 * Created by estagiocit on 22/02/2017.
 */
public class NotasDisciplina {

    private Long idAluno;
    private Long idDisciplina;
    private Long nota;

    public NotasDisciplina() {
    }

    public NotasDisciplina(Long idAluno, Long idDisciplina, Long nota) {
        this.idAluno = idAluno;
        this.idDisciplina = idDisciplina;
        this.nota = nota;
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

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public Long getNota() {
        return nota;
    }

    public void setNota(Long nota) {
        this.nota = nota;
    }

    public boolean isValid(){
        if(this.idAluno != null && this.idAluno > 0 &&
                this.idDisciplina != null && this.idDisciplina > 0 &&
                this.nota != null && (this.nota >= 0 && this.nota <= 10)){
            return true;
        }
        return false;
    }
}
