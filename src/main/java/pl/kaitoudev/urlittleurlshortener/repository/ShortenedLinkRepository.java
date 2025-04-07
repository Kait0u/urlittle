package pl.kaitoudev.urlittleurlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kaitoudev.urlittleurlshortener.model.entity.ShortenedLink;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ShortenedLinkRepository extends JpaRepository<ShortenedLink, Long> {
    public ShortenedLink findByActualUrl(String actualUrl);
    public ShortenedLink findByShortenedCode(String shortenedCode);
    public List<ShortenedLink> findShortenedLinkByCreatedOnBefore(LocalDate createdOn);
}
