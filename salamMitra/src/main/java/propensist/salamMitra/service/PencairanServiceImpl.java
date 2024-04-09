package propensist.salamMitra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.repository.PengajuanDb;

import java.util.ArrayList;
import java.util.List;

@Service
public class PencairanServiceImpl implements PencairanService{

    @Autowired
    private PengajuanDb pengajuanDb;

    @Override
    public List<Pengajuan> getListPengajuanMenungguPencairanProgramService() {

        List<Pengajuan> pengajuanMenungguPencairan = pengajuanDb.findByStatus("Menunggu Pencairan oleh Program Service");
        List<Pengajuan> listPencairan = new ArrayList<>();

        for (Pengajuan pengajuan : pengajuanMenungguPencairan) {
            listPencairan.add(pengajuan);
        }

        return listPencairan;
    }

    @Override
    public List<Pengajuan> getListPengajuanMenungguPencairanAdminFinance() {
        
        List<Pengajuan> pengajuanMenungguPencairan = pengajuanDb.findByStatus("Menunggu Pencairan oleh Admin Finance");
        List<Pengajuan> listPencairan = new ArrayList<>();

        for (Pengajuan pengajuan : pengajuanMenungguPencairan) {
            listPencairan.add(pengajuan);
        }

        return listPencairan;
    }

    // @Override
    // public void handleImage(Pencairan pencairan) {
    //     byte[] imageByte = pencairan.getBuktiPencairanSalamSetara();
    //     String image = Base64.getEncoder().encodeToString(imageByte);
    //     pencairan.setImageBase64(image); // Set the imageBase64 field

    // }
}
