package propensist.salamMitra.service;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensist.salamMitra.model.Pencairan;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.repository.PencairanDb;
import propensist.salamMitra.repository.PengajuanDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Base64;

@Service
public class PencairanServiceImpl implements PencairanService{

    @Autowired
    private PengajuanDb pengajuanDb;

    @Autowired
    private PencairanDb pencairanDb;

    @Override
    public List<Pengajuan> getListPengajuanMenungguPencairan() {
        List<Pengajuan> allPengajuan = pengajuanDb.findAll();
        List<Pengajuan> listPencairan = new ArrayList<>();
        
        ArrayList<String> allowedStatus = new ArrayList<>(Arrays.asList(
            "Menunggu Pencairan oleh Program Service",
            "Menunggu Pencairan oleh Admin Finance",
            "Menunggu Laporan",
            "Selesai"
        ));

        for (Pengajuan pengajuan : allPengajuan) {
            String status = pengajuan.getStatus();
            if (allowedStatus.contains(status)) {
                listPencairan.add(pengajuan);
            }
        }
        
        return listPencairan;
    }

    @Override
    public List<Pengajuan> getListPengajuanMenungguPencairanAdminFinance() {
        
        List<Pengajuan> allPengajuan = pengajuanDb.findAll();
        List<Pengajuan> listPencairan = new ArrayList<>();

        ArrayList<String> allowedStatus = new ArrayList<>(Arrays.asList(
            "Menunggu Pencairan oleh Admin Finance",
            "Menunggu Laporan",
            "Selesai"
        ));
    
        for (Pengajuan pengajuan : allPengajuan) {
            String status = pengajuan.getStatus();
            if (allowedStatus.contains(status)) {
                listPencairan.add(pengajuan);
            }
        }
    
        System.out.println(listPencairan);
        return listPencairan;
    }

    @Override
    public void savePencairan(Pencairan pencairan) {
        if (pencairan != null) {
            pencairanDb.save(pencairan);
        } else {
            throw new IllegalArgumentException("Pencairan cannot be null");
        }
    }

    @Override
    public String convertByteToImage(byte[] byteArray) {
        return Base64.getEncoder().encodeToString(byteArray);
    }
}

