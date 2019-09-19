package com.lifetime.mvvm_retrofit.viewmodels;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lifetime.mvvm_retrofit.activity.DetailActivity;
import com.lifetime.mvvm_retrofit.activity.RegisterActivity;
import com.lifetime.mvvm_retrofit.model.Employee;
import com.lifetime.mvvm_retrofit.model.EmployeeResponse;
import com.lifetime.mvvm_retrofit.networking.EmployeeAPI;
import com.lifetime.mvvm_retrofit.networking.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeViewModel extends ViewModel {
    private EmployeeAPI employeeAPI;

    public MutableLiveData<List<Employee>> employeesData = new MutableLiveData<>();

    public void init(){
            employeeAPI = RetrofitService.createService(EmployeeAPI.class);
    }

    public void getAllEmployee(){
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
    }

    public void createNewEmployee(EmployeeResponse employee, RegisterActivity context){
        employeeAPI.createEmployee(employee).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                context.finish();
            }

            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
            }
        });
    }

    public LiveData<Employee> getEmployeeById(int id){
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

    public void updateEmployee(int id,EmployeeResponse employeeResponse,DetailActivity context){
        employeeAPI.updateEmployee(id,employeeResponse).enqueue(new Callback<EmployeeResponse>() {
            @Override
            public void onResponse(Call<EmployeeResponse> call, Response<EmployeeResponse> response) {
                context.finish();
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<EmployeeResponse> call, Throwable t) {
                Log.d("BBB",t.getMessage());
            }
        });
    }

    public void deleteEmployee(Employee employee,DetailActivity context){
        employeeAPI.deleteEmployee(employee.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                context.finish();
                Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}
