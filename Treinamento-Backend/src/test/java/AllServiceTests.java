import netgloo.Service.AlunoServiceTest;
import netgloo.Service.DisciplinaServiceTest;
import netgloo.Service.ProfessorServiceTest;
import netgloo.Service.UserServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by estagiocit on 24/02/2017.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { AlunoServiceTest.class,
        DisciplinaServiceTest.class,
        ProfessorServiceTest.class,
        UserServiceTest.class})
public class AllServiceTests {
}
