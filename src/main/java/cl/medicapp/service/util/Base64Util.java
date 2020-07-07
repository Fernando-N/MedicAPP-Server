package cl.medicapp.service.util;

import java.util.Base64;

public class Base64Util {

    private Base64Util () {

    }

    public static String decode(byte[] target) {
        return new String(Base64.getDecoder().decode(target));
    }

    public static byte[] encode(String target) {
        try {
            return Base64.getEncoder().encode(target.getBytes());
        }catch (Exception e) {
            throw e;
        }
    }


}
