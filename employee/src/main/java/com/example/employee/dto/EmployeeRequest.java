package com.example.employee.dto;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class EmployeeRequest {
    @NotNull(message = "Emp_no should not be null")
    @Positive
    private Long emp_no;
    @NotNull(message = "First name should not be null")
    private String firstName;
    private String last_name;

    @Past(message = "Birth date must be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birth_date;

    //@FutureOrPresent(message = "Hire date cannot be in the past")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hire_date;
    @Pattern(regexp = "M|F|O" , message = "Gender must be Male, Female, or Other")
    private String gender;
}
