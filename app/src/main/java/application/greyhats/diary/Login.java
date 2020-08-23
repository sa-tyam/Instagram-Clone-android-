package application.greyhats.diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    AutoCompleteTextView mEmailView;
    EditText mPasswordView;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = findViewById(R.id.login_email);
        mPasswordView = findViewById(R.id.login_password);

        mAuth = FirebaseAuth.getInstance();
    }

    public void register_page(View view) {
        Intent myIntent = new Intent(Login.this , Register.class);
        startActivity(myIntent);
    }

    public void login_user(View view) {
        attemptLogin();
    }

    private void attemptLogin(){
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        if ( email == "" || password == "") return;

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    showErrorDialog ("login failed");
                } else {
                    Intent myIntent = new Intent (Login.this , MainActivity.class);
                    finish();
                    startActivity(myIntent);
                }
            }
        });
    }

    private void showErrorDialog ( String message ){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok , null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
