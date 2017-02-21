package netgloo.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by estagiocit on 21/02/2017.
 */
public class Utils {
    public static Date dataZerada(Date data){
        Calendar calendario = new GregorianCalendar();
        calendario.setTime(data);
        calendario.set(Calendar.HOUR,0);
        calendario.set(Calendar.MINUTE,0);
        calendario.set(Calendar.SECOND,0);
        calendario.set(Calendar.MILLISECOND,0);

        return calendario.getTime();
    }
}
