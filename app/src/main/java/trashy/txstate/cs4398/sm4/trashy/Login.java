package trashy.txstate.cs4398.sm4.trashy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.Toast;
import android.util.Log;
import com.google.firebase.auth.AuthResult;
import android.support.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;


public class Login extends AppCompatActivity {
    //Handles
    /**Handle for username field*/
    private EditText userNameField;
    private EditText passwordField;
    private Button signInButton;
    private Button registerButton;
    private FirebaseAuth mAuth;
    private String TAG;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        //Linking handles
        userNameField = findViewById(R.id.userNameField);
        passwordField = findViewById(R.id.passwordField);
        signInButton = findViewById(R.id.signInButton);
        registerButton = findViewById(R.id.registerButton);

        //Assigning Listeners
        //Sign in button listener
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userNameField.getText().toString().isEmpty() || passwordField.getText().toString().isEmpty()) {
                    // Sign in fails due to one of the credentials being empty
                    Log.d(TAG, "Sign in failed. A sign in field is empty.");
                    Toast.makeText(Login.this, "Sign in failed.",
                            Toast.LENGTH_LONG).show();
                    updateUI(null);
                } else {
                    mAuth.signInWithEmailAndPassword(userNameField.getText().toString(), passwordField.getText().toString())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {

                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (rootUser(userNameField.getText().toString(), passwordField.getText().toString())) {
                                        Log.d(TAG, "rootUserDetected");
                                        Toast.makeText(Login.this, "Root User Detected.",
                                                Toast.LENGTH_LONG).show();
                                    } else if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        Toast.makeText(Login.this, "Sign in successful.",
                                                Toast.LENGTH_LONG).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        updateUI(user);

                                        Intent changeActivity;

                                        changeActivity = new Intent(Login.this, Trash.class);
                                        startActivity(changeActivity);
                                    } else {
                                        // Sign in fails due to incorrect credentials or error connecting to server
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Sign in failed.",
                                                Toast.LENGTH_LONG).show();
                                        updateUI(null);
                                    }

                                    // ...
                                }
                            });
                }
            }
        });

        //Register button listener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //Retrieving user entered values
                mAuth.createUserWithEmailAndPassword(userNameField.getText().toString(), passwordField.getText().toString())
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    Toast.makeText(Login.this, "Registration successful.",
                                            Toast.LENGTH_LONG).show();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    //Note: Not sure if Login correct here
                                    Toast.makeText(Login.this, "Registration failed.  Use a valid email and Confirm password is 6+ characters.",
                                            Toast.LENGTH_LONG).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });

    }

    private boolean rootUser(String userName, String password){
        if (userNameField.getText().toString().equals("mmr161@txstate.edu") && passwordField.getText().toString().equals("rootaccess")) {
            return true;
        } else if (userNameField.getText().toString().equals("acs144@txstate.edu") && passwordField.getText().toString().equals("rootaccess")) {
            return true;
        } else if (userNameField.getText().toString().equals("dlr195@txstate.edu") && passwordField.getText().toString().equals("rootaccess")) {
            return true;
        } else if (userNameField.getText().toString().equals("m_v188@txstate.edu") && passwordField.getText().toString().equals("rootaccess")) {
            return true;
        }
        return false;
    }

    //TODO
    private void updateUI(FirebaseUser user) {
        /*hideProgressDialog();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);
            mDetailTextView.setText(null);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }*/
    }
}