package netgloo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Represents an User for this web application.
 */
@Entity
@Table(name = "users")
public class User {

  // ------------------------
  // PRIVATE FIELDS
  // ------------------------
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;
  
  @NotNull
  private String email;
  
  @NotNull
  private String name;

  @NotNull
  private String login;

  @NotNull
  private String senha;

  private Date ultimoAcesso;

  // ------------------------
  // PUBLIC METHODS
  // ------------------------
  
  public User() { }

  public User(long id) { 
    this.id = id;
  }

  public User(String email, String name) {
    this.email = email;
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public void setId(long value) {
    this.id = value;
  }

  public String getEmail() {
    return email;
  }
  
  public void setEmail(String value) {
    this.email = value;
  }
  
  public String getName() {
    return name;
  }

  public void setName(String value) {
    this.name = value;
  }

  public String getSenha() {
    return senha;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public Date getUltimoAcesso() {
    return ultimoAcesso;
  }

  public void setUltimoAcesso(Date ultimoAcesso) {
    this.ultimoAcesso = ultimoAcesso;
  }

  public boolean isValid(){
    if(this.email != null && !this.email.isEmpty() &&
            this.name != null && !this.name.isEmpty() &&
            this.login != null && !this.login.isEmpty() &&
            this.senha != null && !this.senha.isEmpty() && this.senha.length() > 5){

        return true;
    }
    return false;
  }
} // class User
