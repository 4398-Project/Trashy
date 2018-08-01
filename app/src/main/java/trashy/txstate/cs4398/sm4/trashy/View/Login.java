package trashy.txstate.cs4398.sm4.trashy.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import trashy.txstate.cs4398.sm4.trashy.R;

public class Login extends AppCompatActivity {
    //Handles
    /**Handle for username field*/
    private EditText userNameField;
    /**Handle for password field*/
    private EditText passwordField;
    /**Handle for sign in button*/
    private Button signInButton;
    /**Handle for register button*/
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Linking handles
        userNameField = findViewById(R.id.userNameField);
        signInButton = findViewById(R.id.signInButton);

        //Assigning Listeners
        //Sign in button listener
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**Intent used to specify which activity to jump to next when signInButton is pressed*/
                Intent changeActivity;

                changeActivity = new Intent(Login.this, Trash.class);
                startActivity(changeActivity);
            }
        });

    }
}