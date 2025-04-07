package pl.kaitoudev.urlittleurlshortener.component;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kaitoudev.urlittleurlshortener.model.entity.ShortenedLink;
import pl.kaitoudev.urlittleurlshortener.services.CleanupService;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledCleanupTasks {
    private static final Logger log = LoggerFactory.getLogger(ScheduledCleanupTasks.class);
    private final CleanupService cleanupService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void removeLinksOlderThan30Days_EverydayAtMidnight() {
        log.info("Starting clean-up: links older than 30 days to be removed.");
        LocalDate date30DaysAgo = LocalDate.now().minusDays(30);
        List<ShortenedLink> toDelete = cleanupService.getShortenedLinksOlderThan(date30DaysAgo);
        long count = toDelete.size();
        if (count == 0) {
            log.info("No links older than 30 days have been found. Nothing to delete.");
        } else {
            log.info("Found {} links to remove. Removing...", count);
            if (cleanupService.removeShortenedLinks(toDelete)) {
                log.info("Removed {} links older than 30 days!", count);
            } else {
                log.error("Failed to remove links older than 30 days!");
            }
        }
    }
}


