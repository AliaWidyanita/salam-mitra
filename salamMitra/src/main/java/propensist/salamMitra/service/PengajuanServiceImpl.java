package propensist.salamMitra.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.repository.PengajuanDb;

import java.util.List;
import java.util.Base64;



@Service
public class PengajuanServiceImpl implements PengajuanService{

    @Autowired
    PengajuanDb pengajuanDb;

    @Override
    public void savePengajuan(Pengajuan pengajuan) {
        
        pengajuanDb.save(pengajuan);
    }
    
    @Override
    public List<Pengajuan> getAllPengajuan() {
        return pengajuanDb.findAll();
    }
    @Override
    public Optional<Pengajuan> getPengajuanById(Long id) {
        return pengajuanDb.findById(id);
    }

    


    @Override
    public void handleKTP(Pengajuan pengajuan) {
        byte[] ktpByte = pengajuan.getKtpPIC();
        String ktpImage = Base64.getEncoder().encodeToString(ktpByte);
        pengajuan.setImageBase64(ktpImage); // Set the imageBase64 field

    }

    @Override
    public void handleRAB(Pengajuan pengajuan) {
        byte[] rabByte = pengajuan.getRab();
        String rab = Base64.getEncoder().encodeToString(rabByte);
        pengajuan.setRabBase64(rab); // Set the imageBase64 field
    }

    @Override
    public void handleDOC(Pengajuan pengajuan) {
        byte[] dokumenByte = pengajuan.getDokumen();
        String dokumen = Base64.getEncoder().encodeToString(dokumenByte);
        pengajuan.setDokumenBase64(dokumen); 
    }

}

