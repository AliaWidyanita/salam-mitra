package propensist.salamMitra.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import propensist.salamMitra.model.Lokasi;
import propensist.salamMitra.repository.LokasiDb;

@Service
public class LokasiServiceImpl implements LokasiService{

    @Autowired
    LokasiDb lokasiDb;

    @Override
    public void saveLokasi(Lokasi lokasi) {
        lokasiDb.save(lokasi);
    }




    @Override
    public List<Lokasi> getAllLokasi() {
        return lokasiDb.findAll();
    }

    @Override
    public List<String> getAllProvinsi() {
        return lokasiDb.findAll().stream().map(Lokasi::getProvinsi).distinct().collect(Collectors.toList());
    }



    @Override
    public List<String> getAllKabupatenKotaByProvinsi(String provinsi) {
        return lokasiDb.findAllByProvinsi(provinsi).stream().map(Lokasi::getKabupatenKota).distinct().collect(Collectors.toList());
    }

    @Override
    public List<String> getAllKecamatanByKabupatenKota(String kabupatenKota) {
        return lokasiDb.findAllByKabupatenKota(kabupatenKota).stream().map(Lokasi::getKecamatan).distinct().collect(Collectors.toList());
    }
    @Override
    public List<String> getAllKecamatanByProvinsiKabupatenKota(String provinsi, String kabupatenKota) {
        return lokasiDb.findAllKecamatanByProvinsiAndKabupatenKota(provinsi, kabupatenKota);
    }

 
}

