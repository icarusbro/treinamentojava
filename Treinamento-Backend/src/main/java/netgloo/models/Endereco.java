package netgloo.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by estagiocit on 20/02/2017.
 */
@Entity
@Table(name="ENDERECOS")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String logradouro;

    @NotNull
    private String numero;

    @NotNull
    private int cep;

    @NotNull
    private String bairro;

    @NotNull
    private String cidade;

    @NotNull
    private String uf;


    public Endereco() {
    }

    public Endereco(String logradouro, String numero, int cep, String bairro, String cidade, String uf) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getCep() {
        return cep;
    }

    public void setCep(int cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public boolean isValid(){
        if (this.uf != null && !this.uf.isEmpty()
                && this.cidade != null && !this.cidade.isEmpty()
                && this.bairro != null && !this.bairro.isEmpty()
                && this.cep > 0
                && this.numero != null && !this.numero.isEmpty()
                && this.logradouro != null && !this.logradouro.isEmpty())
            return true;

        return false;
    }
}
