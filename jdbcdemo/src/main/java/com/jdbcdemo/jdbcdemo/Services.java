package com.jdbcdemo.jdbcdemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import com.jdbcdemo.jdbcdemo.coreCall.BaseServicesCall;
import com.jdbcdemo.jdbcdemo.coreCall.CoreServiceCall;
import com.jdbcdemo.jdbcdemo.dto.BaseOutput;
import com.jdbcdemo.jdbcdemo.dto.BulkEmployeesResponse;
import com.jdbcdemo.jdbcdemo.dto.EmployeeDetailsResponse;
import com.jdbcdemo.jdbcdemo.dto.InsertEmployeeList;
import com.jdbcdemo.jdbcdemo.dto.InsertEmployeeResponse;
import com.jdbcdemo.jdbcdemo.dto.JobDetails;
import com.jdbcdemo.jdbcdemo.interfaces.CreateEmployeeInBulkIF;
import com.jdbcdemo.jdbcdemo.interfaces.IFN01;
import com.jdbcdemo.jdbcdemo.interfaces.IServices;

@Component
public class Services implements IServices {
	@Autowired
	CoreServiceCall bs;

	public EmployeeDetailsResponse getEmployeeLists(String empId) {

		EmployeeDetailsResponse response = new EmployeeDetailsResponse();
		CoreServiceCall bs = new CoreServiceCall();
		response = bs.getEmployeeLists(empId);

		return response;
	}

	@Override
	public InsertEmployeeResponse insertNewEmployee(InsertEmployeeList request) {
		// InsertEmployeeList employeeList = new ArrayList<>();
		InsertEmployeeResponse response = new InsertEmployeeResponse();
		// CoreServiceCall bs = new CoreServiceCall();
		String empId = "";
		response = bs.insertNewEmployee(request);

		return response;

	}

	@Override
	public EmployeeDetailsResponse getEmployeeDetails(String empId) {
		// TODO Auto-generated method stub
		EmployeeDetailsResponse response = new EmployeeDetailsResponse();

		response = bs.getEmployeeDetails(empId);

		float sal = response.getSalary();
		List<Float> salaryList = new ArrayList<>();
		salaryList.add(sal);

		System.out.println("printting to check lambda");

		if (checkEvenSalary(salaryList))

		// salaryList.stream().filter(i -> i % 2 == 1).forEach(System.out::println);
		// System.out.println();
		{
			return response;
		}

		else {
			response.setErrorDesc("Odd Salary");
			return response;
		}
	}

	private static boolean checkEvenSalary(List<Float> salary) {
		return salary.stream().allMatch(i -> i % 2 == 0);

	}

	@Override
	public BaseOutput removeEmployee(String empId) {
		BaseOutput response = new BaseOutput();
		response = bs.removeEmployee(empId);
		return response;
	}

	public BaseOutput freezeEmployee(String empId) {

		IFN01 fo = (String emId) -> {
			CoreServiceCall bss = new CoreServiceCall();
			BaseOutput response = new BaseOutput();
			response = bss.freezeEmployee(empId);

			return response;

		};

		return fo.freezeEmployee(empId);

	}

	public BulkEmployeesResponse createBulkEmployee(List<InsertEmployeeList> employeeList) {
		BulkEmployeesResponse response = new BulkEmployeesResponse();

		List<String> emailList = new ArrayList<>();

		for (InsertEmployeeList eList : employeeList) {
			emailList.add(eList.getEmail());
		}
		if (validateEmailId(emailList)) {
			CreateEmployeeInBulkIF cf = (List<InsertEmployeeList> empList) -> {

				CoreServiceCall bst = new CoreServiceCall();
				InsertEmployeeResponse empResponse = new InsertEmployeeResponse();
				List<InsertEmployeeResponse> empList5 = new ArrayList<>();
				for (InsertEmployeeList empList3 : empList.stream().toList()) {

					empResponse = bst.insertNewEmployee(empList3);

					empList5.add(empResponse);
					response.setNewEmployeesResponse(empList5);

				}

				return response;
			};

			return cf.createBulkEmployees(employeeList);

		}
		response.setErrorCode(1001);
		response.setErrorDesc("email is not from well knowns TLDs");
		return response;
	}

	private boolean validateEmailId(List<String> emailList) {
		// TODO Auto-generated method stub
		return emailList.stream()
				.allMatch(mail -> mail.contains("@gmail.") || mail.contains("@yahoo.") || mail.contains("@outlook."));
	}

	public Double sumOfDeptWiseSalary(String deptId) {

		CoreServiceCall bst = new CoreServiceCall();
		List<Double> sumOfSalary = new ArrayList<>();

		sumOfSalary = bst.sumOfSalary(deptId, "department_id");

		return sumOfSalary.stream().reduce(0D, (x, y) -> x + y);

	}

	public Double wiseCalcuation(String wise, String type, String id) {

		CoreServiceCall bst = new CoreServiceCall();
		List<Double> salaryList = new ArrayList<>();

		salaryList = bst.sumOfSalary(id, wise);

		Double response = 0D;

		if (type.equals("sum"))
			response = salaryList.stream().reduce(0D, (x, y) -> x + y);
		if (type.equals("average")) {
			Long size = salaryList.stream().count();

			response = (double) Math.round(salaryList.stream().reduce(0D, (x, y) -> (x + y)) / size);

		}
		return response;

	}

	public List<JobDetails> jobDetails(String minSalary) {

		List<JobDetails> jobList = new ArrayList<>();
		CoreServiceCall bst = new CoreServiceCall();
		jobList = bst.jobDetails(minSalary);
		List<JobDetails> jobList2 = new ArrayList<>();

		return jobList;

	}

	public List<Double> empIdList(String salary) {
		List<Double> empIdList = new ArrayList<>();
		CoreServiceCall bst = new CoreServiceCall();
		List<Double> response = new ArrayList<>();
		empIdList = bst.getEmpIdAccToSalary(salary);
		response = empIdList.stream().sorted().collect(Collectors.toList());

		return response;
	}
}
