package propensist.salamMitra.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CreateProgramKerjaRequestDTO {

    private String judul;

    private String kategoriProgram;

    private List<String> kategoriAsnaf;

    private String deskripsi;

    private List<String> provinsi;  

    private List<String> kabupatenKota;  

    private byte[] fotoProgram;
}
