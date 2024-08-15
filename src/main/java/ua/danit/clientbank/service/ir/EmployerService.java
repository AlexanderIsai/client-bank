package ua.danit.clientbank.service.ir;

import ua.danit.clientbank.model.Employer;

import java.util.List;

/**
 * description
 *
 * @author Alexander Isai on 16.07.2024.
 */
public interface EmployerService {
    Employer save(Employer employer);
    boolean delete(Employer employer);
    void deleteAll(List<Employer> employers);
    List<Employer> findAll();
    boolean deleteById(long id);
    Employer getById(long id);
    void saveAll(List<Employer> employers);
}
