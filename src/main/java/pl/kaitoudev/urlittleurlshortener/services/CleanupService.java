package pl.kaitoudev.urlittleurlshortener.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kaitoudev.urlittleurlshortener.model.entity.ShortenedLink;
import pl.kaitoudev.urlittleurlshortener.repository.ShortenedLinkRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CleanupService {
    private final ShortenedLinkRepository shortenedLinkRepository;

    public List<ShortenedLink> getShortenedLinksOlderThan(LocalDate date) {
        return shortenedLinkRepository.findShortenedLinkByCreatedOnBefore(date);
    }

    public boolean removeShortenedLinks(List<ShortenedLink> shortenedLinks) {
        try {
            shortenedLinkRepository.deleteAll(shortenedLinks);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
