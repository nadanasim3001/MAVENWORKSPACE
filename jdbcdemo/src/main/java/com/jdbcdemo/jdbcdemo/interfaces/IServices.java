package com.jdbcdemo.jdbcdemo.interfaces;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jdbcdemo.jdbcdemo.dto.BaseOutput;
import com.jdbcdemo.jdbcdemo.dto.EmployeeDetailsResponse;
import com.jdbcdemo.jdbcdemo.dto.InsertEmployeeList;
import com.jdbcdemo.jdbcdemo.dto.InsertEmployeeResponse;
import com.jdbcdemo.jdbcdemo.dto.JobDetails;

@Component
public interface IServices {
	
	public EmployeeDetailsResponse getEmployeeLists(String deptId);
	public InsertEmployeeResponse insertNewEmployee(InsertEmployeeList request); 
	public EmployeeDetailsResponse getEmployeeDetails(String empId);
	public BaseOutput removeEmployee(String empId);
	public Double sumOfDeptWiseSalary(String deptId);
	public Double wiseCalcuation(String wise, String type,String id);
	public List<JobDetails> jobDetails(String minimumSalary);
	public List<Double> empIdList(String salary);


}
