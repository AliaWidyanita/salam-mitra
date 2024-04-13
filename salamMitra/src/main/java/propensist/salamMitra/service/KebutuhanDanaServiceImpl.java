package propensist.salamMitra.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import propensist.salamMitra.model.KebutuhanDana;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.repository.KebutuhanDanaDb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KebutuhanDanaServiceImpl implements KebutuhanDanaService{
    @Autowired
    KebutuhanDanaDb kebutuhanDanaDb;

    @Override
    public void saveKebutuhanDana(KebutuhanDana kebutuhanDana) {
        if (kebutuhanDana != null) {
            kebutuhanDanaDb.save(kebutuhanDana);
        } else {
            throw new IllegalArgumentException("Lokasi tidak bboleh kosong.");
        }
    }

    @Override
    @Transactional
    public void clearKebutuhanDanaByPengajuan(Pengajuan pengajuan) {
        kebutuhanDanaDb.deleteByPengajuan(pengajuan);
    }
}
