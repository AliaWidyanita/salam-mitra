package propensist.salamMitra.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.Pengguna;

@Repository
public interface PenggunaDb extends JpaRepository<Pengguna, UUID> {
    
}
