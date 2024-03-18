package propensist.salamMitra.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.ArrayList;
import jakarta.validation.Valid;
import propensist.salamMitra.dto.KebutuhanDanaMapper;
import propensist.salamMitra.dto.PengajuanMapper;
import propensist.salamMitra.dto.request.CreateKebutuhanDanaDTO;
import propensist.salamMitra.dto.request.CreateListPengajuanKebutuhanDanaDTO;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.service.KebutuhanDanaService;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PengajuanService;
import propensist.salamMitra.service.PenggunaService;
import propensist.salamMitra.service.ProgramKerjaService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class PengajuanController {

    @Autowired
    private PengajuanMapper pengajuanMapper;

    @Autowired
    private KebutuhanDanaMapper kebutuhanDanaMapper;

    @Autowired
    private PengajuanService pengajuanService;
    
    @Autowired
    private KebutuhanDanaService kebutuhanDanaService;
    
    @Autowired
    private LokasiService lokasiService;

    @Autowired
    private ProgramKerjaService programKerjaService;

    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/pengajuan/tambah")
    public String formTambahPengajuan(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        
        var listPengajuanKebutuhanDanaDTO = new CreateListPengajuanKebutuhanDanaDTO();
        model.addAttribute("listPengajuanKebutuhanDanaDTO", listPengajuanKebutuhanDanaDTO);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategori", programKerjaService.getAllKategori());


        return "form-tambah-pengajuan";
    }
    
    @PostMapping("/pengajuan/tambah")
    public String tambahPengajuan(@Valid @ModelAttribute CreateListPengajuanKebutuhanDanaDTO listPengajuanKebutuhanDanaDTO,
                               @RequestParam("ktpPIC") MultipartFile ktpPIC,
                               @RequestParam("rab") MultipartFile rab,
                               @RequestParam("dokumen") MultipartFile dokumen,
                               RedirectAttributes redirectAttributes,
                               Model model) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);       

        //Set Up Pengajuan Only                        
        var pengajuanDTO = listPengajuanKebutuhanDanaDTO.getPengajuanDTO();
        byte[] ktpPICBytes = ktpPIC.getBytes();
        byte[] rabBytes = rab.getBytes();
        byte[] dokumenBytes = dokumen.getBytes();

        var pengajuan =  pengajuanMapper.createPengajuanRequestDTOToPengajuan(pengajuanDTO);                  
        pengajuan.setKtpPIC(ktpPICBytes);
        pengajuan.setRab(rabBytes);
        pengajuan.setDokumen(dokumenBytes);
        pengajuan.setStatus("Diajukan");

        Long id = pengajuan.getId();
        Long nominalDana = 0L;
        pengajuan.setNominalKebutuhanDana(nominalDana);
        pengajuan.setJumlahKebutuhanOperasional((long) 0);
        pengajuan.setUsername(user.getUsername());
                                

        pengajuanService.savePengajuan(pengajuan);
        id = pengajuan.getId();


        for(CreateKebutuhanDanaDTO kebutuhanDanaDTO : listPengajuanKebutuhanDanaDTO.getListKebutuhanDanaDTO()){
            kebutuhanDanaDTO.setPengajuan(pengajuan);
            var kebutuhanDana = kebutuhanDanaMapper.createKebutuhanDanaDTOToKebutuhanDana(kebutuhanDanaDTO);
            nominalDana += kebutuhanDana.getJumlahDana();
            kebutuhanDanaService.saveKebutuhanDana(kebutuhanDana);
        }
        pengajuan.setJumlahKebutuhanOperasional(Long.valueOf(listPengajuanKebutuhanDanaDTO.getListKebutuhanDanaDTO().size()));
        pengajuan.setNominalKebutuhanDana(nominalDana);
        pengajuanService.savePengajuan(pengajuan);
        model.addAttribute("pengajuan", pengajuanDTO);
        model.addAttribute("id", id);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        redirectAttributes.addFlashAttribute("successMessage", "Kerja Sama baru berhasil diajukan!");

    
        return "redirect:/pengajuan";
    }

    @GetMapping("/pengajuan")
    public String listPengajuan(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        List<Pengajuan> listPengajuan = pengajuanService.getAllPengajuan();
        // Membuat list baru untuk menyimpan Pengajuan dengan username yang sesuai
        List<Pengajuan> listPengajuanUsername = new ArrayList<>();

        // Iterasi melalui setiap Pengajuan di listPengajuan
        for (Pengajuan pengajuan : listPengajuan) {
            // Memeriksa apakah username pengajuan sama dengan username yang sedang diautentikasi
            if (pengajuan.getUsername().equals(user.getUsername())) {
                // Jika username sama, tambahkan pengajuan ke listPengajuanUsername
                listPengajuanUsername.add(pengajuan);
            }
        }


        // Menambahkan list pengajuan ke model untuk ditampilkan di halaman web
        model.addAttribute("listPengajuan", listPengajuanUsername);

        return "viewall-pengajuan";
    }

    @GetMapping("/pengajuan/{id}")
    public String detailAjuan(@PathVariable("id") String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        Long longId = Long.parseLong(id);
        
        try {
            var optPengajuan = pengajuanService.getPengajuanById(longId);
            
            if (optPengajuan.isPresent()) {
                Pengajuan pengajuan = optPengajuan.get();

                pengajuanService.handleKTP(pengajuan);
                pengajuanService.handleRAB(pengajuan);
                pengajuanService.handleDOC(pengajuan);

                model.addAttribute("pengajuan", pengajuan);
                return "view-pengajuan";
            } 
            else {
                return "error-page";
            }
        } catch (Exception e) {
            return "error-page";
        }
    }   
}
