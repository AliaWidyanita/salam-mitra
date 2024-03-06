package propensist.salamMitra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import propensist.salamMitra.dto.request.LoginJwtRequestDTO;
import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Mitra;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.repository.AdminDb;
import propensist.salamMitra.repository.MitraDb;
import propensist.salamMitra.repository.PenggunaDb;

@Service
public class PenggunaServiceImpl implements PenggunaService{

    @Autowired
    MitraDb mitraDb;
    
    @Autowired
    AdminDb adminDb;

    @Autowired
    PenggunaDb penggunaDb;

    @Override
    public void saveAdmin(Admin admin) {
        if (admin != null) {
            adminDb.save(admin);
        } else {
            throw new IllegalArgumentException("Admin cannot be null");
        }
    }

    @Override
    public List<Pengguna> getAllPengguna() {
        return penggunaDb.findAll();
    }

    @Override
    public void saveMitra(Mitra mitra) {
        if (mitra != null) {
            mitraDb.save(mitra);
        } else {
            throw new IllegalArgumentException("Mitra cannot be null");
        }
    }

    // @Override
    // public String encrypt(String password){
    //     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    //     return passwordEncoder.encode(password);
    // }

    @Override
    public Pengguna authenticate(String username) {
        return penggunaDb.findByUsername(username);
    }

   
}
