package propensist.salamMitra.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.Pengajuan;


@Repository
public interface PengajuanDb extends JpaRepository<Pengajuan, Long> {
    @SuppressWarnings("null")
    Optional<Pengajuan> findById(Long id);
}
