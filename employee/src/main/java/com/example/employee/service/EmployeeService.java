package com.example.employee.service;

import com.example.employee.dto.EmployeeRequest;
import com.example.employee.exception.EmployeeNotFoundException;
import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(EmployeeRequest empR){
        Employee emp = Employee.build(empR.getEmp_no(),empR.getFirstName(),empR.getLast_name(),
                empR.getBirth_date(),empR.getHire_date(), String.valueOf(empR.getGender()));
        return employeeRepository.save(emp);
    }
    public List<Employee> saveEmployees(List<EmployeeRequest> emplR){
        List<Employee> empls = emplR.stream().map(i-> Employee.build(i.getEmp_no(),i.getFirstName(),i.getLast_name(),
                i.getBirth_date(),i.getHire_date(), String.valueOf(i.getGender()))).collect(Collectors.toList());
        return employeeRepository.saveAll(empls);
    }
    public List<Employee> getEmployess(){
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(long id) throws EmployeeNotFoundException {
        Employee emp = employeeRepository.findById(id).orElse(null);
        if(emp != null){
            return emp;
        }else{
            throw new EmployeeNotFoundException("Employee not found with id " + id);
        }
    }
    public List<Employee> getEmployeeByName(String name){
        return employeeRepository.findByFirstName(name);
    }
    public String deleteEmployeeById(long id){
        employeeRepository.deleteById(id);
        return "Product removed!!" + id;
    }
    public Employee updateEmployee(EmployeeRequest emp) throws EmployeeNotFoundException {
        Employee existingEmp = employeeRepository.findById(emp.getEmp_no())
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id " + emp.getEmp_no() + " not found"));
        existingEmp.setFirstName(emp.getFirstName());
        existingEmp.setGender(String.valueOf(emp.getGender()));
        existingEmp.setLast_name(emp.getLast_name());
        existingEmp.setHire_date(emp.getHire_date());
        existingEmp.setBirth_date(emp.getBirth_date());
        return employeeRepository.save(existingEmp);
    }

    public List<Employee> getEmployeeWithSorting(String field){
        return employeeRepository.findAll(Sort.by(Sort.Direction.ASC ,field));
    }


    public Page<Employee> getEmployeeWithPagination(int offset , int pageSize){
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(offset, pageSize));
        return employees;
    }




}
