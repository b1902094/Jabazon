

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpUser extends AppCompatActivity {

    EditText edittextUsername;
    EditText edittextPassword;
    EditText emailEditText;
    EditText ICPassportEditText;
    Pattern PASSWORD_PATTERN = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$\\*]{3,24}");
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[com]+";
    String usernamePattern = "[a-zA-z0-9._-]+@[jabazonPatient]+\\.+[com]+";
    boolean isUsernameValid, isPasswordValid, isEmailValid, isICPassportValid;
    User user;
    public static String User_ID;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fbfs = FirebaseFirestore.getInstance();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        edittextUsername = findViewById(R.id.text_view_input_username);
        edittextPassword = findViewById(R.id.text_view_input_password);
        emailEditText = findViewById(R.id.text_view_input_email);
        ICPassportEditText = findViewById(R.id.text_view_input_ICPassport);
    }

    private void checkDataValidation() {
        //to check if username is valid
        if (edittextUsername.getText().toString().isEmpty()) {
            edittextUsername.setError("Please enter this field");
            isUsernameValid = false;
        } else if(!(edittextUsername.getText().toString().matches(usernamePattern))){
            edittextUsername.setError("Please include @jabazonPatient.com");
            isUsernameValid = false;
        }else{
            isUsernameValid = true;
        }
        //to check if email is valid
        if (emailEditText.getText().toString().isEmpty()) {
            emailEditText.setError("Please enter this field");
            isEmailValid = false;
        } else if (!(emailEditText.getText().toString()).matches(emailPattern)) {
            emailEditText.setError("Invalid Email Address");
            isEmailValid = false;
        } else {
            isEmailValid = true;
        }
        //to check if password is valid
        if (edittextPassword.getText().toString().isEmpty()) {
            edittextPassword.setError("Please enter this field");
            isPasswordValid = false;
        } else if (!PASSWORD_PATTERN.matcher(edittextPassword.getText().toString()).matches()) {
            edittextPassword.setError("Please include numeric digits and symbols like !@#$* in your password.");
            isPasswordValid = false;
        } else {
            isPasswordValid = true;
        }
        //to check if the IC Passport field is not empty
        if (ICPassportEditText.getText().toString().isEmpty()) {
            ICPassportEditText.setError("Please enter this field");
            isICPassportValid = false;
        } else {
            isICPassportValid = true;
        }

        if (isUsernameValid && isPasswordValid && isEmailValid && isICPassportValid) {
            AlertDialog.Builder ADBuilder2 = new AlertDialog.Builder(this);
            ADBuilder2.setTitle("Account created successfully");
            ADBuilder2.setMessage("Account has been created! Please proceed to login");
            ADBuilder2.setCancelable(false);
            ADBuilder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    dialogInterface.cancel();
                }
            });
            AlertDialog alert22 = ADBuilder2.create();
            alert22.show();
        }
    }

    public void signUpButtonOnClick(View view) {
        hideKeyboard(SignUpUser.this, view);
        checkDataValidation();
            mAuth.createUserWithEmailAndPassword(edittextUsername.getText().toString(), edittextPassword.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                DocumentReference df = fbfs.collection("Users").document(user.getUid());
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("Username", edittextUsername.getText().toString());
                                userInfo.put("Password", edittextPassword.getText().toString());
                                userInfo.put("Email", emailEditText.getText().toString());
                                userInfo.put("IC/Passport", ICPassportEditText.getText().toString());
                                //to specify user is patient
                                userInfo.put("isUser", "1");
                                df.set(userInfo);
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpUser.this, "Failed to create an account", Toast.LENGTH_SHORT).show();
                }
            });
    }
    private void hideKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void ShowHiddenPasswordOnClick(View view) {
        if(view.getId()==R.id.show_password_button){
            if(edittextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                edittextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                edittextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }

    }
}
