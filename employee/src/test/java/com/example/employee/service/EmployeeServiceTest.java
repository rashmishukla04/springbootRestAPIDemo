package com.example.employee.service;

import com.example.employee.dto.EmployeeRequest;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee existingEmployee;
    private EmployeeRequest employeeRequest;

    @Before
    public void setUp() {
        // Setup mock employee data
        existingEmployee =  Employee.build(9L, "Jane", "Doe",LocalDate.of(1992,03,04),LocalDate.of(1997,03,04),"M");

        // Setup the employee request data for updating
        employeeRequest =  EmployeeRequest.build(9L, "Jane", "Doe",LocalDate.of(1992,03,04),LocalDate.of(1997,03,04),"M");
    }

    @Test
    public void testUpdateEmployee_Success() throws EmployeeNotFoundException {
        // Mocking the repository
        when(employeeRepository.findById(9L)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        // Call the updateEmployee method
        Employee updatedEmployee = employeeService.updateEmployee(employeeRequest);

        // Validate the update operation
        assertNotNull(updatedEmployee);
        assertEquals("Jane", updatedEmployee.getFirstName());
        assertEquals("Doe", updatedEmployee.getLast_name());
        assertEquals("M", updatedEmployee.getGender());
        assertEquals(LocalDate.of(1992, 3, 4), updatedEmployee.getBirth_date());
        assertEquals(LocalDate.of(1997, 3, 4), updatedEmployee.getHire_date());

        // Verify that save method was called once
        //verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void testUpdateEmployee_EmployeeNotFound() throws EmployeeNotFoundException {
        // Mock repository to return empty for non-existent employee
        //when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        // Call the service method (should throw EmployeeNotFoundException)
        employeeService.updateEmployee(employeeRequest);
    }
}


