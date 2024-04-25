package propensist.salamMitra.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Admin.AdminRole;
import propensist.salamMitra.model.Manajemen;
import propensist.salamMitra.model.Notifikasi;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramService;
import propensist.salamMitra.repository.NotfikasiDb;
import propensist.salamMitra.repository.PenggunaDb;

@Service
public class NotifikasiServiceImpl implements NotifikasiService {

    @Autowired
    private NotfikasiDb notifikasiDb;

    @Autowired
    private PenggunaService penggunaService;

    @Override
    public void addNotifikasi(Pengajuan pengajuan) {
        String status = pengajuan.getStatus();
        Long idPengajuan = pengajuan.getId();
        Notifikasi notifikasi = new Notifikasi();
        notifikasi.setNotifiedDate(new Date());
        List<Admin> listAdmin = penggunaService.getAllAdmin();
        List<ProgramService> listProgramService = penggunaService.getAllProgramService();
        List<Manajemen> listManajemen = penggunaService.getAllManajemen();

        switch(status){
            case("Diajukan"):
                notifikasi.setMessage("Terdapat ajuan kerja sama baru! Silakan periksa ajuan dengan ID " + idPengajuan + ".");

                for (Admin admin : listAdmin) {
                    if (admin.getAdminRole() == AdminRole.PROGRAM) {
                        notifikasi.setPengguna(admin);
                        notifikasiDb.save(notifikasi);
                    }
                }
                break; 
            
            case("Sedang Diperiksa"):
                notifikasi.setMessage("Ajuan dengan ID " + idPengajuan + " sedang diperiksa. Silakan tunggu pemberitahuan selanjutnya!");
                notifikasi.setPengguna(pengajuan.getMitra());
                notifikasiDb.save(notifikasi);
                break; 

            case("Diteruskan ke Manajemen"):
                notifikasi.setMessage("Ajuan dengan ID " + idPengajuan + " diteruskan ke manajemen. Silakan periksa ajuan!");
                for (Manajemen manajemen : listManajemen) {
                    notifikasi.setPengguna(manajemen);
                    notifikasiDb.save(notifikasi);
                }
                break; 
            
            case("Menunggu Pencairan Dana oleh Program Service"):
                notifikasi.setMessage("Ajuan dengan ID " + idPengajuan + " menunggu pencairan dana. Silakan lakukan pencairan ke rekening Salam Setara");
                for (ProgramService programService : listProgramService) {
                    notifikasi.setPengguna(programService);
                    notifikasiDb.save(notifikasi);
                }

                notifikasi.setMessage("Ajuan Anda dengan ID " + idPengajuan + " telah disetujui. Silakan tunggu dana dicairkan!");
                notifikasi.setPengguna(pengajuan.getMitra());
                notifikasiDb.save(notifikasi);
                break; 

            case("Menunggu Pencairana Dana oleh Admin Finance"):
                notifikasi.setMessage("Dana ajuan dengan ID " + idPengajuan + " telah dicairkan ke rekening Salam Setara. Silakan lakukan pencairan dana ke rekening mitra!");
                for (Admin admin : listAdmin) {
                    if (admin.getAdminRole() == AdminRole.FINANCE) {
                        notifikasi.setPengguna(admin);
                        notifikasiDb.save(notifikasi);
                    }
                }
                break; 

            case("Menunggu Laporan"):
                notifikasi.setMessage("Dana ajuan Anda dengan ID " + idPengajuan + " telah dicairkan. Silakan unggah laporan aktivitas kerja sama sesuai tenggat waktu.");
                notifikasi.setPengguna(pengajuan.getMitra());
                notifikasiDb.save(notifikasi);
                break; 

            case("Selesai"):
                notifikasi.setMessage("Laporan ajuan dengan ID" + idPengajuan + " telah diunggah. Kerja sama telah selesai.");
                for (Admin admin : listAdmin) {
                    if (admin.getAdminRole() == AdminRole.PROGRAM) {
                        notifikasi.setPengguna(admin);
                        notifikasiDb.save(notifikasi);
                    }
                }
                break; 
            
            case("Perlu Revisi"):
                notifikasi.setMessage("Ajuan Anda dengan ID " + idPengajuan + " perlu direvisi! Silakan cek komentar untuk melakukan revisi.");
                notifikasi.setPengguna(pengajuan.getMitra());
                notifikasiDb.save(notifikasi);
                break; 

            case("Dibatalkan"):
                notifikasi.setMessage("Ajuan dengan ID " + idPengajuan + " telah dibatalkan oleh mitra.");
                
                for (Admin admin : listAdmin) {
                    if (admin.getAdminRole() == AdminRole.PROGRAM) {
                        notifikasi.setPengguna(admin);
                        notifikasiDb.save(notifikasi);
                    }
                }
                break; 

            case("Ditolak"):
                notifikasi.setMessage("Mohon maaf. Ajuan Anda dengan ID " + idPengajuan + " telah ditolak! Silakan ajukan ajuan lainnya.");
                notifikasi.setPengguna(pengajuan.getMitra());
                notifikasiDb.save(notifikasi);
                break; 
        }
    }

    @Override
    public List<Notifikasi> getNotifikasiByUser(Pengguna pengguna) {
        return notifikasiDb.findByPengguna(pengguna);
    }
    
}
