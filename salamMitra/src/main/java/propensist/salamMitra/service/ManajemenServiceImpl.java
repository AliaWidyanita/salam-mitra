package propensist.salamMitra.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import propensist.salamMitra.model.Manajemen;
import propensist.salamMitra.repository.ManajemenDb;

@Service
@Transactional
public class ManajemenServiceImpl implements ManajemenService {
    
    @Autowired
    ManajemenDb manajemenDb;

    @Override
    public Manajemen getManajemenById(UUID id) {
        for (Manajemen manajemen : manajemenDb.findAll()) {
            if (manajemen.getId().equals(id)) {
                return manajemen;
            }
        }
        return null;
    }

    @Override
    public Manajemen getManajemenByUsername(String username) {
        for (Manajemen manajemen : manajemenDb.findAll()) {
            if (manajemen.getUsername().equals(username)) {
                return manajemen;
            }
        }
        return null;
    }
}
