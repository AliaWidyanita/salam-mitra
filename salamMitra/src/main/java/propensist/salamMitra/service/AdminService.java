package propensist.salamMitra.service;

import java.util.UUID;

import propensist.salamMitra.model.Admin;

public interface AdminService {
    
    Admin getAdminById(UUID id);

    Admin getAdminByUsername(String username);
}
