package ua.danit.clientbank.dto.account;

import lombok.Data;
import ua.danit.clientbank.model.Currency;

/**
 * description
 *
 * @author Alexander Isai on 20.08.2024.
 */
@Data
public class AccountResponse {

    private Long id;
    private String number;
    private Currency currency;
    private Double balance;
}