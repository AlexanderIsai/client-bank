package ua.danit.clientbank.facade;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.danit.clientbank.dto.employer.EmployerRequest;
import ua.danit.clientbank.dto.employer.EmployerResponse;
import ua.danit.clientbank.model.Employer;
import ua.danit.clientbank.service.ir.EmployerService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EmployerFacade {

    private final EmployerService employerService;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployerFacade(EmployerService employerService, ModelMapper modelMapper) {
        this.employerService = employerService;
        this.modelMapper = modelMapper;
    }

    public EmployerResponse createEmployer(EmployerRequest request) {
        Employer employer = modelMapper.map(request, Employer.class);
        employer = employerService.save(employer);
        return modelMapper.map(employer, EmployerResponse.class);
    }

    public EmployerResponse updateEmployer(Long id, EmployerRequest request) {
        Employer employer = employerService.getById(id);
        if (employer != null) {
            modelMapper.map(request, employer);
            Employer updatedEmployer = employerService.save(employer);
            return modelMapper.map(updatedEmployer, EmployerResponse.class);
        }
        return null;
    }

    public EmployerResponse getEmployerById(Long id) {
        Employer employer = employerService.getById(id);
        if (employer != null) {
            return modelMapper.map(employer, EmployerResponse.class);
        }
        return null;
    }

    public void deleteEmployer(Long id) {
        employerService.deleteById(id);
    }

    public List<EmployerResponse> getAllEmployers() {
        List<Employer> employers = employerService.findAll();
        return employers.stream()
                .map(employer -> modelMapper.map(employer, EmployerResponse.class))
                .collect(Collectors.toList());
    }
}
