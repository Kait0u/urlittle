package pl.kaitoudev.urlittleurlshortener.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.kaitoudev.urlittleurlshortener.component.Base62Encoder;
import pl.kaitoudev.urlittleurlshortener.model.entity.ShortenedLink;
import pl.kaitoudev.urlittleurlshortener.repository.ShortenedLinkRepository;

@Service
@AllArgsConstructor
public class ShortenerService {
    private static final long MIN_CODE_LENGTH = 4;
    private static final long MAX_CODE_LENGTH = 7;
    private static final long MIN_LONG = (long) Math.pow(Base62Encoder.BASE, MIN_CODE_LENGTH);
    private static final long MAX_LONG = (long) Math.pow(Base62Encoder.BASE, MAX_CODE_LENGTH);
    private static final int MAX_ATTEMPTS = 5;

    private static final Logger logger = LoggerFactory.getLogger(ShortenerService.class);

    private RandomService randomService;
    private Base62Encoder base62Encoder;

    private ShortenedLinkRepository shortenedLinkRepository;


    @Transactional
    public ShortenedLink shortenLink(String url) {
        ShortenedLink shortenedLink = findShortenedLinkByActualUrl(url);
        if (shortenedLink != null)
            return shortenedLink;

        shortenedLink = new ShortenedLink();
        shortenedLink.setActualUrl(url);

        ShortenedLink result = null;
        for (int attemptIdx = 1; result == null && attemptIdx <= MAX_ATTEMPTS; ++attemptIdx) {
            String randomCode = generateCode();
            try {
                shortenedLink.setShortenedCode(randomCode);
                result = shortenedLinkRepository.save(shortenedLink);
            } catch (Exception e) {
                logger.info("Failed to save url \"{}\" for code={}. Retrying - {} attempts left", url, randomCode, MAX_ATTEMPTS - attemptIdx);
            }
        }

        return result;
    }

    private String generateCode() {
        long randLong = randomService.randomLong(MIN_LONG, MAX_LONG);
        return base62Encoder.encode(randLong);
    }

    public ShortenedLink findShortenedLinkByActualUrl(String actualUrl) {
        return shortenedLinkRepository.findByActualUrl(actualUrl);
    }

    public ShortenedLink findShortenedLinkByShortenedCode(String shortenedCode) {
        return shortenedLinkRepository.findByShortenedCode(shortenedCode);
    }
}
