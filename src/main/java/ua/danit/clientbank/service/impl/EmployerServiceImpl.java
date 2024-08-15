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

    public Employer save(Employer employer) {
        return employerRepository.save(employer);
    }

    public boolean delete(Employer employer) {
        if (employerRepository.existsById(employer.getId())) {
            employerRepository.delete(employer);
            return true;
        }
        return false;
    }

    public void deleteAll(List<Employer> employers) {
        employerRepository.deleteAll(employers);
    }

    public List<Employer> findAll() {
        return employerRepository.findAll();
    }

    public boolean deleteById(long id) {
        if (employerRepository.existsById(id)) {
            employerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Employer getById(long id) {
        return employerRepository.findById(id).orElse(null);
    }

    public void saveAll(List<Employer> employers) {
        employerRepository.saveAll(employers);
    }
}