package pl.kaitoudev.urlittleurlshortener.component;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {
    public static final int BASE = 62;
    private static final String BASE_62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String encode(long value) {
        StringBuilder result = new StringBuilder();
        while (value > 0) {
            result.append(BASE_62.charAt((int) (value % BASE)));
            value /= BASE;
        }
        return result.reverse().toString();
    }
}
