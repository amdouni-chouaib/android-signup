package com.example.okbbi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // creating variables for
    // EditText and buttons.
    private EditText EmailEdt, PassEdt, RepassEdt;
    private Button sendDatabtn;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mAuth;
    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing our edittext and button

        EmailEdt = findViewById(R.id.Email);
        PassEdt = findViewById(R.id.pass);
        RepassEdt = findViewById(R.id.repass);

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth=FirebaseAuth.getInstance();
        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("user");

        // initializing our object
        // class variable.
        user = new User();

        sendDatabtn = findViewById(R.id.btn);

        // adding on click listener for our button.
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from our edittext fields.
                String email = EmailEdt.getText().toString();
                String password = PassEdt.getText().toString();
                String repassword = RepassEdt.getText().toString();

                // below line is for checking whether the
                // edittext fields are empty or not.
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(repassword)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.

                    addDatatoFirebase(email, password, repassword);
                    mAuth
                            .createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task)
                                {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(),
                                                        "Registration successful!",
                                                        Toast.LENGTH_LONG)
                                                .show();

                                        // hide the progress bar


                                        // if the user created intent to login activity

                                    }
                                    else {

                                        // Registration failed
                                        Toast.makeText(
                                                        getApplicationContext(),
                                                        "Registration failed!!"
                                                                + " Please try again later",
                                                        Toast.LENGTH_LONG)
                                                .show();

                                        // hide the progress bar

                                    }
                                }
                            });


                }
            }
        });
    }
    int i=0;
    private void addDatatoFirebase(String Email, String pass, String repass) {
        // below 3 lines of code is used to set
        // data in our object class.
       user.setEmail(Email);
       user.setPassword(pass);
       user.setRePassword(repass);

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String email = EmailEdt.getText().toString();
                String password = PassEdt.getText().toString();

                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.child(i+"").setValue(user);


                // after adding this data we are showing toast message.
                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(MainActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
        i++;
    }
}
