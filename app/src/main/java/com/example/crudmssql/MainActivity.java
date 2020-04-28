package com.example.crudmssql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.crudmssql.adapters.EmployeeAdapter;
import com.example.crudmssql.model.Employee;
import com.example.crudmssql.util.ConnectionHelper;

public class MainActivity extends AppCompatActivity {

    ConnectionHelper connection;

    Button btnAdd;
    RecyclerView recyclerViewEmployees;

    EmployeeAdapter employeeAdapter;
    List<Employee> listEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = new ConnectionHelper();

        btnAdd = (Button) findViewById(R.id.btnadd);
        recyclerViewEmployees = (RecyclerView) findViewById(R.id.recyclerViewEmployees);

        listEmployees = new ArrayList<>();
        employeeAdapter = new EmployeeAdapter(MainActivity.this, listEmployees);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewEmployees.setLayoutManager(layoutManager);
        recyclerViewEmployees.setItemAnimator(new DefaultItemAnimator());
        recyclerViewEmployees.addItemDecoration(new DividerItemDecoration(MainActivity.this, LinearLayoutManager.VERTICAL));
        recyclerViewEmployees.setAdapter(employeeAdapter);

        listEmployees();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });
    }

    public void listEmployees() {
        Connection conn = connection.Conn();

        if(conn == null) {
            Toast.makeText(MainActivity.this, "Error connecting", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            String QUERY = "SELECT * FROM employee";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);

            while(resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setPhone(resultSet.getString("phone"));
                employee.setAge(resultSet.getInt("age"));

                listEmployees.add(employee);
            }

            employeeAdapter.notifyDataSetChanged();

        } catch (SQLException erro) {
            Toast.makeText(MainActivity.this, "An error has occurred: " + erro, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listEmployees.clear();
        listEmployees();
    }
}
