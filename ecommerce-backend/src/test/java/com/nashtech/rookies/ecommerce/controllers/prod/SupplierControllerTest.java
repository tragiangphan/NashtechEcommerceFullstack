package com.nashtech.rookies.ecommerce.controllers.prod;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierGetRequestParamsDTO;
import com.nashtech.rookies.ecommerce.dto.prod.requests.SupplierRequestDTO;
import com.nashtech.rookies.ecommerce.dto.prod.responses.SupplierResponseDTO;
import com.nashtech.rookies.ecommerce.services.prod.SupplierService;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.ArrayList;

@WebMvcTest(SupplierController.class)
class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SupplierService supplierService;

    @InjectMocks
    private SupplierController supplierController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSupplier() throws Exception {
        SupplierRequestDTO requestDTO = new SupplierRequestDTO();
        // Set properties of requestDTO

        SupplierResponseDTO responseDTO = new SupplierResponseDTO();
        // Set properties of responseDTO

        when(supplierService.createSupplier(any(SupplierRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/suppliers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void testGetSupplier() throws Exception {
        SupplierGetRequestParamsDTO paramsDTO = new SupplierGetRequestParamsDTO();
        // Set properties of paramsDTO

        when(supplierService.handleGetSupplier(any(SupplierGetRequestParamsDTO.class))).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(get("/v1/suppliers")
                .param("id", "1")
                .param("productId", "1")
                .param("supplierName", "Test Supplier")
                .param("direction", "ASC")
                .param("pageNum", "0")
                .param("pageSize", "10"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSupplier() throws Exception {
        SupplierRequestDTO requestDTO = new SupplierRequestDTO();
        // Set properties of requestDTO

        SupplierResponseDTO responseDTO = new SupplierResponseDTO();
        // Set properties of responseDTO

        when(supplierService.updateSupplier(anyLong(), any(SupplierRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/v1/suppliers")
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }
}

