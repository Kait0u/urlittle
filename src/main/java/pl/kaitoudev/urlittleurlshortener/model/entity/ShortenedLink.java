package pl.kaitoudev.urlittleurlshortener.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "shortened_links")
@Getter
@Setter
public class ShortenedLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String shortenedCode;

    @Column(unique = true, nullable = false)
    private String actualUrl;

    @Column(nullable = false)
    private LocalDate createdOn;

    @PrePersist
    protected void onCreate() {
        this.createdOn = LocalDate.now();
    }
}
