package propensist.salamMitra.service;

import java.util.List;

import propensist.salamMitra.model.ProgramKerja;

public interface ProgramKerjaService {
    void saveProgramKerja(ProgramKerja programKerja);
    List<ProgramKerja> getAllProgramKerja();
    ProgramKerja findProgramKerjaById(Long id);
    List<String> getAllKategoriProgram(); 
    List<String> getAllKategoriAsnaf();
    ProgramKerja updateProgramKerja(ProgramKerja programKerjaFromDto);
    void handleFotoProgram(ProgramKerja programKerja);
    List<ProgramKerja> findProgramKerjaByKategori(String kategori);
    ProgramKerja findProgramKerjaByJudul(String judul);
}