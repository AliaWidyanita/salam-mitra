package propensist.salamMitra.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateMitraRequestDTO {
    private String username;

    private String email;

    private String password;

    private String companyName;

    private String location;

    private Long contact;

    private boolean isDeleted = false;
}
