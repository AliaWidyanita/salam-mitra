package propensist.salamMitra;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import jakarta.transaction.Transactional;
import propensist.salamMitra.dto.LokasiMapper;
import propensist.salamMitra.dto.request.CreateLokasiRequestDTO;
import propensist.salamMitra.model.Lokasi;
import propensist.salamMitra.service.LokasiService;

@SpringBootApplication
public class SalamMitraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalamMitraApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(LokasiService lokasiService, LokasiMapper lokasiMapper){
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
            for (CreateLokasiRequestDTO lokasiDTO : lokasiDTOs) {
                Lokasi lokasi = new Lokasi();
                lokasi.setProvinsi(lokasiDTO.getProvinsi());
                lokasi.setKabupatenKota(lokasiDTO.getKabupatenKota());
                lokasi.setKecamatan(lokasiDTO.getKecamatan());
                lokasiService.saveLokasi(lokasi);
            }
		
		};	
	}





}
