package com.lifetime.mvvm_retrofit.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.lifetime.mvvm_retrofit.model.Employee;
import com.lifetime.mvvm_retrofit.model.EmployeeResponse;
import com.lifetime.mvvm_retrofit.networking.EmployeeAPI;
import com.lifetime.mvvm_retrofit.networking.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepository {

    private static EmployeeRepository employeeRepository;

    public static EmployeeRepository getInstance(){
        if(employeeRepository == null) {
            employeeRepository = new EmployeeRepository();
        }
        return employeeRepository;
    }

    private EmployeeAPI employeeAPI;

    public EmployeeRepository(){
        employeeAPI = RetrofitService.createService(EmployeeAPI.class);
    }

    public MutableLiveData<List<Employee>> getAllEmployee(){
        final MutableLiveData<List<Employee>> employeesData = new MutableLiveData<>();
        employeeAPI.getAllEmployees().enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if(response.isSuccessful()){
                    employeesData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                employeesData.setValue(null);
            }
        });
        return employeesData;
    }



    public MutableLiveData<Employee> getEmployeeById(int id){
        final MutableLiveData<Employee> employee = new MutableLiveData<>();
        employeeAPI.getEmployeeById(id).enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                if(response.isSuccessful()){
                    employee.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                employee.setValue(null);
            }
        });
        return employee;
    }

    public void updateEmployee(int id,EmployeeResponse employeeResponseReceived){
        employeeAPI.updateEmployee(id,employeeResponseReceived).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {

            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                Log.d("BBB",t.getMessage());
            }
        });
    }

    public void deleteEmployee(int id){
        employeeAPI.deleteEmployee(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void createEmployee(EmployeeResponse employeeResponseReceived){
        employeeAPI.createEmployee(employeeResponseReceived).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
            }
        });
    }


}
