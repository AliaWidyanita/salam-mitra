package propensist.salamMitra.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.ProgramService;

@Repository
public interface ProgramUserServiceDb extends JpaRepository<ProgramService, UUID> {
    
}
