package ua.danit.clientbank.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.danit.clientbank.dto.employer.EmployerRequest;
import ua.danit.clientbank.dto.employer.EmployerResponse;
import ua.danit.clientbank.facade.EmployerFacade;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employers")
@CrossOrigin(origins = "*")
public class EmployerController {
    private final EmployerFacade employerFacade;

    @PostMapping
    public ResponseEntity<EmployerResponse> createEmployer(@RequestBody EmployerRequest employerRequest) {
        log.info("Creating employer with request data: {}", employerRequest);
        EmployerResponse savedEmployer = employerFacade.createEmployer(employerRequest);
        log.info("Employer created successfully with ID: {}", savedEmployer.getId());
        return ResponseEntity.ok(savedEmployer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployerResponse> updateEmployer(@PathVariable Long id, @RequestBody EmployerRequest employerRequest) {
        log.info("Updating employer with ID: {} with request data: {}", id, employerRequest);
        EmployerResponse updatedEmployer = employerFacade.updateEmployer(id, employerRequest);
        if (updatedEmployer != null) {
            log.info("Employer updated successfully with ID: {}", updatedEmployer.getId());
            return ResponseEntity.ok(updatedEmployer);
        } else {
            log.info("No employer found with ID: {}, unable to update", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        log.info("Deleting employer with ID: {}", id);
        employerFacade.deleteEmployer(id);
        log.info("Employer deleted successfully with ID: {}", id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerResponse> getEmployerById(@PathVariable Long id) {
        log.info("Fetching employer details for ID: {}", id);
        EmployerResponse employerResponse = employerFacade.getEmployerById(id);
        if (employerResponse != null) {
            log.info("Employer details retrieved successfully for ID: {}", id);
            return ResponseEntity.ok(employerResponse);
        } else {
            log.info("No employer found with ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EmployerResponse>> getAllEmployers() {
        log.info("Fetching all employers");
        List<EmployerResponse> employers = employerFacade.getAllEmployers();
        log.info("Number of employers retrieved: {}", employers.size());
        return ResponseEntity.ok(employers);
    }
}
