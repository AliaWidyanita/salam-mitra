package propensist.salamMitra.dto.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import java.math.BigInteger;
import lombok.Data;

import java.util.List;

import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateListPengajuanKebutuhanDanaDTO {
    private List<CreateKebutuhanDanaDTO> listKebutuhanDanaDTO;
    private CreatePengajuanRequestDTO pengajuanDTO;
}
