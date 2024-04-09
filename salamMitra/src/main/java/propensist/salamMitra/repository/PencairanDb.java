package propensist.salamMitra.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.Pencairan;

@Repository
public interface PencairanDb extends JpaRepository<Pencairan, Long> {
  
}
