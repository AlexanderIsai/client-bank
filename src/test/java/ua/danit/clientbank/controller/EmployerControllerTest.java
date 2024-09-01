package ua.danit.clientbank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.danit.clientbank.dto.employer.EmployerRequest;
import ua.danit.clientbank.dto.employer.EmployerResponse;
import ua.danit.clientbank.facade.EmployerFacade;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class EmployerControllerTest {

    @Mock
    private EmployerFacade employerFacade;

    @InjectMocks
    private EmployerController employerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employerController).build();
    }

    @Test
    void testCreateEmployer() throws Exception {
        EmployerRequest request = new EmployerRequest();
        EmployerResponse response = new EmployerResponse();
        response.setId(1L);

        given(employerFacade.createEmployer(any(EmployerRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/employers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"propertyName\":\"value\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateEmployer() throws Exception {
        Long id = 1L;
        EmployerRequest request = new EmployerRequest();
        EmployerResponse response = new EmployerResponse();

        given(employerFacade.updateEmployer(eq(id), any(EmployerRequest.class))).willReturn(response);

        mockMvc.perform(put("/api/employers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"propertyName\":\"value\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteEmployer() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/employers/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEmployerById() throws Exception {
        Long id = 1L;
        EmployerResponse response = new EmployerResponse();
        response.setId(id);

        given(employerFacade.getEmployerById(id)).willReturn(response);

        mockMvc.perform(get("/api/employers/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void testGetAllEmployers() throws Exception {
        List<EmployerResponse> responses = Arrays.asList(new EmployerResponse(), new EmployerResponse());
        responses.get(0).setId(1L);
        responses.get(1).setId(2L);

        given(employerFacade.getAllEmployers()).willReturn(responses);

        mockMvc.perform(get("/api/employers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }
}
