package propensist.salamMitra.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensist.salamMitra.dto.request.CreateMitraRequestDTO;
import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Manajemen;
import propensist.salamMitra.model.Mitra;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramService;
import propensist.salamMitra.repository.AdminDb;
import propensist.salamMitra.repository.ManajemenDb;
import propensist.salamMitra.repository.MitraDb;
import propensist.salamMitra.repository.PenggunaDb;
import propensist.salamMitra.repository.ProgramServiceDb;

@Service
public class PenggunaServiceImpl implements PenggunaService{

    @Autowired
    MitraDb mitraDb;
    
    @Autowired
    AdminDb adminDb;

    @Autowired
    ManajemenDb manajemenDb;

    @Autowired
    ProgramServiceDb programServiceDb;

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

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveAdmin(Admin admin) {
        if (admin != null) {
            adminDb.save(admin);
        } else {
            throw new IllegalArgumentException("Admin cannot be null");
        }
    }

    @Override
    public void saveProgramService(ProgramService programService) {
        if (programService != null) {
            programServiceDb.save(programService);
        } else {
            throw new IllegalArgumentException("Admin cannot be null");
        }
    }
    

    @Override
    public void saveManajemen(Manajemen manajemen) {
        if (manajemen != null) {
            manajemenDb.save(manajemen);
        } else {
            throw new IllegalArgumentException("Admin cannot be null");
        }
    }

    @Override
    public List<ProgramService> getAllProgramService() {
        return programServiceDb.findAll();
    }

    @Override
    public List<Pengguna> getAllPengguna() {
        return penggunaDb.findAllByIsDeletedFalse();
    }

    @Override
    public List<Manajemen> getAllManajemen() {
        return manajemenDb.findAll();
    }

    @Override
    public List<Manajemen> getAllManajemen() {
        return manajemenDb.findAll();
    }

    @Override
    public void saveMitra(Mitra mitra) {
        if (mitra != null) {
            mitraDb.save(mitra);
        } else {
            throw new IllegalArgumentException("Username cannot be null");
        }
    }

    @Override
    public Pengguna getAkunByEmail(String email){
        return penggunaDb.findByEmail(email);
    }

    @Override
    public Pengguna authenticate(String username) {
        return penggunaDb.findByUsername(username);
    }

    @Override
    public Mitra createMitra(CreateMitraRequestDTO mitraDTO) {
        var user = new Mitra();
        user.setUsername(mitraDTO.getUsername());
        user.setEmail(mitraDTO.getEmail());
        user.setPassword(mitraDTO.getPassword());
        user.setCompanyName(mitraDTO.getCompanyName());
        user.setLocation(mitraDTO.getLocation());
        user.setContact(mitraDTO.getContact());
        user.setDeleted(mitraDTO.isDeleted());
        saveMitra(user);
        return user;
    }

    @Override
    public Pengguna getUserByUsername(String username) {
        var admin = adminService.getAdminByUsername(username);
        var manajemen = manajemenService.getManajemenByUsername(username);
        var mitra = mitraService.getMitraByUsername(username);
        var programService = programUserService.getProgramUserServiceByUsername(username);

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

    @Override
    public void deletePengguna(Pengguna pengguna) {
        pengguna.setDeleted(true);
        penggunaDb.save(pengguna);
    }

    @Override
    public Pengguna findPenggunaById(UUID id) {
        for (Pengguna pengguna : getAllPengguna()) {
            if (pengguna.getId().equals(id)) {
                return pengguna;
            }
        }
        return null;
    }

    public boolean gantiPassword(String id, String passwordLama, String passwordBaru) {
        Pengguna pengguna = findPenggunaById(UUID.fromString(id));

        if (pengguna != null && passwordEncoder.matches(passwordLama, pengguna.getPassword())) {
            pengguna.setPassword(passwordEncoder.encode(passwordBaru));
            penggunaDb.save(pengguna);
            return true;
        }

        return false;
    }

}
