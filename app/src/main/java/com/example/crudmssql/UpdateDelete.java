package com.example.crudmssql;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.crudmssql.model.Employee;
import com.example.crudmssql.util.ConnectionHelper;

public class UpdateDelete extends AppCompatActivity {

    EditText editId, editName, editPhoneNum, editAge;
    Button btnSave, btnDelete;

    ConnectionHelper Connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Connection = new ConnectionHelper();

        editId = (EditText) findViewById(R.id.editId);
        editName = (EditText) findViewById(R.id.editNome);
        editPhoneNum = (EditText) findViewById(R.id.editTelefone);
        editAge = (EditText) findViewById(R.id.editIdade);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {
            int id = (int) bundle.get("ID");
            recoverEmployee(id);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                java.sql.Connection conn = Connection.Conn();
                String resultado = "";

                if(conn == null) {
                    Toast.makeText(UpdateDelete.this, "Error connecting", Toast.LENGTH_LONG).show();
                    return;
                }

                String id = editId.getText().toString();
                // Update an existing record
                Employee employee = new Employee();
                employee.setId(Integer.parseInt(editId.getText().toString()));
                employee.setName(editName.getText().toString());
                employee.setPhone(editPhoneNum.getText().toString());
                employee.setAge(Integer.parseInt(editAge.getText().toString()));

                resultado = updateEmployee(conn, employee);

                Toast.makeText(UpdateDelete.this, resultado, Toast.LENGTH_LONG).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Connection conn = Connection.Conn();

                if(conn == null) {
                    Toast.makeText(UpdateDelete.this, "Error connecting", Toast.LENGTH_LONG).show();
                    return;
                }

                String id = editId.getText().toString();

                if(!id.isEmpty())  {
                    deleteEmployee(conn, id);
                }
            }
        });
    }

    public void recoverEmployee(int id) {

        Connection conn = Connection.Conn();
        String resultado = "";

        if(conn == null) {
            Toast.makeText(UpdateDelete.this, "Error connecting", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            String QUERY = "SELECT * FROM employee WHERE id = " + id;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);

            while(resultSet.next()) {
                editId.setText(String.valueOf(id));
                editName.setText(resultSet.getString("name"));
                editPhoneNum.setText(resultSet.getString("phone"));
                editAge.setText(String.valueOf(resultSet.getInt("age")));
            }

        } catch (SQLException erro) {
            Toast.makeText(UpdateDelete.this, "An error has occurred: " + erro, Toast.LENGTH_LONG).show();
        }

    }

    public String insertEmployee(Connection conn, Employee funcionario) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO employee (name, phone, age, id) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, funcionario.getName());
            preparedStatement.setString(2, funcionario.getPhone());
            preparedStatement.setInt(3, funcionario.getAge());
            preparedStatement.setInt(4, funcionario.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return "Add successfully";
        } catch (SQLException erro) {
            return "Error saving: " + erro.getMessage();
        }
    }

    public String updateEmployee(Connection conn, Employee funcionario) {
        try {
            String QUERY_UPDATE = "UPDATE employee SET name = ?, phone = ?, age = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_UPDATE);
            preparedStatement.setString(1, funcionario.getName());
            preparedStatement.setString(2, funcionario.getPhone());
            preparedStatement.setInt(3, funcionario.getId());
            preparedStatement.setInt(4, funcionario.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return "Updated successfully";
        } catch (SQLException erro) {
            return "Error updating:" + erro.getMessage();
        }
    }

    public void deleteEmployee(Connection conn, String id) {
        try {
            String QUERY_DELETE = "DELETE FROM employee WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_DELETE);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            Toast.makeText(UpdateDelete.this, "Successfully deleted ", Toast.LENGTH_LONG).show();
            finish();

        } catch (SQLException erro) {
            Toast.makeText(UpdateDelete.this, "Error saving: " + erro.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}