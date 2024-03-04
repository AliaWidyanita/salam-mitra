package propensist.salamMitra.service;

import java.util.List;

import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Pengguna;

public interface PenggunaService {
    
    void saveAdmin(Admin admin);

    List<Pengguna> getAllPengguna();
}
