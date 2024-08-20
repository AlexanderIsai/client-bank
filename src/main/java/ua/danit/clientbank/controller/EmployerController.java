package ua.danit.clientbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.danit.clientbank.dto.employer.EmployerRequest;
import ua.danit.clientbank.dto.employer.EmployerResponse;
import ua.danit.clientbank.facade.EmployerFacade;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employers")
@CrossOrigin(origins = "*")
public class EmployerController {
    private final EmployerFacade employerFacade;

    @PostMapping
    public ResponseEntity<EmployerResponse> createEmployer(@RequestBody EmployerRequest employerRequest) {
        EmployerResponse savedEmployer = employerFacade.createEmployer(employerRequest);
        return ResponseEntity.ok(savedEmployer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployerResponse> updateEmployer(@PathVariable Long id, @RequestBody EmployerRequest employerRequest) {
        EmployerResponse updatedEmployer = employerFacade.updateEmployer(id, employerRequest);
        return updatedEmployer != null ? ResponseEntity.ok(updatedEmployer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        employerFacade.deleteEmployer(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployerResponse> getEmployerById(@PathVariable Long id) {
        EmployerResponse employerResponse = employerFacade.getEmployerById(id);
        return employerResponse != null ? ResponseEntity.ok(employerResponse) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<EmployerResponse>> getAllEmployers() {
        List<EmployerResponse> employers = employerFacade.getAllEmployers();
        return ResponseEntity.ok(employers);
    }
}
