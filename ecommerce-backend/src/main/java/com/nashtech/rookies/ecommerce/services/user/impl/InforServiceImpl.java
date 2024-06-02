package com.nashtech.rookies.ecommerce.services.user.impl;

import java.util.ArrayList;
import java.util.List;

import com.nashtech.rookies.ecommerce.dto.user.requests.InforGetRequestParamsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nashtech.rookies.ecommerce.dto.user.requests.InforRequestDTO;
import com.nashtech.rookies.ecommerce.dto.user.responses.InforResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.mappers.user.InforMapper;
import com.nashtech.rookies.ecommerce.models.user.Infor;
import com.nashtech.rookies.ecommerce.repositories.user.InforRepository;
import com.nashtech.rookies.ecommerce.repositories.user.UserRepository;
import com.nashtech.rookies.ecommerce.services.CommonServiceImpl;
import com.nashtech.rookies.ecommerce.services.user.InforService;

@Service
@Transactional(readOnly = true)
public class InforServiceImpl extends CommonServiceImpl<Infor, Long> implements InforService {
  private final InforRepository inforRepository;
  private final InforMapper inforMapper;
  private final UserRepository userRepository;

  public InforServiceImpl(InforRepository inforRepository, UserRepository userRepository, InforMapper inforMapper) {
    super(inforRepository);
    this.inforRepository = inforRepository;
    this.inforMapper = inforMapper;
    this.userRepository = userRepository;
  }

  @Transactional
  public InforResponseDTO createInfor(InforRequestDTO inforRequestDTO) {
    if (userRepository.existsById(inforRequestDTO.userId())) {
      Infor infor = new Infor();
      infor.setAddress(inforRequestDTO.address());
      infor.setStreet(inforRequestDTO.street());
      infor.setWard(inforRequestDTO.ward());
      infor.setDistrict(inforRequestDTO.district());
      infor.setCity(inforRequestDTO.city());
      infor.setCountry(inforRequestDTO.country());
      infor.setPostalCode(inforRequestDTO.postalCode());
      infor.setUser(userRepository.findById(inforRequestDTO.userId()).get());
      infor = inforRepository.saveAndFlush(infor);
      return inforMapper.toResponseDTO(infor);
    } else {
      throw new NotFoundException("Not found User with an id: " + inforRequestDTO.userId());
    }
  }

  @Override
  public ResponseEntity<?> handleGetInfor(InforGetRequestParamsDTO requestParamsDTO) {
    List<InforResponseDTO> inforResponseDTOs;
    InforResponseDTO inforResponseDTO;
    if (requestParamsDTO.id() != null) {
      inforResponseDTO = getInforById(requestParamsDTO.id());
      return ResponseEntity.ok(inforResponseDTO);
    } else {
      inforResponseDTOs = getInfors();
      return ResponseEntity.ok(inforResponseDTOs);
    }
  }

  public List<InforResponseDTO> getInfors() {
    var infors = inforRepository.findAll();
    List<InforResponseDTO> inforResponseDTO = new ArrayList<>();
    infors.forEach(infor -> inforResponseDTO.add(inforMapper.toResponseDTO(infor)));
    return inforResponseDTO;
  }

  public InforResponseDTO getInforById(Long id) {
    if (inforRepository.existsById(id)) {
      return inforMapper.toResponseDTO(inforRepository.findById(id).get());
    } else {
      throw new NotFoundException("Not found Infor with an id: " + id);
    }
  }

  @Transactional
  public InforResponseDTO updateInfor(Long id, InforRequestDTO inforRequestDTO) {
    if (inforRepository.existsById(id)) {
      if (userRepository.existsById(inforRequestDTO.userId())) {
        Infor infor = inforRepository.findById(id).get();
        infor.setUser(userRepository.findById(inforRequestDTO.userId()).get());
        infor.setAddress(inforRequestDTO.address());
        infor.setStreet(inforRequestDTO.street());
        infor.setWard(inforRequestDTO.ward());
        infor.setDistrict(inforRequestDTO.district());
        infor.setCity(inforRequestDTO.city());
        infor.setCountry(inforRequestDTO.country());
        infor.setPostalCode(inforRequestDTO.postalCode());
        infor = inforRepository.saveAndFlush(infor);
        return inforMapper.toResponseDTO(infor);
      } else {
        throw new NotFoundException("Not found User with an id: " + inforRequestDTO.userId());
      }
    } else {
      throw new NotFoundException("Not found Infor with an id: " + id);
    }
  }
}
