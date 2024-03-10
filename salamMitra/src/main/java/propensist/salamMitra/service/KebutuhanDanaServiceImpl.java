package propensist.salamMitra.service;

import org.springframework.beans.factory.annotation.Autowired;

import propensist.salamMitra.model.KebutuhanDana;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.repository.KebutuhanDanaDb;
import propensist.salamMitra.repository.PengajuanDb;
import org.springframework.stereotype.Service;

@Service
public class KebutuhanDanaServiceImpl implements KebutuhanDanaService{
    @Autowired
    KebutuhanDanaDb kebutuhanDanaDb;

    @Override
    public void saveKebutuhanDana(KebutuhanDana kebutuhanDana) {
        
        kebutuhanDanaDb.save(kebutuhanDana);
    }
    
}
