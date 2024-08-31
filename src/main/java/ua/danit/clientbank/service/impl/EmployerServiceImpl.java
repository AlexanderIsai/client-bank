package ua.danit.clientbank.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.danit.clientbank.model.Employer;
import ua.danit.clientbank.repository.EmployerRepository;
import ua.danit.clientbank.service.ir.EmployerService;

import java.util.List;

@Service
@Transactional
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;

    @Autowired
    public EmployerServiceImpl(EmployerRepository employerRepository) {
        this.employerRepository = employerRepository;
    }

    @Override
    public Employer save(Employer employer) {
        return employerRepository.save(employer);
    }

    @Override
    public void delete(Employer employer) {
        employerRepository.delete(employer);
    }

    @Override
    public List<Employer> findAll() {
        return employerRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        employerRepository.deleteById(id);
    }

    @Override
    public Employer getById(long id) {
        return employerRepository.findById(id).orElseThrow(() -> new RuntimeException("Employer not found"));
    }

    @Override
    public List<Employer> saveAll(List<Employer> employers) {
        employerRepository.saveAll(employers);
        return employers;
    }
}
