// package propensist.salamMitra.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;

// import propensist.salamMitra.service.DashboardService;

// import java.time.LocalDate;
// import java.util.Map;

// @Controller
// public class DashboardController {

//     @Autowired
//     private DashboardService dashboardService;

//     @GetMapping("/dashboard")
//     public String dashboard(Model model) {
//         // Mendapatkan tanggal saat ini
//         LocalDate currentDate = LocalDate.now();

//         // Mendapatkan data total pengajuan per bulan dari service dashboard
//         Map<String, Integer> totalPengajuanPerMonth = dashboardService.getTotalPengajuanForMonth(currentDate);

//         System.out.println("Jumlah: " + totalPengajuanPerMonth);

//         // Menyimpan data dalam model untuk ditampilkan pada halaman dashboard
//         model.addAttribute("totalPengajuanPerMonth", totalPengajuanPerMonth);

//         return "dashboard";
//     }
// }