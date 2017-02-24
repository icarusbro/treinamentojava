package netgloo.Service;

import netgloo.Dao.DisciplinaDao;
import netgloo.Dao.UserDao;
import netgloo.Excecoes.UserInvalidoException;
import netgloo.Excecoes.UserNaoCadastradoException;
import netgloo.models.Disciplina;
import netgloo.models.User;
import netgloo.models.UserSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by estagiocit on 17/02/2017.
 */

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public boolean create(User user)throws UserInvalidoException{
        if(user.isValid()) {
            userDao.create(user);
            return true;
        }else
            throw new UserInvalidoException("Usuario invalido");
    }

    public boolean delete(Long id) throws UserNaoCadastradoException {
        User user = userDao.getById(id);

        if(user != null){
            userDao.delete(user);
            return true;
        }else{
            throw new UserNaoCadastradoException("Usuario nao cadastrado");
        }
    }

    public boolean update(User user) throws UserNaoCadastradoException {

        User checkUser = userDao.getById(user.getId());

        if (checkUser != null) {
            userDao.update(user);
            return true;
        } else {
            throw new UserNaoCadastradoException("Usuario nao cadastrado");
        }
    }

    public List<User> search (UserSearchForm userSearchForm){

        boolean nome = (userSearchForm.getNome()!=null && !userSearchForm.getNome().isEmpty());
        boolean login = (userSearchForm.getLogin()!=null && !userSearchForm.getLogin().isEmpty());

        return userDao.search(login,nome,userSearchForm);
    }
}
