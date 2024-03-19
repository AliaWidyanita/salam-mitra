package propensist.salamMitra.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import propensist.salamMitra.model.ProgramKerja;
import propensist.salamMitra.repository.ProgramKerjaDb;
import org.springframework.stereotype.Service;


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
    public List<String> getAllKategori() {
        return programKerjaDb.findAll().stream().map(ProgramKerja::getKategori).distinct().collect(Collectors.toList());
    }
    
}
