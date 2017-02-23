package netgloo.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by estagiocit on 17/02/2017.
 */

@Entity
@Table (name = "DISCIPLINAS")
public class Disciplina implements Serializable {

    @GeneratedValue (strategy = GenerationType.AUTO)
    @Id
    private Long idDisciplina;

    @NotNull
    private String codigo;

    @NotNull
    private String nome;

    @NotNull
    private String descricao;

    @NotNull
    private String sala;





    public Disciplina() {
    }

    public Disciplina(String nome) {
        this.nome = nome;
    }

    public Long getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(Long idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public boolean isValid(){
        if(this.codigo != null && this.codigo.length()<=5 && !this.codigo.isEmpty() &&
                this.nome != null && !this.nome.isEmpty() &&
                this.sala != null && !this.sala.isEmpty() &&
                this.descricao != null && !this.descricao.isEmpty()
                ){
            return true;
        }
        return false;
    }
}
