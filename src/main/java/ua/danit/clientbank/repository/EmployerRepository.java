package ua.danit.clientbank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.danit.clientbank.model.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {
}