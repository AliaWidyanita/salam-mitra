package propensist.salamMitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.ArrayList;
import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramService;
import propensist.salamMitra.service.PencairanService;
import propensist.salamMitra.service.PenggunaService;
import org.springframework.ui.Model;


@Controller
public class PencairanController {

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private PencairanService pencairanService;
    
    @GetMapping("/pencairan")
    public String listPengajuan(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        List<Pengajuan> listPencairanProgramService = pencairanService.getListPengajuanMenungguPencairanProgramService();
        List<Pengajuan> listPencairanAdminFinance = pencairanService.getListPengajuanMenungguPencairanAdminFinance();

        List<Pengajuan> listPencairan = new ArrayList<>();
        listPencairan.addAll(listPencairanProgramService);
        listPencairan.addAll(listPencairanAdminFinance);

        if (user instanceof ProgramService) {
            model.addAttribute("listPencairanProgramService", listPencairanProgramService);
        } 
        else if (user instanceof Admin) {
            Admin admin = (Admin) user;
            if (admin.getAdminRole() == Admin.AdminRole.FINANCE) {
                model.addAttribute("listPencairanAdminFinance", listPencairanAdminFinance);
            }
        }
        
        model.addAttribute("listPencairan", listPencairan);
        
        return "daftar-pencairan";
    }
    
}
