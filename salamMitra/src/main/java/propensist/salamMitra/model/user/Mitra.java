package propensist.salamMitra.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mitra")
@SQLDelete(sql = "UPDATE mitra SET is_deleted = true WHERE id=?")
public class Mitra extends UserModel {
    @NotNull
    @Column(name = "companyName", nullable = false)
    private String companyName;

    @NotNull
    @Column(name = "location", nullable = false)
    private String location;

    @NotNull
    @Column(name = "contact", nullable = false)
    private Long contact;
}
