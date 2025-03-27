package com.example.jornal_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {

    private TextView textView2;
    private EditText user_text, mail_text;
    private AutoCompleteTextView autoCompleteTextView;
    Button register_btn;

    // interaction with authentication
    private FirebaseAuth firebaseAuth;


    // keeps information
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.light_theme);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView2 = findViewById(R.id.textView2);
        user_text = findViewById(R.id.user_text);
        mail_text = findViewById(R.id.mail_text);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        register_btn = findViewById(R.id.register_btn);

        firebaseAuth = FirebaseAuth.getInstance();


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(user_text.getText().toString()) &&
                        !TextUtils.isEmpty(mail_text.getText().toString()) &&
                        !TextUtils.isEmpty(autoCompleteTextView.getText().toString())
                ){
                    String username = user_text.getText().toString();
                    String email = mail_text.getText().toString();
                    String pass = autoCompleteTextView.getText().toString();
                    createAccount(username, email, pass);
                }else {
                    Toast.makeText(RegisterActivity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createAccount(String username, String email, String pass){
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)){
            firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                        user_text.setText("");
                        mail_text.setText("");
                        autoCompleteTextView.setText("");
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }
}