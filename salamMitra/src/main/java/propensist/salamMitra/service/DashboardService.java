package propensist.salamMitra.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface DashboardService {
    Map<String, Integer> getTotalPengajuanForMonth(LocalDate date);
    Map<String, Integer> getJumlahProgramKerjaPerKategori();
    
}
