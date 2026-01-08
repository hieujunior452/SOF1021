package util;

import java.util.Base64;
import java.util.UUID;

public class XStr {

    public static boolean isBlank(String text) {
        return text == null || text.trim().length() == 0;
    }

    public static String valueOf(Object object) {
        return object == null ? "" : String.valueOf(object);
    }

    public static String encodeB64(String text) {
        byte[] data = text.getBytes();
        return Base64.getEncoder().encodeToString(data);
    }

    public static String decodeB64(String text) {
        byte[] data = Base64.getDecoder().decode(text);
        return new String(data);
    }

    public static String getKey(String... args) {
        if (args.length == 0) {
            args = new String[]{UUID.randomUUID().toString(), String.valueOf(System.currentTimeMillis())};
        }
        int hashCode = String.join("-", args).hashCode();
        String key = "XXXXXXXX" + Integer.toHexString(Math.abs(hashCode)).toUpperCase();
        return key.substring(key.length() - 8);
    }
}
