package ua.danit.clientbank.dto.customer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import jakarta.validation.constraints.*;
/**
 * description
 *
 * @author Alexander Isai on 20.08.2024.
 */
@Data
public class CustomerRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Min(value = 18, message = "Customer must be at least 18 years old")
    private Integer age;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phoneNumber;

}