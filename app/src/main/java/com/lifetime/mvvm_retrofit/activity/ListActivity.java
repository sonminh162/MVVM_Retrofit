package com.lifetime.mvvm_retrofit.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lifetime.mvvm_retrofit.R;
import com.lifetime.mvvm_retrofit.adapter.ListAdapter;
import com.lifetime.mvvm_retrofit.model.Employee;
import com.lifetime.mvvm_retrofit.model.EmployeeResponse;
import com.lifetime.mvvm_retrofit.viewmodels.EmployeeViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.lifetime.mvvm_retrofit.constant.Constant.ADDRESS;
import static com.lifetime.mvvm_retrofit.constant.Constant.NAME;
import static com.lifetime.mvvm_retrofit.constant.Constant.SUBJECT;

public class ListActivity extends AppCompatActivity {
    public static final int CODE = 110;
    ArrayList<Employee> employeesArrayList = new ArrayList<>();
    ListAdapter listAdapter;
    RecyclerView recyclerView;
    EmployeeViewModel employeeViewModel;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_list);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);

        employeeViewModel.getEmployeeRepositoryTest().observe(this, new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                employeesArrayList.addAll(employees);
                setupRecyclerView(employeesArrayList);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 1000);
            }
        });

        findViewById(R.id.floating_button_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();

                employeeViewModel.getDataAllEmployee();
                employeeViewModel.getEmployeeRepositoryTest();
            }
        });
        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListActivity.this, RegisterActivity.class));
            }
        });

    }

    private void setupRecyclerView(ArrayList<Employee> list) {

            listAdapter = new ListAdapter(list, ListActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                listAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.show();
        employeeViewModel.getDataAllEmployee();
        employeeViewModel.getEmployeeRepositoryTest();

    }


}
