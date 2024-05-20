package com.nashtech.rookies.ecommerce.services.user;

import java.util.List;

import com.nashtech.rookies.ecommerce.dto.user.requests.InforRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.InforResponseDTO;
import com.nashtech.rookies.ecommerce.models.user.Infor;
import com.nashtech.rookies.ecommerce.services.CommonService;

public interface InforService extends CommonService<Infor, Long>{
  InforResponseDTO createInfor(InforRequestDTO inforRequestDTO);
  List<InforResponseDTO> getInfors();
  List<InforResponseDTO> getInfors(Long id);
  InforResponseDTO updateInfor(Long id, InforRequestDTO inforRequestDTO);
}
