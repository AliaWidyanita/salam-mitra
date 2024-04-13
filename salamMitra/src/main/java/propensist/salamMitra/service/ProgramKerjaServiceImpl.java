package propensist.salamMitra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import propensist.salamMitra.model.ProgramKerja;
import propensist.salamMitra.repository.ProgramKerjaDb;
import org.springframework.stereotype.Service;
import java.util.Base64;


@Service
public class ProgramKerjaServiceImpl implements ProgramKerjaService{

    @Autowired
    ProgramKerjaDb programKerjaDb;

    @Override
    public void saveProgramKerja(ProgramKerja programKerja) {
        if (programKerja != null) {
            programKerjaDb.save(programKerja);
        } else {
            throw new IllegalArgumentException("Program Kerja cannot be null.");
        }
    }

    @Override
    public List<ProgramKerja> getAllProgramKerja() {
        return programKerjaDb.findAll();
    }

    @Override
    public ProgramKerja findProgramKerjaById(Long id) {
        for (ProgramKerja programKerja : getAllProgramKerja()) {
            if (programKerja.getId().equals(id)) {
                return programKerja;
            }
        }
        return null;
    }

    @Override
    public List<String> getAllKategoriProgram() {
        List<String> kategoriProgram = new ArrayList<>();
        kategoriProgram.add("Bidang Kemanusiaan");
        kategoriProgram.add("Bidang Kesehatan");
        kategoriProgram.add("Bidang Pendidikan");
        kategoriProgram.add("Bidang Ekonomi");
        kategoriProgram.add("Bantuan Dakhwah-Advokasi");
        return kategoriProgram;
    }

    @Override
    public List<String> getAllKategoriAsnaf() {
        List<String> kategoriAsnaf = new ArrayList<>();
        kategoriAsnaf.add("Miskin");
        kategoriAsnaf.add("Mualaf");
        kategoriAsnaf.add("Riqab");
        kategoriAsnaf.add("Gharimin");
        kategoriAsnaf.add("Fi Sabilillah");
        kategoriAsnaf.add("Ibnu Sabil");
        return kategoriAsnaf;
    }

    @Override
    public ProgramKerja updateProgramKerja(ProgramKerja programKerjaFromDto){
        
        ProgramKerja programKerja = findProgramKerjaById(programKerjaFromDto.getId());
        if(programKerja != null){
            programKerja.setId(programKerjaFromDto.getId());
            programKerja.setJudul(programKerjaFromDto.getJudul());
            programKerja.setKategoriProgram(programKerjaFromDto.getKategoriProgram());
            programKerja.setKategoriAsnaf(programKerjaFromDto.getKategoriAsnaf());
            programKerja.setProvinsi(programKerjaFromDto.getProvinsi());
            programKerja.setKabupatenKota(programKerjaFromDto.getKabupatenKota());
            programKerja.setDeskripsi(programKerjaFromDto.getDeskripsi());
            programKerja.setFotoProgram(programKerjaFromDto.getFotoProgram());
            programKerjaDb.save(programKerja);
        }
        return programKerja;
    }

    public void handleFotoProgram(ProgramKerja programKerja) {
        byte[] fotoProgramByte = programKerja.getFotoProgram();

        String fotoProgramBase64 = Base64.getEncoder().encodeToString(fotoProgramByte);

        programKerja.setImageBase64(fotoProgramBase64);
    }

    @Override
    public List<ProgramKerja> findProgramKerjaByKategori(String kategori) {
        List<ProgramKerja> programsByCategory = new ArrayList<>();
        for (ProgramKerja programKerja : getAllProgramKerja()) {
            if (programKerja.getKategoriProgram().equals(kategori)) {
                programsByCategory.add(programKerja);
            }
        }
        return programsByCategory;
    }
    @Override
    public ProgramKerja findProgramKerjaByJudul(String judul) {
        for (ProgramKerja programKerja : getAllProgramKerja()) {
            if (programKerja.getJudul().equals(judul)) {
                return programKerja;
            }
        }
        return null;
    } 
}
