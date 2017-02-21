package netgloo.models;

/**
 * Created by estagiocit on 20/02/2017.
 */


public class UserSearchForm {
    private String nome;
    private String login;

    public UserSearchForm() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
