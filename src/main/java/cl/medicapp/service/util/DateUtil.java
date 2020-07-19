package cl.medicapp.service.util;

import cl.medicapp.service.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
            log.error(Constants.ERROR_DATE_PARSE, e);
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_DATE_PARSE);
        }
    }

    public static String from(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    public static Date from(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(dateStr);
        }catch (ParseException e) {
            log.error(Constants.ERROR_DATE_PARSE, e);
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

    public static boolean differenceNowDate(LocalDateTime target) {
        LocalDateTime now = LocalDateTime.now();
        now = now.plusMinutes(15);
        return false;//target.isBefore(now);
    }

}
