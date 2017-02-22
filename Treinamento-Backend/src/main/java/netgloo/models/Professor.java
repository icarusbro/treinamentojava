package netgloo.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by estagiocit on 20/02/2017.
 */
@Entity
@Table(name="PROFESSORES")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idProfessor;

    @NotNull
    private Long cpf;

    @NotNull
    private String nome;

    @NotNull
    private Date dataNascimento;

    @NotNull
    private Character sexo;

    private String biografia;

    private String email;

    private Long idEndereco;

    public Professor(Long cpf, String nome, Date dataNascimento, Character sexo, String biografia, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.biografia = biografia;
        this.email = email;
    }

    public Long getId() {
        return idProfessor;
    }

    public void setId(Long id) {
        this.idProfessor = id;
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

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
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

    public Professor(){}

    public Professor(Long id) {
        this.idProfessor = id;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }


    public boolean isValid(){
        if(this.cpf != null && this.cpf > 0 &&
                this.nome != null && !this.nome.isEmpty() &&
                this.dataNascimento != null &&
                this.sexo != null && this.sexo.toString().matches("[mMFf]") &&
                this.email != null && !this.email.isEmpty() &&
                this.biografia != null
                ){
            return true;
        }
        return false;
    }
}
