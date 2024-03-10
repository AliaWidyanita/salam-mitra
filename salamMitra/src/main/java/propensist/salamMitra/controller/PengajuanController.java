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
import jakarta.validation.Valid;
import propensist.salamMitra.dto.KebutuhanDanaMapper;
import propensist.salamMitra.dto.PengajuanMapper;
import propensist.salamMitra.dto.request.CreateKebutuhanDanaDTO;
import propensist.salamMitra.dto.request.CreateListPengajuanKebutuhanDanaDTO;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.service.KebutuhanDanaService;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PengajuanService;

import org.springframework.ui.Model;

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

    @GetMapping("/pengajuan/tambah")
    public String formTambahPengajuan(Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("user", role);
        
        var listPengajuanKebutuhanDanaDTO = new CreateListPengajuanKebutuhanDanaDTO();
        model.addAttribute("listPengajuanKebutuhanDanaDTO", listPengajuanKebutuhanDanaDTO);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());

        return "form-tambah-pengajuan";
    }
    
    @PostMapping("/pengajuan/tambah")
    public String tambahPengajuan(@Valid @ModelAttribute CreateListPengajuanKebutuhanDanaDTO listPengajuanKebutuhanDanaDTO,
                               @RequestParam("ktpPIC") MultipartFile ktpPIC,
                               @RequestParam("rab") MultipartFile rab,
                               @RequestParam("dokumen") MultipartFile dokumen,
                               Model model) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("user", role);

        //Set Up Pengajuan Only                        
        var pengajuanDTO = listPengajuanKebutuhanDanaDTO.getPengajuanDTO();
        byte[] ktpPICBytes = ktpPIC.getBytes();
        byte[] rabBytes = rab.getBytes();
        byte[] dokumenBytes = dokumen.getBytes();

        var pengajuan =  pengajuanMapper.createPengajuanRequestDTOToPengajuan(pengajuanDTO);                  
        pengajuan.setKtpPIC(ktpPICBytes);
        pengajuan.setRab(rabBytes);
        pengajuan.setDokumen(dokumenBytes);
        Long id = pengajuan.getId();
        Long nominalDana = 0L;
        pengajuan.setNominalKebutuhanDana(nominalDana);
        pengajuan.setJumlahKebutuhanOperasional((long) 0);

        pengajuanService.savePengajuan(pengajuan);

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

    
        return "view-pengajuan";
    }

    @GetMapping("/pengajuan")
    public String listPengajuan(Model model) {
        
        List<Pengajuan> listPengajuan = pengajuanService.getAllPengajuan();

        // Menambahkan list pengajuan ke model untuk ditampilkan di halaman web
        model.addAttribute("listPengajuan", listPengajuan);

        return "viewall-pengajuan";
    }

    @GetMapping("/pengajuan/{id}")
    public String detailAjuan(@PathVariable("id") String id, Model model) {
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
