package propensist.salamMitra.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pencairan")
public class Pencairan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sourceOfFound", nullable = false)
    private String sourceOfFound;

    @Column(name = "channeling", nullable = false)
    private String channeling;

    @Column(name = "jenisDana", nullable = false)
    private String jenisDana;

    @Column(name = "linkPencairan", nullable = false)
    private String linkPencairan;

    @Column(name = "tanggalPencairanSalamSetara", nullable = false)
    private Date tanggalPencairanSalamSetara;

    @Column(name = "tanggalPencairanMitra", nullable = false)
    private Date tanggalPencairanMitra;

    @Lob
    @Column(name = "buktiPencairanSalamSetara", nullable = false)
    private byte[] buktiPencairanSalamSetara;
    
    @Transient 
    private String buktiPencairanSalamSetaraBase;

    @Lob
    @Column(name = "buktiPencairanMitra", nullable = false)
    private byte[] buktiPencairanMitra;

    @Transient 
    private String buktiPencairanMitraBase;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pengajuan_id", referencedColumnName = "id")
    private Pengajuan pengajuan;

}
