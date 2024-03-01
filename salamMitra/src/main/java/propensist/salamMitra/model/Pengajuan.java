package propensist.salamMitra.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pengajuan")
public class Pengajuan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "namaProgram", nullable = false)
    private String namaProgram;

    @NotNull
    @Column(name = "namaMitra", nullable = false)
    private String namaMitra;

    @NotNull
    @Column(name = "kategori", nullable = false)
    private String kategori;

    @NotNull
    @Column(name = "namaPIC", nullable = false)
    private String namaPIC;

    @NotNull
    @Column(name = "kontakPIC", nullable = false)
    private Long kontakPIC;

    @NotNull
    @Column(name = "ktpPIC", nullable = false)
    private Long ktpPIC;

    @NotNull
    @Column(name = "alamatKantor", nullable = false)
    private String alamatKantor;

    @NotNull
    @Column(name = "jumlahDana", nullable = false)
    private Long jumlahDana;

    @NotNull
    @Column(name = "jumlahOperasional", nullable = false)
    private Integer jumlahOperasional;

    @NotNull
    @Column(name = "tglMulaiImpl", nullable = false)
    private Date tglMulaiImpl;

    @NotNull
    @Column(name = "tglSelesaiImpl", nullable = false)
    private Date tglSelesaiImpl;

    @NotNull
    @Column(name = "tglSelesaiLaporan", nullable = false)
    private Date tglSelesaiLaporan;

    @NotNull
    @Column(name = "lokasiPenyaluran", nullable = false)
    private String lokasiPenyaluran;
}
