package propensist.salamMitra.dto.request;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import propensist.salamMitra.model.KebutuhanDana;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.List;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePengajuanRequestDTO {
    private String namaMitra;

    private String namaProgram;

    private String kategori;

    private String namaPIC;

    private String kontakPIC;

    private String alamatKantor;

    private List<KebutuhanDana> listKebutuhanDana = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify the format here
    private Date tanggalMulai;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify the format here
    private Date tanggalSelesai;

    @DateTimeFormat(pattern = "yyyy-MM-dd") // Specify the format here
    private Date tanggalLaporan;

    private String provinsi;

    private String kabupatenKota;

    private String kecamatan;

    private String alamatLengkap;
    
}
