package propensist.salamMitra.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import propensist.salamMitra.dto.MitraMapper;
import propensist.salamMitra.dto.request.CreateMitraRequestDTO;
import propensist.salamMitra.dto.request.LoginJwtRequestDTO;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.security.jwt.JwtService;
import propensist.salamMitra.service.FrontEndService;
import propensist.salamMitra.service.PenggunaService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;



@Controller
public class UserController {

    @Autowired
    private MitraMapper mitraMapper;

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    FrontEndService frontEndService;

    @Autowired
    JwtService jwtUtils;

    @RequestMapping("/home")
    public String home (Principal principal, Model model){
        String role = penggunaService.getAkunByEmail(principal.getName()).getRole();
        model.addAttribute("role",role);
        return "home";
    }

    // @GetMapping("/register")
    // public String registerMitra(Model model) {
    //     model.addAttribute("mitraDTO", new CreateMitraRequestDTO());
    //     return "login";
    // }

    @PostMapping("/register")
    public String registerMitra(@Valid @ModelAttribute CreateMitraRequestDTO mitraDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasFieldErrors()) {
            redirectAttributes.addFlashAttribute("error", "Formulir memiliki data yang tidak valid atau belum diisi.");
            return "redirect:/register";  // Redirect to the registration page
        } else {
            if (penggunaService.authenticate(mitraDTO.getUsername()) != null) {
                if (penggunaService.getUserByUsername(mitraDTO.getUsername()).isDeleted() == false) {
                    redirectAttributes.addFlashAttribute("error", "Username sudah terpakai!");
                }
            }
            if (penggunaService.getAkunByEmail(mitraDTO.getEmail()) != null) {
                redirectAttributes.addFlashAttribute("error", "Email sudah terpakai!");
            } else { // mitra
                penggunaService.createMitra(mitraDTO);

                // Menyimpan pesan sukses
                redirectAttributes.addFlashAttribute("successMessage", "Selamat, anda berhasil mendaftarkan akun!");
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new LoginJwtRequestDTO());
        model.addAttribute("mitraDTO", new CreateMitraRequestDTO());
        System.out.println("masuk login");
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Pengguna usersModel, Model model) {
        System.out.println("login request: " + usersModel);
        Pengguna authenticated = penggunaService.authenticate(usersModel.getUsername());
        if (authenticated != null){
            model.addAttribute("userLogin", authenticated.getUsername());
            
            String role = authenticated.getRole();
            System.out.println("ini role: " + role);
            model.addAttribute("role",role);
            if (role.equals("admin")) {
                return "redirect:/pengguna";
            } else if (role.equals("mitra")) {
                return "personal-page";
            }
        } 
            return "error-page";
        
    }

    @PostMapping("/login")
    public String authenticateAndGetToken(@ModelAttribute LoginJwtRequestDTO loginRequest, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        
        System.out.println("ada apaaa");
        try {
            System.out.println("masuk");
            Pengguna authenticated = penggunaService.authenticate(loginRequest.getUsername());
            if (authenticated == null || authenticated.isDeleted() == true) {
                System.out.println("masuk2");
                redirectAttributes.addFlashAttribute("error", "Username salah!");
            }
            System.out.println("masuk3");
            String jwt = jwtUtils.generateToken(loginRequest.getUsername(), authenticated.getId(), authenticated.getRole());
            System.out.println(jwt);

            // set cookie
            frontEndService.setCookie(response, jwt);

            return "redirect:/";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println("masuk4");
            return "redirect:/login";
        } 
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletResponse response) {
        frontEndService.setCookie(response, null);
        return "redirect:/";
    }
}
