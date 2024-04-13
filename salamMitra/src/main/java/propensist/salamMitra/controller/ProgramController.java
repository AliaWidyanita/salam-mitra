package propensist.salamMitra.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import propensist.salamMitra.dto.ProgramKerjaMapper;
import propensist.salamMitra.dto.request.CreateProgramKerjaRequestDTO;
import propensist.salamMitra.dto.request.UpdateProgramKerjaRequestDTO;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramKerja;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PenggunaService;
import propensist.salamMitra.service.ProgramKerjaService;

@Controller
public class ProgramController {

    @Autowired
    private LokasiService lokasiService;

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private ProgramKerjaService programKerjaService;

    @Autowired
    private ProgramKerjaMapper programKerjaMapper;

    @GetMapping("/tambah-program")
    public String formTambahProgram(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        var programKerjaDTO = new CreateProgramKerjaRequestDTO();
        model.addAttribute("programKerjaDTO", programKerjaDTO);
        
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategoriProgram", programKerjaService.getAllKategoriProgram());
        model.addAttribute("daftarKategoriAsnaf", programKerjaService.getAllKategoriAsnaf());

        return "form-tambah-program";
    }

    @PostMapping("/tambah-program")
    public String tambahProgram(@Valid @ModelAttribute CreateProgramKerjaRequestDTO programKerjaDTO, BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes, @RequestParam("foto") MultipartFile foto, @RequestParam("kategoriAsnaf") List<String> kategoriAsnaf
                                ) throws IOException {

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

        if (!foto.isEmpty()) {
            byte[] fotoProgram = foto.getBytes();
            programKerjaDTO.setFotoProgram(fotoProgram);
        }

        ProgramKerja programKerja = programKerjaMapper.createProgramKerjaDTOToProgramKerja(programKerjaDTO);

        programKerja.setKategoriAsnaf(kategoriAsnaf);

        programKerjaService.saveProgramKerja(programKerja);

        model.addAttribute("idProgram", programKerja.getId());

        redirectAttributes.addFlashAttribute("successMessage", "Program kerja baru berhasil ditambahkan!");

        return "redirect:/";
    }

    @GetMapping("/program-{id}")
    public String detailProgram(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        var program = programKerjaService.findProgramKerjaById(id);

        if (program.getFotoProgram() != null) {
            programKerjaService.handleFotoProgram(program);
        }
        model.addAttribute("program", program);

        return "detail-program";
    } 

    @GetMapping("/edit-program-{id}")
    public String formEditProgram(@PathVariable("id") String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        Long longId = Long.parseLong(id);

        var program = programKerjaService.findProgramKerjaById(longId);

        model.addAttribute("program", program);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategoriProgram", programKerjaService.getAllKategoriProgram());
        model.addAttribute("daftarKategoriAsnaf", programKerjaService.getAllKategoriAsnaf());
        
        return "form-edit-program";
    }

    @PostMapping("/edit-program-{id}")
    public String editProgram(@Valid @ModelAttribute UpdateProgramKerjaRequestDTO programKerjaDTO, Model model, RedirectAttributes redirectAttributes) {

        var programFromDto = programKerjaMapper.updateProgramKerjaRequestDTOToProgramKerja(programKerjaDTO);
        
        var programKerja = programKerjaService.updateProgramKerja(programFromDto);
        
        model.addAttribute("id", programKerja.getId());

        return "redirect:/";
    }
}