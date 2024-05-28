package com.nashtech.rookies.ecommerce.controllers.user;

import com.nashtech.rookies.ecommerce.dto.user.requests.InforGetRequestParamsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.dto.user.responses.InforResponseDTO;
import com.nashtech.rookies.ecommerce.configs.RestVersionConfig;
import com.nashtech.rookies.ecommerce.dto.user.requests.InforRequestDTO;
import com.nashtech.rookies.ecommerce.services.user.InforService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(RestVersionConfig.API_VERSION + "/infors")
public class InforController {
  private final InforService inforService;

  public InforController(InforService inforService) {
    this.inforService = inforService;
  }

  @PostMapping()
  public ResponseEntity<InforResponseDTO> createInfor(@Valid @RequestBody InforRequestDTO inforRequestDTO) {
    return ResponseEntity.ok(inforService.createInfor(inforRequestDTO));
  }

  @GetMapping()
  public ResponseEntity<?> getInfors(@Valid @RequestParam(name = "id", required = false) Long id) {
      return inforService.handleGetInfor(new InforGetRequestParamsDTO(id));
  }

  @PutMapping()
  public ResponseEntity<InforResponseDTO> updateInfor(@Valid @RequestParam(name = "id") Long id,
      @RequestBody InforRequestDTO inforRequestDTO) {
    return ResponseEntity.ok(inforService.updateInfor(id, inforRequestDTO));
  }
}
