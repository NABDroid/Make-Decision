package bd.com.nabdroid.makedecision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityLogIn extends AppCompatActivity {
    private EditText emailET, passwordET;
    private Button loginBTN;
    private TextView goForSignUpBTN;
    private String email, password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        init();

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailET.getText().toString().trim();
                password = passwordET.getText().toString().trim();
                logIn(email, password);
            }
        });

        goForSignUpBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityLogIn.this, ActivityRegistration.class));
            }
        });


    }

    private void logIn(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(ActivityLogIn.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(ActivityLogIn.this, MainActivity.class));
                        }
                        else {
                            Toast.makeText(ActivityLogIn.this, "Email and password didn't matched", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void init() {
        emailET = findViewById(R.id.emailETLI);
        passwordET = findViewById(R.id.passwordETLI);
        loginBTN = findViewById(R.id.loginBTNLI);
        firebaseAuth = FirebaseAuth.getInstance();
        goForSignUpBTN = findViewById(R.id.goForSignUpBTNLI);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null){
            startActivity(new Intent(ActivityLogIn.this, MainActivity.class));
        }
    }





}
