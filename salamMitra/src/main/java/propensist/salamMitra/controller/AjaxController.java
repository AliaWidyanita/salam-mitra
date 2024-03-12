package propensist.salamMitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PenggunaService;

import java.util.List;
import org.springframework.ui.Model;

@RestController
public class AjaxController {

    private final LokasiService lokasiService;

    public AjaxController(LokasiService lokasiService) {
        this.lokasiService = lokasiService;
    }

    
    @Autowired
    private PenggunaService penggunaService;

    @GetMapping("/getKabupatenKotaByProvinsi")
    public ResponseEntity<List<String>> getKabupatenByProvinsi(@RequestParam("provinsi") String provinsi, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);  
        List<String> daftarKabupatenKota = lokasiService.getAllKabupatenKotaByProvinsi(provinsi);
        return ResponseEntity.ok(daftarKabupatenKota);
    }

    @GetMapping("/getKecamatanByProvinsiKabupatenKota")
    public ResponseEntity<List<String>> getKecamatanByProvinsiKabupatenKota(@RequestParam("provinsi") String provinsi, @RequestParam("kabupatenKota") String kabupatenKota, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);  
        
        List<String> daftarKecamatan = lokasiService.getAllKecamatanByProvinsiKabupatenKota(provinsi, kabupatenKota);
        return ResponseEntity.ok(daftarKecamatan);
    }
    
}
