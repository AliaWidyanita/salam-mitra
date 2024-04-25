package propensist.salamMitra.model;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifikasi")
public class Notifikasi {

    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    @NotNull
    @Column(name = "notified_date", nullable = false)
    private Date notifiedDate;

    @NotNull
    @Column(name = "read", nullable = false)
    private Boolean read = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pengguna", referencedColumnName = "id")
    private Pengguna pengguna;
    
}
