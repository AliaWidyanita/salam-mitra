package propensist.salamMitra;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.transaction.Transactional;
import propensist.salamMitra.dto.LokasiMapper;
import propensist.salamMitra.dto.request.CreateLokasiRequestDTO;
import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Lokasi;
import propensist.salamMitra.model.Manajemen;
import propensist.salamMitra.model.Mitra;
import propensist.salamMitra.model.ProgramKerja;
import propensist.salamMitra.model.ProgramService;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PenggunaService;
import propensist.salamMitra.service.ProgramKerjaService;

@SpringBootApplication
public class SalamMitraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalamMitraApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(LokasiService lokasiService, LokasiMapper lokasiMapper, 
        PenggunaService penggunaService, ProgramKerjaService programKerjaService){
		return args -> {
			List<CreateLokasiRequestDTO> lokasiDTOs = new ArrayList<>();

            // Provinsi Provinsi Dump
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dump", "Kota Dump 1", "Kec. A"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dump", "Kota Dump 1", "Kec. B"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dump", "Kota Dump 2", "Kec. C"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dump", "Kota Dump 2", "Kec. D"));




            // Simpan lokasi ke database menggunakan LokasiService
            if (lokasiService.getAllLokasi().isEmpty()) {
                for (CreateLokasiRequestDTO lokasiDTO : lokasiDTOs) {
                    Lokasi lokasi = new Lokasi();
                    lokasi.setProvinsi(lokasiDTO.getProvinsi());
                    lokasi.setKabupatenKota(lokasiDTO.getKabupatenKota());
                    lokasi.setKecamatan(lokasiDTO.getKecamatan());
                    lokasiService.saveLokasi(lokasi);
                }
            }

            if (penggunaService.getAllProgramService().isEmpty()){
                ProgramService programService = new ProgramService();
                programService.setUsername("programservice1");
                programService.setEmail("programservice1@salamsetara.com");
                programService.setPassword(new BCryptPasswordEncoder().encode("Programservice1"));
                programService.setFullName("Program Service 1");
                programService.setAddress("Address 1");
                programService.setGender("Pria");
                programService.setContact(1234567890L);
                penggunaService.saveProgramService(programService);
            }

            if (penggunaService.getAllManajemen().isEmpty()){
                Manajemen manajemen = new Manajemen();
                manajemen.setUsername("manajemen1");
                manajemen.setEmail("manajemen1@salamsetara.com");
                manajemen.setPassword(new BCryptPasswordEncoder().encode("Manajemen1"));
                manajemen.setFullName("Manajemen 1");
                manajemen.setAddress("Address 2");
                manajemen.setGender("Wanita");
                manajemen.setContact(1234567890L);
                penggunaService.saveManajemen(manajemen);
            }

            
                Mitra mitra = new Mitra();
                mitra.setUsername("mitra1");
                mitra.setEmail("manajemen1@salamsetara.com");
                mitra.setPassword(new BCryptPasswordEncoder().encode("Mitranad1"));
                mitra.setCompanyName("Com 2");
                mitra.setLocation("Address 2");
                mitra.setContact(1234567890L);
                penggunaService.saveMitra(mitra);
            

            if (penggunaService.getAllAdmin().isEmpty()) {
                Admin admin = new Admin();
                admin.setUsername("adminprogram1");
                admin.setEmail("adminprogram@salamsetara.com");
                admin.setPassword(new BCryptPasswordEncoder().encode("Adminprogram1"));
                admin.setFullName("Admin Program 1");
                admin.setAddress("Address 3");
                admin.setGender("Pria");
                admin.setContact(1234567890L);
                admin.setAdminRole(Admin.AdminRole.PROGRAM);
                penggunaService.saveAdmin(admin);
            }

            if (programKerjaService.getAllProgramKerja().isEmpty()){
                ProgramKerja programKerja = new ProgramKerja();
                programKerja.setJudul("Judul1");
                programKerja.setKategori("Pendidikan");
                programKerja.setDeskripsi("Desc1");
                programKerja.setEligibilitas("Eli1");
                programKerja.setSyarat("Syarat1");
                programKerja.setForm("Dummy1");

                ProgramKerja programKerja2 = new ProgramKerja();
                programKerja2.setJudul("Judul2");
                programKerja2.setKategori("Kesehatan");
                programKerja2.setDeskripsi("Des");
                programKerja2.setEligibilitas("El21");
                programKerja.setSyarat("Syarat2");
                programKerja2.setForm("Dummy2");

                programKerjaService.saveProgramKerja(programKerja);
                programKerjaService.saveProgramKerja(programKerja2);

            }
		};	
	}
}
