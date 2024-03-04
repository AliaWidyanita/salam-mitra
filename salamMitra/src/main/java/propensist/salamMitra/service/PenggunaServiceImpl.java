package propensist.salamMitra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.repository.AdminDb;
import propensist.salamMitra.repository.PenggunaDb;

@Service
public class PenggunaServiceImpl implements PenggunaService{
    
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

}
