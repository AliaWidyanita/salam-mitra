package propensist.salamMitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import propensist.salamMitra.dto.request.CreateMitraRequestDTO;
import propensist.salamMitra.dto.request.LoginJwtRequestDTO;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.security.jwt.JwtService;
import propensist.salamMitra.service.FrontEndService;
import propensist.salamMitra.service.PenggunaService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class UserController {

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    FrontEndService frontEndService;

    @Autowired
    JwtService jwtUtils;

    @GetMapping("/")
    public String home (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("user", role);
        return "landing-page";
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
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Pengguna usersModel, Model model) {
        System.out.println("login request: " + usersModel);
        Pengguna authenticated = penggunaService.authenticate(usersModel.getUsername());
        if (authenticated != null){
            String role = authenticated.getRole();
            System.out.println("ini role: " + role);

            model.addAttribute("userLogin", authenticated.getUsername());
            model.addAttribute("role",role);
            
            if (role.equals("mitra")) {
                return "landing-page";
            } else {
                return "dashboard";
            }
        } 
            return "error-page";
    }
    
    @RequestMapping("/logout")
    public String logout(HttpServletResponse response) {
        frontEndService.setCookie(response, null);
        return "redirect:/";
    }
}
