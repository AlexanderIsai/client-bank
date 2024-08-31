package ua.danit.clientbank.service.ir;

import ua.danit.clientbank.model.Employer;
import java.util.List;

public interface EmployerService {
    Employer save(Employer employer);
    void delete(Employer employer);
    List<Employer> findAll();
    void deleteById(long id);
    Employer getById(long id);
    List<Employer> saveAll(List<Employer> employers);
}
