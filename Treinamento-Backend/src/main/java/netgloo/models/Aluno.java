package netgloo.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by estagiocit on 20/02/2017.
 */
@Entity
@Table(name = "ALUNOS")
public class Aluno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idAluno;
    @NotNull
    private String nome;
    @NotNull
    private Long cpf;
    @NotNull
    private Date dataNascimento;
    @NotNull
    private Character sexo;
    private String email;

    private Long idEndereco;

    public Aluno(){}
    public Aluno(Long id){this.idAluno = id;}

    public Aluno(Long cpf,String nome, Date dataNascimento, Character sexo, String email) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.email = email;
        this.cpf = cpf;
    }


    public Long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Long idAluno) {
        this.idAluno = idAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public boolean isValid(){
        if(this.cpf != null && this.cpf > 0 && this.nome != null && !this.nome.isEmpty() &&
                this.dataNascimento != null &&
                this.sexo != null && this.sexo.toString().matches("[mMFf]") &&
                this.email != null && !this.email.isEmpty()
                ){
            return true;
        }
        return false;
    }
}
