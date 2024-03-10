package propensist.salamMitra.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import propensist.salamMitra.dto.AdminMapper;
import propensist.salamMitra.dto.request.CreateAdminRequestDTO;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.service.PenggunaService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Controller
@RequestMapping("/pengguna")
public class PenggunaController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("")
    public String viewDaftarPengguna(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("user", role);
        
        List<Pengguna> listPengguna = penggunaService.getAllPengguna();

        System.out.println(listPengguna);
        model.addAttribute("listPengguna", listPengguna);
        
        return "view-daftar-pengguna";
    }

    @GetMapping("/tambah-admin")
    public String addAdmin(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("user", role);
        
        var adminDTO = new CreateAdminRequestDTO();
        model.addAttribute("adminDTO", adminDTO);
        
        return "form-tambah-admin";
    }

    @PostMapping("/tambah-admin")
    public String addAdmin(@Valid @ModelAttribute CreateAdminRequestDTO adminDTO, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        
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

        String encodedPassword = encoder.encode(adminDTO.getPassword());
        adminDTO.setPassword(encodedPassword);

        var admin = adminMapper.createAdminRequestDTOToAdmin(adminDTO);
        penggunaService.saveAdmin(admin);

        // Menyimpan pesan sukses
        redirectAttributes.addFlashAttribute("successMessage", "Admin berhasil ditambahkan!");

        // Mengarahkan pengguna kembali ke halaman "/pengguna"
        return "redirect:/pengguna";
    }
}
