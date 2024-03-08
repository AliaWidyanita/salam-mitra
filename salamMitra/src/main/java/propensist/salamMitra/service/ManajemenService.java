package propensist.salamMitra.service;

import java.util.UUID;

import propensist.salamMitra.model.Manajemen;

public interface ManajemenService {

    Manajemen getManajemenById(UUID id);

    Manajemen getManajemenByUsername(String username);
}
