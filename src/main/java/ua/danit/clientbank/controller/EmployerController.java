package ua.danit.clientbank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.danit.clientbank.model.Employer;
import ua.danit.clientbank.service.ir.EmployerService;

import java.util.List;

/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employers")
@CrossOrigin(origins = "*")
public class EmployerController {
    private final EmployerService employerService;

    @PostMapping
    public ResponseEntity<Employer> createEmployer(@RequestBody Employer employer) {
        Employer savedEmployer = employerService.save(employer);
        return ResponseEntity.ok(savedEmployer);
    }

    @PutMapping("/{id}")
    public Employer updateEmployer(@PathVariable Long id, @RequestBody Employer employer) {
        employer.setId(id);
        return employerService.save(employer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        if (employerService.deleteById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employer> getEmployerById(@PathVariable Long id) {
        Employer employer = employerService.getById(id);
        if (employer != null) {
            return ResponseEntity.ok(employer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Employer>> getAllEmployers() {
        List<Employer> employers = employerService.findAll();
        return ResponseEntity.ok(employers);
    }
}