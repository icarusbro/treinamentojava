package netgloo.controllers;

import netgloo.Excecoes.UserInvalidoException;
import netgloo.Excecoes.UserNaoCadastradoException;
import netgloo.Service.UserService;
import netgloo.models.User;

import netgloo.models.UserSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.soap.MTOM;
import java.util.List;

/**
 * Class UserController
 */
@Controller
@RequestMapping(value="/usuario")
public class UserController {
  // ------------------------
  // PRIVATE FIELDS
  // ------------------------

  // Wire the UserDao used inside this controller.
  @Autowired
  private UserService userService;


  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  /**
   * Create a new user with an auto-generated id and email and name as passed
   * values.
   */
  @RequestMapping(value="/create")
  @ResponseBody
  public String create(@RequestBody User user) {
    try {

      userService.create(user);
  } catch (UserInvalidoException e) {
      return e.getMessage();
    }

    return "User succesfully created!";
  }

  /**
   * Delete the user with the passed id.
   */
  @RequestMapping(value="/delete")
  @ResponseBody
  public String delete(long id) {
    try {

      if(userService.delete(id)){
        return "OK";
      }else{
        return "Errou!Usuário não existe";
      }
    } catch (UserNaoCadastradoException e) {
      return e.getMessage();
    }

    //return "User succesfully deleted!";
  }


  /**
   * Update the email and the name for the user indentified by the passed id.
   */
  @RequestMapping(value="/update")
  @ResponseBody
  public String update(@RequestBody User user) {
    try {
      boolean retorno = userService.update(user);
      if (!retorno) {
        return "Erro na criação do usuário";
      }
    } catch (UserNaoCadastradoException e) {
      return e.getMessage();
    }

    return "User succesfully updated!";
  }

  @RequestMapping(value="/search-user")
  @ResponseBody
  public List<User> search(@RequestBody UserSearchForm userSearchForm) {
    return userService.search(userSearchForm);
  }


} // class UserController
