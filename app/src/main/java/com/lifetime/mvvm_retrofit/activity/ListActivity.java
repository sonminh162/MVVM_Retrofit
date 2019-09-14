package com.lifetime.mvvm_retrofit.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.lifetime.mvvm_retrofit.viewmodels.EmployeeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    ArrayList<Employee> employeesArrayList = new ArrayList<>();
    ListAdapter listAdapter;
    RecyclerView recyclerView;
    EmployeeViewModel employeeViewModel;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        recyclerView = findViewById(R.id.recycler_list);

        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);
        employeeViewModel.init();

        employeeViewModel.getEmployeeRepository().observe(this, new Observer<List<Employee>>() {
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



        setupRecyclerView(employeesArrayList);

        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListActivity.this, RegisterActivity.class));
            }
        });

        findViewById(R.id.floating_button_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                employeeViewModel.getDataAllEmployee();

            }
        });
    }

    private void setupRecyclerView(ArrayList<Employee> list) {
        if (listAdapter == null) {
            listAdapter = new ListAdapter(employeesArrayList, ListActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(listAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
            listAdapter.notifyDataSetChanged();
//            listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
//                @Override
//                public void OnItemClick(Employee employee) {
////                    MutableLiveData<Employee> testUnit = service.getEmployeeById(employee.getId());
////                    Employee testUnit2 = testUnit.getValue();
////                    int testUnit3 = testUnit2.getId();
////                    int getId = service.getEmployeeById(employee.getId()).getValue().getId();
//                    Intent intent = new Intent(ListActivity.this, DetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("id", employee.getId());
//                    bundle.putString("name",employee.getName());
//                    bundle.putString("salary",employee.getSalary());
//                    bundle.putString("age",employee.getAge());
//                    intent.putExtra("package", bundle);
//                    startActivity(intent);
//                }
//            });
        } else {
            listAdapter = new ListAdapter(list, ListActivity.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(listAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
            listAdapter.notifyDataSetChanged();
//            listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
//                @Override
//                public void OnItemClick(Employee employee) {
////                    MutableLiveData<Employee> testUnit = service.getEmployeeById(employee.getId());
////                    Employee testUnit2 = testUnit.getValue();
////                    int testUnit3 = testUnit2.getId();
////                    int getId = service.getEmployeeById(employee.getId()).getValue().getId();
//                    Intent intent = new Intent(ListActivity.this, DetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("id", employee.getId());
//                    intent.putExtra("package", bundle);
//                    startActivity(intent);
//                }
//            });
        }
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
        employeeViewModel.getDataAllEmployee();
        Log.d("ABCASDF","done");
    }

}
