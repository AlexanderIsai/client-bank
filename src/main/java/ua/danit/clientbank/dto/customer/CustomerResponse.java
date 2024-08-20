package ua.danit.clientbank.dto.customer;
import lombok.Data;
/**
 * description
 *
 * @author Alexander Isai on 20.08.2024.
 */
@Data
public class CustomerResponse {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String phoneNumber;
}
