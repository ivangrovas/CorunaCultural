package com.example.corunacultural;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RegisterSC extends AppCompatActivity {
    private EditText editTextNombre, editTextCorreo, editTextContraseña, editTextConfirmarContraseña, editTextFechaNacimiento;
    private Button buttonRegistrar;
    private Context context = this;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sc);

        mAuth = FirebaseAuth.getInstance();

        editTextNombre = findViewById(R.id.Name);
        editTextCorreo = findViewById(R.id.Email);
        editTextContraseña = findViewById(R.id.Password);
        editTextConfirmarContraseña = findViewById(R.id.ConfrimPassword);
        editTextFechaNacimiento = findViewById(R.id.FechaNacimiento);
        buttonRegistrar = findViewById(R.id.buttonRegister);
        calendar = Calendar.getInstance();

        // Configurar el selector de fecha
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Actualizar el campo de texto con la fecha seleccionada
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);
                actualizarCampoFecha();
            }
        };

        // Manejar el clic del botón para mostrar el selector de fecha
        editTextFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarSelectorFecha();
            }
        });

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(RegisterSC.this, LoginSC.class);
                registrarUsuario();
                startActivity(signInIntent);
            }
        });
    }

    private void mostrarSelectorFecha() {
        new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void actualizarCampoFecha() {
        String formatoFecha = "dd/MM/yyyy";
        editTextFechaNacimiento.setText(android.text.format.DateFormat.format(formatoFecha, calendar.getTime()));
    }

    private void registrarUsuario(){
        String nombre = editTextNombre.getText().toString().trim();
        String email = editTextCorreo.getText().toString().trim();
        String password = editTextContraseña.getText().toString().trim();
        String birthdate = editTextFechaNacimiento.getText().toString().trim();

        if (TextUtils.isEmpty(nombre)){
            editTextNombre.setError("Ingresa tu nombre");
            return;
        }

        if(TextUtils.isEmpty(email)){
            editTextCorreo.setError("Ingresa tu correo electrónico");
            return;
        }

        if(TextUtils.isEmpty(password) || password.length() < 6){
            editTextContraseña.setError("Ingresa una contraseña válida (mínimo 6 caracteres)");
            return;
        }

        if(TextUtils.isEmpty(birthdate)){
            editTextFechaNacimiento.setError("Ingresa tu fecha de nacimiento:");
            return;
        }
    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task ->{
        if(task.isSuccessful()){
           FirebaseUser user = mAuth.getCurrentUser();
           Usuario nuevoUsuario = new Usuario(nombre, email, birthdate);
           FirebaseDatabase.getInstance()
                    .getReference("usuarios").child(user.getUid()).setValue(nuevoUsuario)
                    .addOnCompleteListener(taskDb -> {
                        if(taskDb.isSuccessful()){
                            Toast.makeText(RegisterSC.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterSC.this, LoginSC.class));
                            finish();
                        }else {
                            Toast.makeText(RegisterSC.this, "Error al guardar datos:"
                                    + taskDb.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Log.e("TagError", task.getException().getMessage());
            Toast.makeText(RegisterSC.this, "Registro fallido: " +
                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
    }

    static class Usuario {
        public String nombre, correo, birthdate;
        public Usuario(String nombre, String correo, String birthdate){
            this.nombre = nombre;
            this.correo = correo;
            this.birthdate = birthdate;
        }
    }
}