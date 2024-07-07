package com.cosmetics.controller;

import com.cosmetics.api.controller.VendorController;
import com.cosmetics.domain.vendor.dto.VendorManagement;
import com.cosmetics.domain.vendor.service.VendorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(VendorController.class)
public class VendControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    VendorService vendorService;

    @Test
    @DisplayName("업체등록 파라미터가 누락되었을 경우_validation handler_MethodArgumentNotValidException 테스트")
    public void validVendorTest() throws Exception {
        //given
        VendorManagement vendorManagement = requestValidVendor();

        mockMvc.perform(
                        post("http://localhost:8080/v1/vendor")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(vendorManagement)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("errorCode").value("INVALID_PARAMETER"))
                .andExpect(jsonPath("errorMessage").value("유효하지 않는 값입니다"))
                .andExpect(jsonPath("$.fieldErrorList[0].field").value(anyOf(is("vendorNm"), is("postNo"), is("addr"))))
                .andExpect(jsonPath("$.fieldErrorList[0].message").value("must not be blank"));

    }

    private static VendorManagement requestValidVendor() {
        return VendorManagement.builder()
                .bizNo("1111").build();
    }

}
