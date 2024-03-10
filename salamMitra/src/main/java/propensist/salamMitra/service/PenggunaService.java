package propensist.salamMitra.service;

import java.util.List;
import java.util.UUID;

import propensist.salamMitra.dto.request.CreateMitraRequestDTO;
import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Manajemen;
import propensist.salamMitra.model.Mitra;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramService;

public interface PenggunaService { 
  
    void saveAdmin(Admin admin);

    void saveProgramService(ProgramService programService);
    
    void saveManajemen(Manajemen manajemen);

    List<Pengguna> getAllPengguna();

    void deletePengguna(Pengguna pengguna);

    Pengguna findPenggunaById(UUID id);

    List<ProgramService> getAllProgramService();

    List<Manajemen> getAllManajemen();

    void saveMitra(Mitra mitra);

    Pengguna getAkunByEmail(String email);

    Pengguna authenticate(String username);

    Mitra createMitra(CreateMitraRequestDTO mitraDTO);

    Pengguna getUserByUsername(String username);

    Pengguna getUserById(UUID id);

    boolean gantiPassword(String username, String passwordLama, String passwordBaru);
}
