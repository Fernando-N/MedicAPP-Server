package cl.medicapp.service.util;

/**
 * Clase utilitaria para Strings
 */
public class StringUtil {

    /**
     * Capitaliza todas las palabras de un String
     * @param target
     * @return
     */
    public static String capitalizeAllWords(String target) {
        String[] arr = target.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Constructor privado para no permitir crear instancias de la clase
     */
    private StringUtil() {

    }

}
