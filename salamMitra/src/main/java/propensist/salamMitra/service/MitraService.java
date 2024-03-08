package propensist.salamMitra.service;

import java.util.UUID;

import propensist.salamMitra.model.Mitra;

public interface MitraService {
    
    Mitra getMitraById(UUID id);

    Mitra getMitraByUsername(String username);
}
