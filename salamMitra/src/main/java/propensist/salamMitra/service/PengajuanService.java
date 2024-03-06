package propensist.salamMitra.service;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import propensist.salamMitra.model.Pengajuan;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.repository.PengajuanDb;

public interface PengajuanService {
    void savePengajuan(Pengajuan pengajuan);    
    List<Pengajuan> getAllPengajuan();
    Optional<Pengajuan> getPengajuanById(Long id);
    void handleKTP(Pengajuan pengajuan);
    void handleRAB(Pengajuan pengajuan);
    void handleDOC(Pengajuan pengajuan);

}
