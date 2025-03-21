package pl.kaitoudev.urlittleurlshortener.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShortenedLinkResponseDTO {
    private String fullUrl;
    private String shortUrl;
    private LocalDate creationDate;
}
