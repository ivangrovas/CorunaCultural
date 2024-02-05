package com.example.corunacultural;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginSC extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sc);
        Button signInBT = findViewById(R.id.signInButton2);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.mailBox);
        editTextPassword =findViewById(R.id.pwdBox);
        signInBT.setOnClickListener(v -> iniciarSesion());
        signInBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = new Intent(LoginSC.this, MainSC.class);
                startActivity(signInIntent);
            }
        });
    }

    private void iniciarSesion(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //Validaciones de los campos...

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()){
                        startActivity(new Intent(LoginSC.this,MainSC.class));
                        finish();
                    } else {
                        Toast.makeText(LoginSC.this,"Error al iniciar sesión: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
