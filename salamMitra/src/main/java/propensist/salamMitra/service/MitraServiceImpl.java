package propensist.salamMitra.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import propensist.salamMitra.model.Mitra;
import propensist.salamMitra.repository.MitraDb;

@Service
@Transactional
public class MitraServiceImpl implements MitraService {
    
    @Autowired
    MitraDb mitraDb;

    @Override
    public Mitra getMitraById(UUID id) {
        for (Mitra mitra : mitraDb.findAll()) {
            if (mitra.getId().equals(id)) {
                return mitra;
            }
        }
        return null;
    }    
}
