package propensist.salamMitra.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import propensist.salamMitra.model.Mitra;

public interface MitraDb extends JpaRepository<Mitra, UUID> {
  
}
