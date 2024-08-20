package ua.danit.clientbank.dto.account;
import lombok.Data;
import jakarta.validation.constraints.*;
import ua.danit.clientbank.model.Currency;
/**
 * description
 *
 * @author Alexander Isai on 20.08.2024.
 */
@Data
public class AccountRequest {

    @NotBlank(message = "Account number is required")
    private String number;

    @NotNull(message = "Currency is required")
    private Currency currency;

    private Double balance;
    @NotNull(message = "Customer ID is required")
    private Long customerId;
}


