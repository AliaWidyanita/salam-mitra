package propensist.salamMitra.service;

import java.util.UUID;

import propensist.salamMitra.model.ProgramService;

public interface ProgramUserService {
    
    ProgramService getProgramUserServiceById(UUID id);

    ProgramService getProgramUserServiceByUsername(String username);
}
