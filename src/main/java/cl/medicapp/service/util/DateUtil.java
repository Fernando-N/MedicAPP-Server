package cl.medicapp.service.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtil {

    private DateUtil() {
    }

    public static Date from(int day, int month, int year) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse((String.valueOf(day).concat("/").concat(String.valueOf(month)).concat("/").concat(String.valueOf(year))));
        } catch (ParseException e) {
            log.error("Error al parsear fecha", e);
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al parsear fecha");
        }
    }

    public static boolean differenceNowDate(Date target) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 15);
        Date nowWithFiftyMinutes = calendar.getTime();
        return target.before(nowWithFiftyMinutes);
    }

}
