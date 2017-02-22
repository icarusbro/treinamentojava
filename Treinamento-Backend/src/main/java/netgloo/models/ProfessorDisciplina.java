package netgloo.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by estagiocit on 22/02/2017.
 */
@Entity
@Table(name="PROFESSORDISCIPLINA")
public class ProfessorDisciplina implements Serializable{
    @ManyToOne
    @JoinColumn(name = "idProfessor")
    @Id
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "idDisciplina")
    @Id
    private Disciplina disciplina;

    public ProfessorDisciplina() {
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
