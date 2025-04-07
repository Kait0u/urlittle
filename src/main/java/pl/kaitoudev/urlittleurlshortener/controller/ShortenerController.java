package pl.kaitoudev.urlittleurlshortener.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.kaitoudev.urlittleurlshortener.model.entity.ShortenedLink;
import pl.kaitoudev.urlittleurlshortener.services.RequestService;
import pl.kaitoudev.urlittleurlshortener.services.ShortenerService;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class ShortenerController {
    private final RequestService requestService;
    private final ShortenerService shortenerService;

    @GetMapping
    public String index() {
        return "home";
    }

    @PostMapping
    public String shorten(@RequestParam String url, Model model, HttpServletRequest request) {
        ShortenedLink shortenedLink = shortenerService.shortenLink(url);
        if (shortenedLink != null) {
            String shortenedUrl = requestService.getAddress(request)
                    + String.format("/%s", shortenedLink.getShortenedCode());
            model.addAttribute("shortenedUrl", shortenedUrl);
        } else {
            model.addAttribute("shorteningError", "Shomething went wrong! Sorry! Try again later!");
        }
        return "home";
    }

    @GetMapping("/{code}")
    public String redirectToShortened(@PathVariable String code, Model model) {
        ShortenedLink shortenedLink = shortenerService.findShortenedLinkByShortenedCode(code);
        if (shortenedLink == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return "redirect:" + shortenedLink.getActualUrl();
    }

    @ExceptionHandler({ResponseStatusException.class})
    public String handleException(ResponseStatusException ex, Model model) {
        return "errors/404";
    }

}
