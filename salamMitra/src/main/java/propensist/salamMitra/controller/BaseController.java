package propensist.salamMitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.service.FrontEndService;
import propensist.salamMitra.service.JwtService;
import propensist.salamMitra.service.PenggunaService;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.ui.Model;

@Controller
public class BaseController {

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    FrontEndService frontEndService;

    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/")
    public String home(Model model, @CookieValue(value = "jwtToken", defaultValue = "") String jwtToken, HttpServletRequest request, RedirectAttributes redirectAttrs) {
        Pengguna pengguna = null;

        if (!frontEndService.validateCookieJwt(request, jwtToken)) {
            logger.info("Not logged in");
        } else {
            UUID id = jwtService.getIdFromJwtToken(jwtToken);

            try {
                pengguna = penggunaService.getUserById(id);
            } catch (RuntimeException e) {
                redirectAttrs.addFlashAttribute("error", "Your session has expired. Please log in again");
                return "redirect:/logout";
            }

            logger.info("Pengguna logged in: " + jwtToken);
        }
        
        model.addAttribute("pengguna", pengguna);

        return "landing-page";
    }
}
