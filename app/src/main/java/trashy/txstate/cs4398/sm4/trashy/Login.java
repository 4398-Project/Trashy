package trashy.txstate.cs4398.sm4.trashy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    //Handles
    /**Handle for username field*/
    private EditText userNameField;
    private EditText passwordField;
    private Button signInButton;

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
                Intent changeActivity;

                changeActivity = new Intent(Login.this, Trash.class);
                startActivity(changeActivity);
            }
        });
    }
}