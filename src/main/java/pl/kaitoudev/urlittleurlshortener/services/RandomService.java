package pl.kaitoudev.urlittleurlshortener.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class RandomService {
    private final SecureRandom secRandom;

    public RandomService() {
        secRandom = new SecureRandom();
    }

    public long randomLong(long fromInc, long to) {
        return secRandom.nextLong(fromInc, Math.min(to + 1, Long.MAX_VALUE));
    }
}
