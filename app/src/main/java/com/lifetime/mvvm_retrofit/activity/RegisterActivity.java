package com.lifetime.mvvm_retrofit.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.lifetime.mvvm_retrofit.R;
import com.lifetime.mvvm_retrofit.model.EmployeeResponse;
import com.lifetime.mvvm_retrofit.viewmodels.EmployeeViewModel;

public class RegisterActivity extends AppCompatActivity {
    TextView registerName,registerSalary, registerAge;
    Button buttonSave;

    EmployeeViewModel employeeViewModel;

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

        employeeViewModel.createNewEmployee(new EmployeeResponse(sName,sSalary,sAge),this);

    }
}
