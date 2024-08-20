package ua.danit.clientbank.dto.employer;
import lombok.Data;
import jakarta.validation.constraints.*;
/**
 * description
 *
 * @author Alexander Isai on 20.08.2024.
 */
@Data
public class EmployerRequest {

    @NotBlank(message = "Company name is required")
    @Size(min = 3, message = "Company name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 3, message = "Address must be at least 3 characters long")
    private String address;
}