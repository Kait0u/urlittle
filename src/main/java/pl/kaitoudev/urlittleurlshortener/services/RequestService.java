package pl.kaitoudev.urlittleurlshortener.services;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    public String getAddress(HttpServletRequest request) {
        return String.format("%s://%s", request.getScheme(), request.getHeader("Host"));
    }
}
