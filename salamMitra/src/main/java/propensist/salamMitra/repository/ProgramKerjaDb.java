package propensist.salamMitra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.ProgramKerja;


@Repository
public interface ProgramKerjaDb extends JpaRepository<ProgramKerja, Long> {
    
}
