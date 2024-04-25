package propensist.salamMitra.service;

import java.util.List;

import propensist.salamMitra.model.Notifikasi;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.model.Pengguna;

public interface NotifikasiService {

    void addNotifikasi(Pengajuan pengajuan);
    List<Notifikasi> getNotifikasiByUser(Pengguna pengguna); 
    
}
