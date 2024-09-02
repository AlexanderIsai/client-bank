package ua.danit.clientbank.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.danit.clientbank.dto.account.AccountRequest;
import ua.danit.clientbank.dto.account.AccountResponse;
import ua.danit.clientbank.facade.AccountFacade;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

class AccountControllerTest {

    @Mock
    private AccountFacade accountFacade;

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void testCreateAccount() throws Exception {
        AccountRequest request = new AccountRequest();
        AccountResponse response = new AccountResponse();
        response.setId(1L);

        given(accountFacade.createAccount(request)).willReturn(response);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"propertyName\":\"value\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testUpdateAccount() throws Exception {
        Long id = 1L;
        AccountRequest request = new AccountRequest();
        AccountResponse response = new AccountResponse();

        given(accountFacade.updateAccount(id, request)).willReturn(response);

        mockMvc.perform(put("/api/accounts/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"propertyName\":\"value\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteAccount() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/accounts/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAccountById() throws Exception {
        Long id = 1L;
        AccountResponse response = new AccountResponse();
        response.setId(id);

        given(accountFacade.getAccountById(id)).willReturn(response);

        mockMvc.perform(get("/api/accounts/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));
    }

    @Test
    void testGetAllAccounts() throws Exception {
        List<AccountResponse> responses = Arrays.asList(new AccountResponse(), new AccountResponse());
        responses.get(0).setId(1L);
        responses.get(1).setId(2L);

        given(accountFacade.getAllAccounts()).willReturn(responses);

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

}
