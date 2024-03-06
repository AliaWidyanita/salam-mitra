package propensist.salamMitra.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import propensist.salamMitra.model.Admin;
import propensist.salamMitra.repository.AdminDb;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    
    @Autowired
    AdminDb adminDb;

    @Override
    public Admin getAdminById(UUID id) {
        for (Admin admin : adminDb.findAll()) {
            if (admin.getId().equals(id)) {
                return admin;
            }
        }
        return null;
    }
}
