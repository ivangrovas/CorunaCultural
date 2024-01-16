package com.example.corunacultural;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RegisterSC extends AppCompatActivity {
    private EditText editTextNombre, editTextCorreo, editTextContraseña, editTextConfirmarContraseña, editTextFechaNacimiento;
    private Button buttonRegistrar;
    private Context context = this;
    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sc);

        editTextNombre = findViewById(R.id.Name);
        editTextCorreo = findViewById(R.id.Email);
        editTextContraseña = findViewById(R.id.Password);
        editTextConfirmarContraseña = findViewById(R.id.ConfrimPassword);
        editTextFechaNacimiento = findViewById(R.id.FechaNacimiento);
        buttonRegistrar = findViewById(R.id.buttonRegistrer);
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

        // Eliminé la sección relacionada con el botón de registro y el backend
        // Si necesitas más funcionalidades relacionadas con el front-end, puedes agregarlas aquí.
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
}