package com.nashtech.rookies.ecommerce.controllers.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nashtech.rookies.ecommerce.controllers.RestVersion;
import com.nashtech.rookies.ecommerce.dto.user.responses.InforResponseDTO;
import com.nashtech.rookies.ecommerce.dto.user.requests.InforRequestDTO;
import com.nashtech.rookies.ecommerce.services.user.InforService;

import jakarta.validation.Valid;

@RestController
public class InforController extends RestVersion {
  private final InforService inforService;

  public InforController(InforService inforService) {
    this.inforService = inforService;
  }

  @PostMapping("/infors")
  public ResponseEntity<InforResponseDTO> createInfor(@Valid @RequestBody InforRequestDTO inforRequestDTO) {
    return ResponseEntity.ok(inforService.createInfor(inforRequestDTO));
  }

  @GetMapping("/infors")
  public ResponseEntity<List<InforResponseDTO>> getInfors(@Valid @RequestParam(name = "id", required = false) Long id) {
    List<InforResponseDTO> inforResponseDTO;
    if (id != null) {
      inforResponseDTO = inforService.getInfors(id);
    } else {
      inforResponseDTO = inforService.getInfors();
    }
    return ResponseEntity.ok(inforResponseDTO);
  }

  @PutMapping("/infors")
  public ResponseEntity<InforResponseDTO> updateInfor(@Valid @RequestParam(name = "id", required = true) Long id,
      @RequestBody InforRequestDTO inforRequestDTO) {
    return ResponseEntity.ok(inforService.updateInfor(id, inforRequestDTO));
  }
}