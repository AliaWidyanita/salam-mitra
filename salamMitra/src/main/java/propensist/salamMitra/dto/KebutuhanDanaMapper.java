package propensist.salamMitra.dto;

import org.mapstruct.Mapper;
import propensist.salamMitra.dto.request.CreateKebutuhanDanaDTO;
import propensist.salamMitra.model.KebutuhanDana;

@Mapper(componentModel = "spring")
public interface KebutuhanDanaMapper {
     KebutuhanDana createKebutuhanDanaDTOToKebutuhanDana(CreateKebutuhanDanaDTO createKebutuhanDanaDTO);
    
}
