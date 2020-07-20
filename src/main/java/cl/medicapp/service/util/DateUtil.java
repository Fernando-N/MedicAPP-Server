package cl.medicapp.service.util;

import cl.medicapp.service.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Clase utilitaria para fechas
 */
@Slf4j
public class DateUtil {

    /**
     * Genera objeto Date a partir de un dia, mes y año
     * @param day dia
     * @param month mes
     * @param year año
     * @return Date
     */
    public static Date from(int day, int month, int year) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return formatter.parse((String.valueOf(day).concat("/").concat(String.valueOf(month)).concat("/").concat(String.valueOf(year))));
        } catch (ParseException e) {
            log.error(Constants.ERROR_DATE_PARSE, e);
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.ERROR_DATE_PARSE);
        }
    }

    /**
     * Formatea fecha en Date a String
     * @param date target
     * @return target como String
     */
    public static String from(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);
    }

    /**
     * Parsea String a Date
     * @param dateStr target
     * @return target como Date
     */
    public static Date from(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(dateStr);
        }catch (ParseException e) {
            log.error(Constants.ERROR_DATE_PARSE, e);
            throw GenericResponseUtil.buildGenericException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        }
    }

    /**
     * Obtiene ms de diferencia entre target y ahora
     * @param target target
     * @return ms de diferencia
     */
    public static boolean differenceNowDate(LocalDateTime target) {
        LocalDateTime now = LocalDateTime.now();
        now = now.plusMinutes(15);
        return false;//target.isBefore(now);
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private DateUtil() {
    }

}
