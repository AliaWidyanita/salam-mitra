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
import propensist.salamMitra.model.ProgramService;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PenggunaService;

@SpringBootApplication
public class SalamMitraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalamMitraApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(LokasiService lokasiService, LokasiMapper lokasiMapper, 
        PenggunaService penggunaService){
		return args -> {
			List<CreateLokasiRequestDTO> lokasiDTOs = new ArrayList<>();

            // Provinsi DKI Jakarta
            lokasiDTOs.add(new CreateLokasiRequestDTO("DKI Jakarta", "Jakarta Pusat", "Menteng"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("DKI Jakarta", "Jakarta Pusat", "Gambir"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("DKI Jakarta", "Jakarta Utara", "Tanjung Priok"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("DKI Jakarta", "Jakarta Utara", "Kelapa Gading"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("DKI Jakarta", "Jakarta Barat", "Kebon Jeruk"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("DKI Jakarta", "Jakarta Barat", "Palmerah"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("DKI Jakarta", "Jakarta Selatan", "Pancoran"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("DKI Jakarta", "Jakarta Selatan", "Kebayoran Baru"));


            // Provinsi Sumatera Barat
            lokasiDTOs.add(new CreateLokasiRequestDTO("Sumatera Barat", "Padang", "Pauh"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Sumatera Barat", "Padang", "Kuranji"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Sumatera Barat", "Bukittinggi", "Mandiangin Koto Selayan"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Sumatera Barat", "Bukittinggi", "Banuhampu"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Sumatera Barat", "Padang Panjang", "Lubuk Begalung"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Sumatera Barat", "Padang Panjang", "Padang Panjang Timur"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Sumatera Barat", "Payakumbuh", "Beringin"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Sumatera Barat", "Payakumbuh", "Payakumbuh"));


            // Provinsi Jawa Barat
            lokasiDTOs.add(new CreateLokasiRequestDTO("Jawa Barat", "Bandung", "Lengkong"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Jawa Barat", "Bandung", "Regol"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Jawa Barat", "Bogor", "Bogor Tengah"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Jawa Barat", "Bogor", "Bogor Utara"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Jawa Barat", "Depok", "Beji"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Jawa Barat", "Depok", "Cinere"));

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
                programService.setPassword(new BCryptPasswordEncoder().encode("programservice1"));
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
                manajemen.setPassword(new BCryptPasswordEncoder().encode("manajemen1"));
                manajemen.setFullName("Manajemen 1");
                manajemen.setAddress("Address 2");
                manajemen.setGender("Wanita");
                manajemen.setContact(1234567890L);
                penggunaService.saveManajemen(manajemen);
            }

            if (penggunaService.getAllAdmin().isEmpty()) {
                Admin admin = new Admin();
                admin.setUsername("adminprogram");
                admin.setEmail("adminprogram@salamsetara.com");
                admin.setPassword(new BCryptPasswordEncoder().encode("adminprogram"));
                admin.setFullName("AdminProgram");
                admin.setAddress("Address 3");
                admin.setGender("Pria");
                admin.setContact(1234567890L);
                admin.setAdminRole(Admin.AdminRole.PROGRAM);
                penggunaService.saveAdmin(admin);
            }
		};	
	}
}
