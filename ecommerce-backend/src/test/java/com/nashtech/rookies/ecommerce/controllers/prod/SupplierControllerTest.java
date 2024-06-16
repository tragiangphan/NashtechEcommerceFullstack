package com.nashtech.rookies.ecommerce.controllers.prod;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierPaginationDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.handlers.exceptions.NotFoundException;
import com.nashtech.rookies.ecommerce.handlers.exceptions.ResourceConflictException;
import com.nashtech.rookies.ecommerce.models.constants.ActiveModeEnum;
import com.nashtech.rookies.ecommerce.services.prod.SupplierService;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;

@SpringBootTest()
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupplierService supplierService;

    @InjectMocks
    private SupplierController supplierController;

    @Autowired
    private ObjectMapper objectMapper;

    private SupplierRequestDTO requestDTO;
    private SupplierResponseDTO responseDTO;
    private SupplierPaginationDTO responsePaginationDTO;
    private SupplierGetRequestParamsDTO requestParamsDTO;

    @BeforeEach
    void setUp() {
        openMocks(this);

        requestDTO = new SupplierRequestDTO(
                "Test Supplier",
                "1234567890",
                "test@supplier.com",
                "123 Test Address",
                "Test Street",
                "Test Ward",
                "Test City",
                "Test Country",
                "12345",
                ActiveModeEnum.ACTIVE,
                Set.of(1L, 2L));

        responseDTO = new SupplierResponseDTO(
                1L,
                "Test Supplier",
                "1234567890",
                "test@supplier.com",
                "123 Test Address",
                "Test Street",
                "Test Ward",
                "Test City",
                "Test Country",
                "12345",
                ActiveModeEnum.ACTIVE,
                Set.of(1L, 2L));

        responsePaginationDTO = new SupplierPaginationDTO(1, 1L, 10, 1, List.of(responseDTO));

        mockMvc = MockMvcBuilders.standaloneSetup(new SupplierController(supplierService)).build();
    }

    @Test
    void testCreateSupplier_WhenSupplierDoesNotExist_ShouldReturnStatusOK() throws Exception {
        when(supplierService.createSupplier(any(SupplierRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/suppliers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

//     @Test
//     void testCreateSupplier_WhenSupplierExisted_ShouldThrowStatusConflict() throws Exception {
//         when(supplierService.createSupplier(any(SupplierRequestDTO.class)))
//                 .thenThrow(new ResourceConflictException("Existed Supplier Name with an id: " + 1L));

//         mockMvc.perform(post("/api/v1/suppliers")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(requestDTO)))
//                 .andExpect(status().isConflict());
//     }

    @Test
    void testGetSupplier_WhenSupplierIdProvided_ShouldReturnStatusOK() throws Exception {
        // Arrange
        doReturn(ResponseEntity.ok(responseDTO))
                .when(supplierService).handleGetSupplier(any(SupplierGetRequestParamsDTO.class));

        mockMvc.perform(get("/api/v1/suppliers")
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void testGetSupplier_WhenSupplierNameProvided_ShouldReturnStatusOK() throws Exception {
        // Arrange
        doReturn(ResponseEntity.ok(responseDTO))
                .when(supplierService).handleGetSupplier(any(SupplierGetRequestParamsDTO.class));

        mockMvc.perform(get("/api/v1/suppliers")
                        .param("supplierName", requestDTO.supplierName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void testGetSupplier_WhenPaginationParamProvided_ShouldReturnStatusOK() throws Exception {
        // Calling the controller method
        doReturn(ResponseEntity.ok(responseDTO))
                .when(supplierService).handleGetSupplier(any(SupplierGetRequestParamsDTO.class));

        ResponseEntity<?> response = supplierService.handleGetSupplier(new SupplierGetRequestParamsDTO(null, null, Sort.Direction.ASC, 1,10));

        mockMvc.perform(get("/api/v1/suppliers")
                        .param("direction", "ASC")
                        .param("pageNum", "1")
                        .param("pageSize", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void testUpdateSupplier_WhenSupplierExistsAndProductsExist_ShouldReturnStatusOK() throws Exception {
        // Calling the controller method
        when(supplierService.updateSupplier(1L, requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/v1/suppliers")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

//     @Test
//     void testUpdateSupplier_WhenSupplierDoesNotExist_ShouldReturnNotFoundException() throws Exception {
//         // Calling the controller method
//         when(supplierService.updateSupplier(3L, requestDTO))
//                 .thenThrow(new NotFoundException("Not found Supplier with an id: " + 3L));

//         mockMvc.perform(put("/api/v1/suppliers")
//                         .param("id", "3")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(objectMapper.writeValueAsString(requestDTO)))
//                 .andExpect(status().isNotFound());
//     }
}
