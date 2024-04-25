package propensist.salamMitra.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.Notifikasi;
import propensist.salamMitra.model.Pengguna;

import java.util.List;


@Repository
public interface NotfikasiDb extends JpaRepository<Notifikasi, UUID> {

    List<Notifikasi> findByPengguna(Pengguna pengguna);
    
}
