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
import java.util.Optional;
import java.util.ArrayList;
import jakarta.validation.Valid;
import propensist.salamMitra.dto.KebutuhanDanaMapper;
import propensist.salamMitra.dto.PengajuanMapper;
import propensist.salamMitra.dto.request.CreateKebutuhanDanaDTO;
import propensist.salamMitra.dto.request.CreateListPengajuanKebutuhanDanaDTO;
import propensist.salamMitra.model.Mitra;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramKerja;
import propensist.salamMitra.service.KebutuhanDanaService;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PengajuanService;
import propensist.salamMitra.service.PenggunaService;
import propensist.salamMitra.service.ProgramKerjaService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    @Autowired
    private ProgramKerjaService programKerjaService;

    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/tambah-pengajuan")
    public String formTambahPengajuan(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        
        var listPengajuanKebutuhanDanaDTO = new CreateListPengajuanKebutuhanDanaDTO();
        model.addAttribute("listPengajuanKebutuhanDanaDTO", listPengajuanKebutuhanDanaDTO);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategori", programKerjaService.getAllKategoriProgram());
        
        List<ProgramKerja> programKerja = programKerjaService.getAllProgramKerja();
        List<String> daftarProgram = new ArrayList<>();

        for(ProgramKerja program : programKerja) {
            daftarProgram.add(program.getJudul());
        }

        model.addAttribute("daftarProgram", daftarProgram);
        return "form-tambah-pengajuan";
    }
    
    @PostMapping("/tambah-pengajuan")
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

        if (user instanceof Mitra) {
            for (Pengajuan pengajuan : listPengajuan) {
                if (pengajuan.getUsername().equals(user.getUsername())) {
                    listPengajuanUsername.add(pengajuan);
                }
            }
            model.addAttribute("listPengajuan", listPengajuanUsername);
        } 
        else{    
             model.addAttribute("listPengajuan", listPengajuan);       
        }
        return "daftar-pengajuan";
    }

    @GetMapping("/pengajuan-detail-{id}")
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

                if(role.equals("admin_PROGRAM")){
                    if (pengajuan.getStatus().equalsIgnoreCase("Diajukan")){
                        pengajuan.setStatus("In Review");
                    }
                   
                    pengajuanService.savePengajuan(pengajuan);
                }

                pengajuanService.handleKTP(pengajuan);
                pengajuanService.handleRAB(pengajuan);
                pengajuanService.handleDOC(pengajuan);            
                
                model.addAttribute("pengajuan", pengajuan);
                return "detail-pengajuan";
            } 
            else {
                return "error-page";
            }
        } catch (Exception e) {
            return "error-page";
        }
    } 

    @PostMapping("/review-pengajuan-admin-{id}")
    public String submitReviewByAdmin(@PathVariable("id") String id,
                                      @RequestParam(value="comment", required = false) String comment,
                                      @RequestParam("action") String action,
                                      Model model) {
        // Ambil informasi pengguna yang sedang login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        model.addAttribute("role", role);
        model.addAttribute("user", user);
    
        // Ubah ID menjadi tipe data Long
        Long longId = Long.parseLong(id);
        Optional<Pengajuan> optPengajuan = pengajuanService.getPengajuanById(longId);
    
        if (optPengajuan.isPresent()) {
            Pengajuan pengajuan = optPengajuan.get();

            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            String currentDateTimeString = currentTime.format(formatter);
            String username = user.getUsername();

            // Set komentar pada pengajuan
            pengajuan.setKomentar(comment);
            
            // Sesuaikan status berdasarkan aksi yang dipilih
            switch (action) {
                case "setujui":
                    pengajuan.setStatus("Menunggu Pencairan Dana oleh Program Service");
                    pengajuan.setReviewedBy("Disetujui oleh " + username + " pada " + currentDateTimeString);
                    break;
                case "tolak":
                    pengajuan.setStatus("Ditolak");
                    pengajuan.setReviewedBy("Ditolak oleh " + username + " pada " + currentDateTimeString);

                    break;
                case "perlu-revisi":
                    pengajuan.setStatus("Perlu Revisi");
                    pengajuan.setReviewedBy("Perlu revisi dari " + username + " pada " + currentDateTimeString);
                    break;
                case "teruskan":
                    pengajuan.setStatus("Diteruskan ke Manajemen");
                    pengajuan.setReviewedBy("Diteruskan ke Manajemen oleh " + username + " pada " + currentDateTimeString);

                    break;
                default:
                    // Aksi tidak valid
                    return "error-page";
            }
            
            // Simpan perubahan pada pengajuan
            pengajuanService.savePengajuan(pengajuan);
            
            // Redirect kembali ke halaman review dengan mengirimkan ID pengajuan
            return "redirect:/pengajuan-detail-" + id;
        } else {
            // Pengajuan tidak ditemukan
            return "error-page";
        }
    }
    
    @PostMapping("/review-pengajuan-manajemen-{id}")
    public String submitReviewByManejemen(@PathVariable("id") String id,
                                      @RequestParam(value="comment", required = false) String comment,
                                      @RequestParam("action") String action,
                                      Model model) {
        // Ambil informasi pengguna yang sedang login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        model.addAttribute("role", role);
        model.addAttribute("user", user);
    
        // Ubah ID menjadi tipe data Long
        Long longId = Long.parseLong(id);
        Optional<Pengajuan> optPengajuan = pengajuanService.getPengajuanById(longId);
    
        if (optPengajuan.isPresent()) {
            Pengajuan pengajuan = optPengajuan.get();
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            String currentDateTimeString = currentTime.format(formatter);
            String username = user.getUsername();
            
            // Set komentar pada pengajuan
            pengajuan.setKomentar(comment);
            
            // Sesuaikan status berdasarkan aksi yang dipilih
            switch (action) {
                case "setujui":
                    pengajuan.setStatus("Menunggu Pencairan Dana oleh Program Service");
                    pengajuan.setReviewedBy("Disetujui oleh " + username + " pada " + currentDateTimeString);

                    break;
                case "tolak":
                    pengajuan.setStatus("Ditolak");
                    pengajuan.setReviewedBy("Ditolak oleh " + username + " pada " + currentDateTimeString);

                    break;
                case "perlu-revisi":
                    pengajuan.setStatus("Perlu Revisi");
                    pengajuan.setReviewedBy("Perlu revisi dari " + username + " pada " + currentDateTimeString);

                    break;
                default:
                    // Aksi tidak valid
                    return "error-page";
            }
            
            // Simpan perubahan pada pengajuan
            pengajuanService.savePengajuan(pengajuan);
            
            // Redirect kembali ke halaman review dengan mengirimkan ID pengajuan
            return "redirect:/pengajuan-detail-" + id;
        } else {
            // Pengajuan tidak ditemukan
            return "error-page";
        }
    }
    @PostMapping("/submit-laporan-{id}")
    public String submitLaporanByMitra(@PathVariable("id") String id,
                                        @RequestParam(value="laporan", required = false) String laporan,
                                        @RequestParam("submit") String submit,
                                        Model model) throws IOException {
        // Ambil informasi pengguna yang sedang login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName ());
        model.addAttribute("role", role);
        model.addAttribute("user", user);
    
        // Ubah ID menjadi tipe data Long
        Long longId = Long.parseLong(id);
        Optional<Pengajuan> optPengajuan = pengajuanService.getPengajuanById(longId);
    
        if (optPengajuan.isPresent()) {
            Pengajuan pengajuan = optPengajuan.get();
            LocalDateTime currentTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
            String currentDateTimeString = currentTime.format(formatter);
            String username = user.getUsername();

            byte[] laporanBytes = laporan.getBytes();
            pengajuan.setLaporan(laporanBytes);
        
            // Sesuaikan status berdasarkan aksi yang dipilih
            switch (submit) {
                case "submit":
                    pengajuan.setStatus("Selesai");
                    pengajuan.setReviewedBy("Laporan telah diupload oleh Mitra " + username + " pada " + currentDateTimeString);
                    break;

                default:
                    // Aksi tidak valid
                    return "error-page";
            }

            
            // Simpan perubahan pada pengajuan
            pengajuanService.savePengajuan(pengajuan);
            
            // Redirect kembali ke halaman review dengan mengirimkan ID pengajuan
            return "redirect:/pengajuan-detail-" + id;
        } else {
            // Pengajuan tidak ditemukan
            return "error-page";
        }
    }
}
