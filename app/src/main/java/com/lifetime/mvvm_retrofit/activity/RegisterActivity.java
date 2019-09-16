package com.lifetime.mvvm_retrofit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.lifetime.mvvm_retrofit.R;
import com.lifetime.mvvm_retrofit.model.EmployeeResponse;
import com.lifetime.mvvm_retrofit.repository.EmployeeRepository;
import com.lifetime.mvvm_retrofit.viewmodels.EmployeeViewModel;

import static com.lifetime.mvvm_retrofit.constant.Constant.ADDRESS;
import static com.lifetime.mvvm_retrofit.constant.Constant.NAME;
import static com.lifetime.mvvm_retrofit.constant.Constant.SUBJECT;

public class RegisterActivity extends AppCompatActivity {
    TextView registerName,registerSalary, registerAge;
    Button buttonSave;

    EmployeeViewModel employeeViewModel;
    EmployeeRepository service;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.editTextName);
        registerAge = findViewById(R.id.editTextAge);
        registerSalary = findViewById(R.id.editTextSalary);
        buttonSave = findViewById(R.id.button_save);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEmployee();
            }
        });
    }
    private void saveEmployee(){
        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);
        employeeViewModel.init();

        final String sName = registerName.getText().toString().trim();
        final String sSalary = registerSalary.getText().toString().trim();
        final String sAge = registerAge.getText().toString().trim();

        if(sName.isEmpty()){
            registerName.setError("name required");
            registerName.requestFocus();
            return;
        }

        if(sAge.isEmpty()){
            registerAge.setError("age required");
            registerAge.requestFocus();
        }

        if(sSalary.isEmpty()){
            registerSalary.setError("salary required");
            registerSalary.requestFocus();
            return;
        }

//        Intent result = new Intent();
//        result.putExtra(NAME,sName);
//        result.putExtra(ADDRESS,sSalary);
//        result.putExtra(SUBJECT,sAge);
//        setResult(RESULT_OK,result);
        employeeViewModel.createNewEmployee(new EmployeeResponse(sName,sSalary,sAge));
        Toast.makeText(RegisterActivity.this, "Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
