package com.nashtech.rookies.ecommerce.services.user;

import com.nashtech.rookies.ecommerce.dto.user.requests.InforGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.InforRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.InforResponseDTO;
import com.nashtech.rookies.ecommerce.models.user.Infor;
import com.nashtech.rookies.ecommerce.services.CommonService;
import org.springframework.http.ResponseEntity;

public interface InforService extends CommonService<Infor, Long> {
    InforResponseDTO createInfor(InforRequestDTO inforRequestDTO);

    ResponseEntity<?> handleGetInfor(InforGetRequestParamsDTO requestParamsDTO);

    InforResponseDTO updateInfor(Long id, InforRequestDTO inforRequestDTO);
}
