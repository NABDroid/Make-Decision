package bd.com.nabdroid.makedecision.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import bd.com.nabdroid.makedecision.R;
import bd.com.nabdroid.makedecision.pojo.UserProfile;

public class ActivityRegistration extends AppCompatActivity {
    private EditText userNameET, mobileNumberET, emailET, dateOfBirthET, passwordET, confirmPasswordET;
    private RadioGroup genderRG;
    private TextView goForSignUp;
    private Button registerBTN;
    private String userName, mobileNumber, email, dateOfBirth, gender, password, confirmPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromFields();


                if (confirmPassword.equals(password)) {
                    createUser();
                } else {
                    Toast.makeText(ActivityRegistration.this, "Password didn't mached", Toast.LENGTH_SHORT).show();
                }


            }
        });

        goForSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityRegistration.this, ActivityLogIn.class));
            }
        });


    }

    //--------------------------------------------------------------------------------------------------
    //init fields
    private void init() {
        genderRG = findViewById(R.id.genderRGRegAct);
        userNameET = findViewById(R.id.firstNameETRegAct);
        mobileNumberET = findViewById(R.id.mobileNumberETRegAct);
        goForSignUp = findViewById(R.id.goForSignUp);
        emailET = findViewById(R.id.emailETRegAct);
        dateOfBirthET = findViewById(R.id.dobETRegAct);
        passwordET = findViewById(R.id.passwordETRegAct);
        confirmPasswordET = findViewById(R.id.confirmPasswordETRegAct);
        registerBTN = findViewById(R.id.registerBTNRegAct);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    //--------------------------------------------------------------------------------------------------
//save data to db
    private void saveInfo() {
        String uId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference userRef = databaseReference.child("UserInfo").child(uId);
        UserProfile userProfile = new UserProfile(userName, mobileNumber, email, dateOfBirth, gender, password);

        userRef.setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(ActivityRegistration.this, MainActivity.class));
                } else {
                    Toast.makeText(ActivityRegistration.this, "Error", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    // collect data form fields
    private void getDataFromFields() {
        int genderId = genderRG.getCheckedRadioButtonId();
        if (genderId == R.id.maleId) {
            gender = "Male";
        } else {
            gender = "Female";
        }
        userName = userNameET.getText().toString().trim();
        mobileNumber = mobileNumberET.getText().toString().trim();
        email = emailET.getText().toString().trim();
        dateOfBirth = dateOfBirthET.getText().toString().trim();
        password = passwordET.getText().toString().trim();
        confirmPassword = confirmPasswordET.getText().toString().trim();
    }

    //Register users
    private void createUser() {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(ActivityRegistration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            saveInfo();
                        } else {
                            String error = task.getException().toString().trim();
                            Toast.makeText(ActivityRegistration.this, "Message: " + error + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}
