package propensist.salamMitra.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    AdminService adminService;

    @Autowired
    ManajemenService manajemenService;

    @Autowired
    MitraService mitraService;

    @Autowired
    ProgramUserService programUserService;

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

        // String hashedPass = encrypt(mitra.getPassword());
        // mitra.setPassword(hashedPass);
    }

    @Override
    public Pengguna getAkunByEmail(String email){
        Pengguna akun = penggunaDb.findByEmail(email);
        return akun;
    }

    @Override
    public Pengguna authenticate(String username) {
        return penggunaDb.findByUsername(username);
    }

    @Override
    public Pengguna getUserById(UUID id) {
        var admin = adminService.getAdminById(id);
        var manajemen = manajemenService.getManajemenById(id);
        var mitra = mitraService.getMitraById(id);
        var programService = programUserService.getProgramUserServiceById(id);

        if (admin == null && manajemen == null && mitra == null && programService == null) {
            return null;
        } else if (admin != null) {
            return admin;
        } else if (manajemen != null) {
            return manajemen;
        } else if (mitra != null) {
            return mitra;
        } else {
            return programService;
        }
    }

}
