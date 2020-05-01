package cl.medicapp.service.util;

public class StringUtil {

    private StringUtil() {

    }

    public static String capitalizeAllWords(String target) {
        String[] arr = target.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).append(" ");
        }
        return sb.toString().trim();
    }

}
