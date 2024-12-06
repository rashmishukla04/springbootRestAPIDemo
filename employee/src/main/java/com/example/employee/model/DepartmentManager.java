package com.example.employee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "DEPT_MANAGER")
public class DepartmentManager {
    private int emp_no;
    @Id
    private String dept_no;
    private Date from_date;
    private Date to_date;
}
