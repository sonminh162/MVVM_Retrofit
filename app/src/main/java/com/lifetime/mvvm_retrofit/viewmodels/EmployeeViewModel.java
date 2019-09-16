package com.lifetime.mvvm_retrofit.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lifetime.mvvm_retrofit.model.Employee;
import com.lifetime.mvvm_retrofit.model.EmployeeResponse;
import com.lifetime.mvvm_retrofit.repository.EmployeeRepository;

import java.util.List;

public class EmployeeViewModel extends ViewModel {
    private MutableLiveData<List<Employee>> mutableLiveData;
    private EmployeeRepository employeeRepository;

    public void init(){
        if(mutableLiveData != null) {
            return;
        }
        employeeRepository = EmployeeRepository.getInstance();
        mutableLiveData = employeeRepository.getAllEmployee();
    }

    public LiveData<List<Employee>> getEmployeeRepository(){
        return mutableLiveData;
    }

    public void createNewEmployee(EmployeeResponse employee){
        EmployeeRepository.getInstance().createEmployee(employee);
    }






















    public LiveData<List<Employee>> getEmployeeRepositoryTest(){
        return EmployeeRepository.getInstance().getAllEmployee();
    }

    public LiveData<Employee> getEmployeeById(int id){
        return employeeRepository.getEmployeeById(id);
    }

    public void updateEmployee(int id,EmployeeResponse employeeResponse){
        employeeRepository.updateEmployee(id,employeeResponse);
    }

    public void deleteEmployee(Employee employee){
        employeeRepository.deleteEmployee(employee.getId());
    }

    public void getDataAllEmployee(){
        EmployeeRepository.getInstance().getAllEmployee();
    }

    public void setValueDefaultGetEmployeeById(){
        EmployeeRepository.getInstance().getEmployeeById(10);
    }

}
