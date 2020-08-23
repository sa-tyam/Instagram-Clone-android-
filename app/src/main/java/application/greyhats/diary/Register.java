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

public class Register extends AppCompatActivity {

    AutoCompleteTextView mEmailView;
    EditText mPasswordView;
    EditText mConfirmPasswordView;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
        mPasswordView = (EditText) findViewById(R.id.register_password);
        mConfirmPasswordView = (EditText) findViewById(R.id.register_confirmPassword);

        mAuth = FirebaseAuth.getInstance();
    }

    public void signup_user(View view) {
        createFirebaseUser();
    }
    public boolean isEmailValid ( String email ){
        if ( email.contains("@")) return true;
        return false;
    }

    public boolean isPasswordValid (String password , String confirmPassword ){
        if ( password.equals(confirmPassword) && password.length() > 5 ) return true;
        return false;
    }

    private void createFirebaseUser (){

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();

        if ( isEmailValid(email) && isPasswordValid(password,confirmPassword)){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        showErrorDialog("Registration Attempt failed");
                    } else {
                        startActivity(new Intent(Register.this , MainActivity.class));
                    }
                }
            });
        } else {
            showErrorDialog("Email or Password is invalid");
        }
    }

    public void showErrorDialog ( String message ){
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
