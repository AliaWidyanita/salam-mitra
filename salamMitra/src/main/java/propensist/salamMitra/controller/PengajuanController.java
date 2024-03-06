package propensist.salamMitra.controller;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import java.util.Base64;
import java.util.ArrayList;

import jakarta.validation.Valid;
import propensist.salamMitra.dto.PengajuanMapper;
import propensist.salamMitra.dto.request.CreatePengajuanRequestDTO;
import propensist.salamMitra.model.KebutuhanDana;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PengajuanService;

import org.springframework.ui.Model;

@Controller
public class PengajuanController {

    @Autowired
    private PengajuanMapper pengajuanMapper;

    @Autowired
    private PengajuanService pengajuanService;
    
    @Autowired
    private LokasiService lokasiService;

    @GetMapping("/pengajuan/tambah")
    public String formTambahPengajuan(Model model) {
        var pengajuanDTO = new CreatePengajuanRequestDTO();
        model.addAttribute("pengajuanDTO", pengajuanDTO);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());

        return "form-tambah-pengajuan";
    }
    
    @PostMapping("/pengajuan/tambah")
    public String tambahPengajuan(@Valid @ModelAttribute CreatePengajuanRequestDTO createPengajuanRequestDTO,
                               @RequestParam("ktpPIC") MultipartFile ktpPIC,
                               @RequestParam("rab") MultipartFile rab,
                               @RequestParam("dokumen") MultipartFile dokumen,
                               Model model) throws IOException {
    
        byte[] ktpPICBytes = ktpPIC.getBytes();
        byte[] rabBytes = rab.getBytes();
        byte[] dokumenBytes = dokumen.getBytes();
    
        String nominalStr = createPengajuanRequestDTO.getNominalKebutuhanDana();
        // Hilangkan karakter non-digit dari nominal kebutuhan dana
        String nominalClean = nominalStr.replaceAll("[^\\d]", "");
        // Konversi nominal kebutuhan dana ke dalam bentuk Long
        Long nominalLong = Long.parseLong(nominalClean);
        // Set nominal kebutuhan dana yang sudah bersih ke DTO
        createPengajuanRequestDTO.setNominalKebutuhanDana(nominalClean);
    
        // Menginisialisasi pengajuan
        Pengajuan pengajuan = pengajuanMapper.createPengajuanRequestDTOToPengajuan(createPengajuanRequestDTO);
        // Set data KTP, RAB, dan dokumen ke pengajuan
        pengajuan.setKtpPIC(ktpPICBytes);
        pengajuan.setRab(rabBytes);
        pengajuan.setDokumen(dokumenBytes);
    
        // Set nominal kebutuhan dana ke pengajuan
        pengajuan.setNominalKebutuhanDana(nominalLong);

            
        // Simpan pengajuan ke database
        pengajuanService.savePengajuan(pengajuan);
        Long id = pengajuan.getId();
    
        // Ambil list kebutuhan dana dari DTO
        List<KebutuhanDana> listKebutuhanDana = createPengajuanRequestDTO.getListKebutuhanDana();
        System.out.println("Jumlah Kebutuhan Dana: " + listKebutuhanDana.size());
        for (KebutuhanDana kebutuhanDana : listKebutuhanDana) {
            System.out.println("Asnaf: " + kebutuhanDana.getAsnaf() + ", Pilar: " + kebutuhanDana.getPilar());
        }
        System.out.println("CAPEK");  
        
        
        // Set pengajuan untuk setiap kebutuhan dana
        if (listKebutuhanDana != null) {
            for (KebutuhanDana kebutuhanDana : listKebutuhanDana) {
                kebutuhanDana.setPengajuan(pengajuan);
            }
            // Simpan kebutuhan dana ke database
            pengajuan.setListKebutuhanDana(listKebutuhanDana);
            pengajuanService.savePengajuan(pengajuan);
        }
    


        model.addAttribute("pengajuan", createPengajuanRequestDTO);
        model.addAttribute("id", id);
    
        return "success-create-pengajuan";
    }
    






    // @PostMapping(value = "/pengajuan/tambah", params = {"addRow"})
    // public String addRowKebutuhanDana(@ModelAttribute CreatePengajuanRequestDTO createPengajuanRequestDTO, Model model){
    //     if (createPengajuanRequestDTO.getListKebutuhanDana() == null || 
    //     createPengajuanRequestDTO.getListKebutuhanDana().size() == 0){
    //         createPengajuanRequestDTO.setListKebutuhanDana(new ArrayList<>());
    //     }

        
    
    //     // Tambahkan baris baru ke dalam list kebutuhan dana
    //     createPengajuanRequestDTO.getListKebutuhanDana().add(new KebutuhanDana());
    
    //     model.addAttribute("pengajuanDTO", createPengajuanRequestDTO);
    //     return "form-tambah-pengajuan";
    // }
    

    // @PostMapping(value = "/pengajuan/tambah", params = {"deleteRow"})
    // public String deleteRowKebutuhanDana(
    //         @ModelAttribute CreatePengajuanRequestDTO createpengajuanRequestDTO,
    //         @RequestParam("deleteRow") String row,
    //         Model model
    // ) {
    //     int rowId = Integer.parseInt(row);
    //     if (rowId >= 0 && rowId < createpengajuanRequestDTO.getListKebutuhanDana().size()) {
    //         createpengajuanRequestDTO.getListKebutuhanDana().remove(rowId);
    //     }
    //     model.addAttribute("pengajuanDTO", createpengajuanRequestDTO);
    //     return "form-tambah-pengajuan";
    // }
    













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
