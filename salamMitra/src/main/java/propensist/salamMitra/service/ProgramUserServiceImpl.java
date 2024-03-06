package propensist.salamMitra.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import propensist.salamMitra.model.ProgramService;
import propensist.salamMitra.repository.ProgramUserServiceDb;

@Service
@Transactional
public class ProgramUserServiceImpl implements ProgramUserService {
    
    @Autowired
    ProgramUserServiceDb programUserServiceDb;

    @Override
    public ProgramService getProgramUserServiceById(UUID id) {
        for (ProgramService programService : programUserServiceDb.findAll()) {
            if (programService.getId().equals(id)) {
                return programService;
            }
        }
        return null;
    }
}
