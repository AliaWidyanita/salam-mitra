package propensist.salamMitra.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import propensist.salamMitra.dto.AdminMapper;
import propensist.salamMitra.dto.MitraMapper;
import propensist.salamMitra.dto.request.CreateAdminRequestDTO;
import propensist.salamMitra.dto.request.CreateMitraRequestDTO;
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

    @GetMapping("")
    public String viewDaftarPengguna(Model model) {
        
        List<Pengguna> listPengguna = penggunaService.getAllPengguna();

        System.out.println(listPengguna);
        model.addAttribute("listPengguna", listPengguna);
        
        return "view-daftar-pengguna";
    }

    @GetMapping("/tambah-admin")
    public String addAdmin(Model model) {
        
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

        // Generate random password
        String randomPassword = PasswordGenerator.generateRandomPassword(12);

        // Set the hashed password to the admin DTO
        adminDTO.setPassword(randomPassword);

        var admin = adminMapper.createAdminRequestDTOToAdmin(adminDTO);
        penggunaService.saveAdmin(admin);

        // Menyimpan pesan sukses
        redirectAttributes.addFlashAttribute("successMessage", "Admin berhasil ditambahkan!");

        // Mengarahkan pengguna kembali ke halaman "/pengguna"
        return "redirect:/pengguna";
    }
}
