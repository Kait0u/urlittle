package pl.kaitoudev.urlittleurlshortener.controller.rest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.kaitoudev.urlittleurlshortener.model.dto.ShortenedLinkResponseDTO;
import pl.kaitoudev.urlittleurlshortener.model.entity.ShortenedLink;
import pl.kaitoudev.urlittleurlshortener.services.RequestService;
import pl.kaitoudev.urlittleurlshortener.services.ShortenerService;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RestShortenerController {
    private final ShortenerService shortenerService;
    private final RequestService requestService;

    @PostMapping("/shorten")
    public ShortenedLinkResponseDTO shorten(@RequestParam("url") String url, HttpServletRequest request) {
        ShortenedLink shortenedLink = shortenerService.shortenLink(url);
        if (shortenedLink == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong. Try again later!");
        }
        String shortenedUrl = requestService.getAddress(request)
                + String.format("/%s", shortenedLink.getShortenedCode());
        return new ShortenedLinkResponseDTO(url, shortenedUrl, shortenedLink.getCreatedOn());
    }
}
