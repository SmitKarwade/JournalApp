package com.example.jornal_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextInputLayout textInputLayout;
    TextInputLayout textInputLayout2;

    private TextView textView;
    private EditText name_txt;
    private AutoCompleteTextView pass_txt;
    private Button sign_in_btn, create_acc_btn;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fb_listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.light_theme);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textInputLayout = findViewById(R.id.textInputLayout);
        textInputLayout2 = findViewById(R.id.textInputLayout2);

        textView = findViewById(R.id.textView);
        name_txt = findViewById(R.id.name_txt);
        pass_txt = findViewById(R.id.pass_txt);
        create_acc_btn = findViewById(R.id.create_acc_btn);
        sign_in_btn = findViewById(R.id.sign_in_btn);

        create_acc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        sign_in_btn.setOnClickListener(v -> {
            if(!TextUtils.isEmpty(name_txt.getText().toString()) && !TextUtils.isEmpty(pass_txt.getText().toString())){
                String name = name_txt.getText().toString();
                String pass = pass_txt.getText().toString();
                loginToJournal(name, pass);
                Log.d("Sign", "Working!" + name + pass);
            }
        });

        fb_listener = firebaseAuth -> {
            if(firebaseAuth.getCurrentUser() != null){
                Intent i = new Intent(MainActivity.this, JournalListActivity.class);
                startActivity(i);
                finish();
            }
        };
    }

    private void loginToJournal(String name, String pass) {
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pass)){
            firebaseAuth.signInWithEmailAndPassword(name, pass).addOnSuccessListener(authResult -> {
                Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fb_listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fb_listener != null) {
            firebaseAuth.removeAuthStateListener(fb_listener);
        }
    }


}