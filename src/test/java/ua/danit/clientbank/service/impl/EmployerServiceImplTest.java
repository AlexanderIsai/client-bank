package ua.danit.clientbank.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.danit.clientbank.model.Employer;
import ua.danit.clientbank.repository.EmployerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EmployerServiceImplTest {

    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerServiceImpl employerService;

    @Test
    void testSaveEmployer() {
        Employer employer = new Employer();
        when(employerRepository.save(any(Employer.class))).thenReturn(employer);
        Employer savedEmployer = employerService.save(employer);
        assertNotNull(savedEmployer);
        verify(employerRepository).save(employer);
    }

    @Test
    void testDeleteEmployer() {
        Employer employer = new Employer();
        employer.setId(1L);
        doNothing().when(employerRepository).delete(employer);
        employerService.delete(employer);
        verify(employerRepository).delete(employer);
    }

    @Test
    void testFindAllEmployers() {
        Employer employer1 = new Employer();
        Employer employer2 = new Employer();
        List<Employer> employers = Arrays.asList(employer1, employer2);
        when(employerRepository.findAll()).thenReturn(employers);

        List<Employer> foundEmployers = employerService.findAll();
        assertNotNull(foundEmployers);
        assertEquals(2, foundEmployers.size());
        verify(employerRepository).findAll();
    }

    @Test
    void testDeleteById() {
        doNothing().when(employerRepository).deleteById(1L);
        employerService.deleteById(1L);
        verify(employerRepository).deleteById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(employerRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> employerService.getById(1L));
        assertEquals("Employer not found", exception.getMessage());
    }

    @Test
    void testSaveAllEmployers() {
        List<Employer> employers = Arrays.asList(new Employer(), new Employer());
        when(employerRepository.saveAll(employers)).thenReturn(employers);
        List<Employer> savedEmployers = employerService.saveAll(employers);
        verify(employerRepository).saveAll(employers);
        assertEquals(employers, savedEmployers, "The saved employers should be the same as those returned.");
    }

}
