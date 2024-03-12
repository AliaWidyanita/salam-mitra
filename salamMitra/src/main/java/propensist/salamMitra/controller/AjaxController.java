package propensist.salamMitra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import propensist.salamMitra.service.LokasiService;

import java.util.List;

@RestController
public class AjaxController {

    private final LokasiService lokasiService;

    public AjaxController(LokasiService lokasiService) {
        this.lokasiService = lokasiService;
    }

    @GetMapping("/getKabupatenKotaByProvinsi")
    public ResponseEntity<List<String>> getKabupatenByProvinsi(@RequestParam("provinsi") String provinsi) {
        List<String> daftarKabupatenKota = lokasiService.getAllKabupatenKotaByProvinsi(provinsi);
        return ResponseEntity.ok(daftarKabupatenKota);
    }

    @GetMapping("/getKecamatanByProvinsiKabupatenKota")
    public ResponseEntity<List<String>> getKecamatanByProvinsiKabupatenKota(@RequestParam("provinsi") String provinsi, @RequestParam("kabupatenKota") String kabupatenKota) {
        List<String> daftarKecamatan = lokasiService.getAllKecamatanByProvinsiKabupatenKota(provinsi, kabupatenKota);
        return ResponseEntity.ok(daftarKecamatan);
    }
    
}
