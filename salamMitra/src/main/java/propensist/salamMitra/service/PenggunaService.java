package propensist.salamMitra.service;

import java.util.List;

import propensist.salamMitra.dto.request.LoginJwtRequestDTO;
import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Mitra;
import propensist.salamMitra.model.Pengguna;

public interface PenggunaService { 
    
    
    void saveAdmin(Admin admin);

    List<Pengguna> getAllPengguna();

    void saveMitra(Mitra mitra);

    //String encrypt(String password);

    Pengguna getAkunByEmail(String email);

    Pengguna authenticate(String username);


}
