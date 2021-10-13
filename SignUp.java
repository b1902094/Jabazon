
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    EditText usernameTextView;
    EditText passwordTextView;
    EditText reenterPasswordTextView;
    EditText emailTextView;
    EditText ICPassportTextView;
    Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{8,24}");
    boolean isUsernameValid,isPasswordValid,isEmailValid;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        getSupportActionBar().setTitle("Jabazon");

        usernameTextView = findViewById(R.id.text_view_input_username);
        passwordTextView = findViewById(R.id.text_view_input_password);
        reenterPasswordTextView = findViewById(R.id.text_view_input_reenterPassword);
        emailTextView = findViewById(R.id.text_view_input_email);
        ICPassportTextView = findViewById(R.id.text_view_input_ICPassport);

        ImageButton tickIconImageButton = findViewById(R.id.image_button_tick_icon);
        tickIconImageButton.setOnClickListener(view -> {
            checkDataValidation();

        });
    }

    private void checkDataValidation(){
        //to check if username is valid
        if(usernameTextView.getText().toString().isEmpty()){
            isUsernameValid = false;
        }else{
            isUsernameValid = true;
        }
        //to check if email is valid
        if(emailTextView.getText().toString().isEmpty()){
            isEmailValid = false;
        }else if( Patterns.EMAIL_ADDRESS.matcher(emailTextView.toString()).matches()){
            emailTextView.setError("Please enter a valid email");
            isEmailValid = false;
        }
        else{
            isEmailValid = true;
        }
        //to check if password is valid
        if(passwordTextView.getText().toString().isEmpty()){
            isPasswordValid = false;
        }else if((passwordTextView.getText().toString().length() >= 8) && (PASSWORD_PATTERN.matcher(passwordTextView.toString()).matches())) {
            passwordTextView.setError("Please make your password length more than " +
                    "8 and include numeric digits and symbols in your password.");
            isPasswordValid = false;
        }else if((passwordTextView.getText().toString())!= reenterPasswordTextView.getText().toString()){
            reenterPasswordTextView.setError("Please ensure the password you've entered are the same as the above.");
            isPasswordValid =false;
        }else{
            isPasswordValid = true;
        }
        if(isUsernameValid&&isPasswordValid&&isEmailValid){
            Toast.makeText(getApplicationContext(),"Account created successfully!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent (getApplicationContext(),UserActivity.class);
            startActivity(intent);

        }


    }


}
