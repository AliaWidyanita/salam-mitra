package propensist.salamMitra.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;

import jakarta.validation.Valid;
import propensist.salamMitra.dto.MitraMapper;
import propensist.salamMitra.dto.request.CreateMitraRequestDTO;
import propensist.salamMitra.model.Pengguna;
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

    @RequestMapping("/")
    public String home (Principal principal, Model model){
        String role = penggunaService.getAkunByEmail(principal.getName()).getRole();
        model.addAttribute("role",role);
        return "home";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new Pengguna());
        return "login";
    }

    @GetMapping("/register")
    public String registerMitra(Model model) {
        
        var mitraDTO = new CreateMitraRequestDTO();
        model.addAttribute("mitraDTO", mitraDTO);
        
        return "register-page";
    }

    @PostMapping("/register")
    public String registerMitra(@Valid @ModelAttribute CreateMitraRequestDTO mitraDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(error -> {
                        if (error instanceof FieldError) {
                            FieldError fieldError = (FieldError) error;
                            return fieldError.getField() + ": " + error.getDefaultMessage();
                        }
                        return error.getDefaultMessage();
                    })
                    .collect(Collectors.toList());

                    model.addAttribute("errors", errors);
        }

        var mitra = mitraMapper.createMitraRequestDTOToAdmin(mitraDTO);
        penggunaService.saveMitra(mitra);
        

        // Menyimpan pesan sukses
        redirectAttributes.addFlashAttribute("successMessage", "Selamat, anda berhasil mendaftarkan akun!");

        // Mengarahkan pengguna kembali ke halaman "/pengguna"
        return "redirect:/login";
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
    
    
    
}
