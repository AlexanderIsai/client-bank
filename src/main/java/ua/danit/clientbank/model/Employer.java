package ua.danit.clientbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * description
 *
 * @author Alexander Isai on 30.07.2024.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employer extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @ManyToMany(mappedBy = "employers")
    @JsonIgnore
    private List<Customer> customers = new ArrayList<>();

    public Employer(String name, String address) {
        this.name = name;
        this.address = address;
    }
}

