package propensist.salamMitra.service;

import java.util.List;

import propensist.salamMitra.model.Lokasi;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramKerja;

public interface ProgramKerjaService {
        void saveProgramKerja(ProgramKerja programKerja);
        List<ProgramKerja> getAllProgramKerja();
        List<String> getAllKategori();
        


    
}
