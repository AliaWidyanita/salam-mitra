package propensist.salamMitra.dto;
import org.mapstruct.Mapper;

import propensist.salamMitra.dto.request.CreatePengajuanRequestDTO;
import propensist.salamMitra.model.Pengajuan;


@Mapper(componentModel = "spring")
public interface PengajuanMapper {
    Pengajuan createPengajuanRequestDTOToPengajuan(CreatePengajuanRequestDTO createPengajuanRequestDTO);
    
}
