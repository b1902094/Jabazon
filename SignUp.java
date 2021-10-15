

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    EditText usernameEditText;
    EditText passwordEditText;
    EditText reenterPasswordEditText;
    EditText emailEditText;
    EditText ICPassportEditText;
    Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$\\*]{3,24}");
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[com]+";
    boolean isUsernameValid,isPasswordValid,isEmailValid,isICPassportValid;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        getSupportActionBar().setTitle("Jabazon");

        usernameEditText = findViewById(R.id.text_view_input_username);
        passwordEditText = findViewById(R.id.text_view_input_password);
        reenterPasswordEditText = findViewById(R.id.text_view_input_reenterPassword);
        emailEditText = findViewById(R.id.text_view_input_email);
        ICPassportEditText = findViewById(R.id.text_view_input_ICPassport);
    }

    private void checkDataValidation(){
        //to check if username is valid
        if(usernameEditText.getText().toString().isEmpty()){
            usernameEditText.setError("Please enter this field");
            isUsernameValid = false;
        }else{
            isUsernameValid = true;
        }
        //to check if email is valid
        if(emailEditText.getText().toString().isEmpty()){
            emailEditText.setError("Please enter this field");
            isEmailValid = false;
        }else if(!(emailEditText.getText().toString()).matches(emailPattern)){
            emailEditText.setError("Invalid Email Address");
            isEmailValid = false;
        }
        else{
            isEmailValid = true;
        }
        //to check if password is valid
        if(passwordEditText.getText().toString().isEmpty()){
            passwordEditText.setError("Please enter this field");
            isPasswordValid = false;
        }else if(!PASSWORD_PATTERN.matcher(passwordEditText.getText().toString()).matches()) {
            passwordEditText.setError("Please include numeric digits and symbols in your password.");
            isPasswordValid = false;
        }else if(!(passwordEditText.getText().toString().equals(reenterPasswordEditText.getText().toString()))){
            reenterPasswordEditText.setError("Please ensure the password you've entered are the same as the above.");
            isPasswordValid =false;
        }else{
            isPasswordValid = true;
        }
        //to check if the IC Passport field is not empty
        if(ICPassportEditText.getText().toString().isEmpty()){
            ICPassportEditText.setError("Please enter this field");
            isICPassportValid = false;
        }else{
            isICPassportValid = true;
        }

        if(isUsernameValid&&isPasswordValid&&isEmailValid && isICPassportValid){
            AlertDialog.Builder ADBuilder2 = new AlertDialog.Builder(this);
            ADBuilder2.setMessage("Account has been created! Please proceed to login");
            ADBuilder2.setCancelable(true);
            ADBuilder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alert22 = ADBuilder2.create();
            alert22.setTitle("Account created successfully");
            alert22.show();
        }
    }

    public void signUpButtonOnClick(View view){
        DatabaseHandler Jab_DB = new DatabaseHandler(this);
        boolean isExist = Jab_DB.isUsernameExists(usernameEditText.getText().toString());
        if(isExist){
            Toast.makeText(SignUp.this, "Username is already exist!", Toast.LENGTH_SHORT).show();
        }else {
            checkDataValidation();
            User user = new User();
            user.setUserName(usernameEditText.getText().toString());
            user.setUserPassword(passwordEditText.getText().toString());
            Jab_DB.addUser(user);
            finish();
        }
    }


}
