package ua.danit.clientbank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.danit.clientbank.dto.customer.CustomerRequest;
import ua.danit.clientbank.dto.customer.CustomerResponse;
import ua.danit.clientbank.facade.CustomerFacade;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class CustomerControllerTest {

    @Mock
    private CustomerFacade customerFacade;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void testCreateCustomer() throws Exception {
        CustomerRequest request = new CustomerRequest();
        CustomerResponse response = new CustomerResponse();
        response.setId(1L);

        given(customerFacade.createCustomer(request)).willReturn(response);

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"propertyName\":\"value\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Long id = 1L;
        CustomerRequest request = new CustomerRequest();
        CustomerResponse response = new CustomerResponse();

        given(customerFacade.updateCustomer(id, request)).willReturn(response);

        mockMvc.perform(put("/api/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"propertyName\":\"value\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/customers/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCustomerById() throws Exception {
        Long id = 1L;
        CustomerResponse response = new CustomerResponse();
        response.setId(id);

        given(customerFacade.getCustomerById(id)).willReturn(response);

        mockMvc.perform(get("/api/customers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        CustomerResponse response1 = new CustomerResponse();
        response1.setId(1L);
        CustomerResponse response2 = new CustomerResponse();
        response2.setId(2L);

        List<CustomerResponse> responses = Arrays.asList(response1, response2);
        Page<CustomerResponse> page = new PageImpl<>(responses, PageRequest.of(0, 10), responses.size());

        given(customerFacade.getAllCustomers(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/api/customers?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].id").value(1L))
                .andExpect(jsonPath("$.content[1].id").value(2L));
    }

}
