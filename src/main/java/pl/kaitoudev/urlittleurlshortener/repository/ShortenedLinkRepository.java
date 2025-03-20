package pl.kaitoudev.urlittleurlshortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kaitoudev.urlittleurlshortener.model.entity.ShortenedLink;

public interface ShortenedLinkRepository extends JpaRepository<ShortenedLink, Long> {
    public ShortenedLink findByActualUrl(String actualUrl);
    public ShortenedLink findByShortenedCode(String shortenedCode);
}
