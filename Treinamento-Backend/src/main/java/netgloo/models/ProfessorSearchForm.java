package netgloo.models;

import java.util.Date;

/**
 * Created by estagiocit on 20/02/2017.
 */

public class ProfessorSearchForm {
    private String nome;
    private Date dataNascimento;
    private Character sexo;



    public ProfessorSearchForm() {
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
}
