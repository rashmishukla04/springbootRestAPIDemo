package com.example.employee.controller;


import com.example.employee.dto.EmployeeRequest;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/v1")
public class EmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("/addEmployee")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody EmployeeRequest emp){
        logger.info("Received Employee Request: {}", emp);
        return new ResponseEntity<>(employeeService.saveEmployee(emp) , HttpStatus.CREATED);
    }

    @PostMapping("/addEmployees")
    public ResponseEntity<List<Employee>> addEmployees(@Valid @RequestBody List<EmployeeRequest> emp){
        return new ResponseEntity<>(employeeService.saveEmployees(emp) , HttpStatus.CREATED);
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getEmployess(){
        return ResponseEntity.ok(employeeService.getEmployess());
    }

    @GetMapping("/employeeById/{id}")    //404 it is mandatory to pass od
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id) throws EmployeeNotFoundException {
       return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/employeeByName/{name}")
    public ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable String name){
        return ResponseEntity.ok(employeeService.getEmployeeByName(name));
    }

    @DeleteMapping("/delete/{id}")
    public String deleteEmployeeById(@PathVariable long id){
        return employeeService.deleteEmployeeById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<Employee>  updateEmployee(@RequestBody @Valid EmployeeRequest emp) throws EmployeeNotFoundException {
        return ResponseEntity.ok(employeeService.updateEmployee(emp));
    }

    @GetMapping("/employeeSortBy/{field}")
    public ResponseEntity<List<Employee>> getEmployeeSortingBy(@PathVariable String field){
        return ResponseEntity.ok(employeeService.getEmployeeWithSorting(field));
    }
    @GetMapping("/employeePagination/{offset}/{pageSize}")
    public ResponseEntity<Page<Employee>> getEmployeePagination(@PathVariable int offset, @PathVariable int pageSize ){
        return ResponseEntity.ok(employeeService.getEmployeeWithPagination(offset,pageSize));
    }
}
