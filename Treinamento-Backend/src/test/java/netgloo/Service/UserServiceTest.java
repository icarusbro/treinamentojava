package netgloo.Service;

import netgloo.Dao.UserDao;
import netgloo.Excecoes.UserInvalidoException;
import netgloo.Excecoes.UserNaoCadastradoException;
import netgloo.models.User;
import netgloo.models.UserSearchForm;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.validation.constraints.AssertTrue;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by estagiocit on 24/02/2017.
 */
public class UserServiceTest {
    @Mock
    UserDao userDao;

    @InjectMocks
    UserService userService = new UserService();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createSuccess()throws Exception{
        User usuario = new User();
        usuario.setEmail("ahuauhuh@ahuahhua.com");
        usuario.setName("Joao das Neves");
        usuario.setLogin("inocente");
        usuario.setSenha("sabe de nada");
        Mockito.doNothing().when(userDao).create(usuario);

        Assert.assertTrue(userService.create(usuario));
    }
    @Test(expected = UserInvalidoException.class)
    public void createError()throws Exception{
        User usuario = new User();
        usuario.setEmail("ahuauhuh@ahuahhua.com");
        usuario.setName("Joao das Neves");
        usuario.setLogin("inocente");
        usuario.setSenha("sabe");


        userService.create(usuario);
        Assert.fail();
    }

    @Test
    public void deleteSuccess () throws Exception{
      Mockito.when(userDao.getById(1l)).thenReturn(new User());
       Mockito.doNothing().when(userDao).delete(new User());

        Assert.assertTrue( userService.delete(1l));
    }

    @Test(expected = UserNaoCadastradoException.class)
    public void deleteUserNaoCadastrado () throws Exception{
        Mockito.when(userDao.getById(1l)).thenReturn(null);

        userService.delete(1l);

        Assert.fail();
    }

    @Test
    public void updateSuccess() throws Exception {
        User user = new User();
        user.setId(1l);
        Mockito.when(userDao.getById(user.getId())).thenReturn(user);
        Mockito.doNothing().when(userDao).update(user);

        assertTrue(userService.update(user));
    }

    @Test(expected = UserNaoCadastradoException.class)
    public void updateUserNaoCadastrado() throws Exception {
        User user = new User();
        user.setId(1l);
        Mockito.when(userDao.getById(user.getId())).thenReturn(null);

        userService.update(user);
        Assert.fail();
    }

    @Test
    public void searchSuccess() throws Exception {

        UserSearchForm userSearchForm = new UserSearchForm();
        userSearchForm.setLogin("icaroc");
        userSearchForm.setNome("icaro");

        Mockito.when(userDao.search(true, true, userSearchForm)).thenReturn(new ArrayList<User>());

        Assert.assertTrue(userService.search(userSearchForm).size() == 0);

    }
}