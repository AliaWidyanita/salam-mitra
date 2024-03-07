package propensist.salamMitra.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class FrontEndService {
    private static final long EXPIRATION_TIME = 3600000;

    public void setCookie(HttpServletResponse response, String jwtString) {
        List<String> paths = Arrays.asList("/", "/profile");

        for (String path : paths) {
            Cookie cookie = new Cookie("jwtToken", jwtString);
            cookie.setHttpOnly(true);
            cookie.setPath(path);
            if (jwtString == null) {
                cookie.setMaxAge(0);
            } else {
                cookie.setMaxAge((int) (EXPIRATION_TIME / 1000));
            }
            response.addCookie(cookie);
        }
    }

    public boolean validateCookieJwt(HttpServletRequest request, String token) {
        Cookie[] cookies = request.getCookies();

        // if (cookies != null) {
        //     for (Cookie cookie : cookies) {
        //         if ("jwtToken".equals(cookie.getName())) {
        //             int maxAge = cookie.getMaxAge();
        //             return false;
        //         }
        //     }
        // }

        if (token == null || token.equals("")) {
            return false;
        }

        return true;
    }
}
