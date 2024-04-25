package propensist.salamMitra.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import propensist.salamMitra.model.Notifikasi;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.service.NotifikasiService;
import propensist.salamMitra.service.PenggunaService;

@Controller
public class NotifikasiController {
    
    @Autowired
    NotifikasiService notifikasiService;

    @Autowired
    PenggunaService penggunaService;

    @GetMapping("/notifikasi")
    public String viewDaftarPengguna(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        List<Notifikasi> listNotifikasi = notifikasiService.getNotifikasiByUser(user);
        model.addAttribute("listNotifikasi", listNotifikasi);

        return "daftar-notifikasi";
    }
}
