package netgloo.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by estagiocit on 21/02/2017.
 */
@Entity
@Table(name = "ALUNODISCIPLINA")
public class AlunoDisciplina implements Serializable{

    @ManyToOne
    @JoinColumn(name = "idAluno")
    @Id
    private Aluno aluno;
    @ManyToOne
    @JoinColumn(name = "idDisciplina")
    @Id
    private Disciplina disciplina;
    private Long nota;

    public AlunoDisciplina() {
    }

    public AlunoDisciplina(Disciplina disciplina, Long nota) {
        this.disciplina = disciplina;
        this.nota = nota;
    }

    public Long getNota() {
        return nota;
    }

    public void setNota(Long nota) {
        this.nota = nota;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
