package propensist.salamMitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import propensist.salamMitra.dto.request.CreateMitraRequestDTO;
import propensist.salamMitra.service.PenggunaService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class UserController {

    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        String username = auth.getName();
        
        model.addAttribute("user", role);
        model.addAttribute("username", username);

        if (role.equals("ROLE_ANONYMOUS") || role.equals("mitra")) {
            return "landing-page";
        } else {
            return "personal-page";
        }
    }

    @GetMapping("/register")
    public String registerMitra(Model model) {
        model.addAttribute("mitraDTO", new CreateMitraRequestDTO());
        return "login";
    }

    @PostMapping("/register")
    public String registerMitra(@Valid @ModelAttribute CreateMitraRequestDTO mitraDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasFieldErrors()) {
            redirectAttributes.addFlashAttribute("error", "Formulir memiliki data yang tidak valid atau belum diisi.");
            return "redirect:/register";
        } else {
            if (penggunaService.authenticate(mitraDTO.getUsername()) != null) {
                if (penggunaService.getUserByUsername(mitraDTO.getUsername()).isDeleted() == false) {
                    redirectAttributes.addFlashAttribute("error", "Username sudah terpakai!");
                    return "redirect:/register";
                }
            }
            if (penggunaService.getAkunByEmail(mitraDTO.getEmail()) != null) {
                redirectAttributes.addFlashAttribute("error", "Email sudah terpakai!");
                return "redirect:/register";
            } else { // mitra
                penggunaService.createMitra(mitraDTO);

                // Menyimpan pesan sukses
                redirectAttributes.addFlashAttribute("successMessage", "Selamat, anda berhasil mendaftarkan akun!");
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(name = "error", required = false) String error) {
        model.addAttribute("mitraDTO", new CreateMitraRequestDTO());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("user", role);
        model.addAttribute("currentPage", "login");

        if (error != null) {
            model.addAttribute("error", "Username atau password salah!");
        }
        
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        
        return "redirect:/";
    }
}
