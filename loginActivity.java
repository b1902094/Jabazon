

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    CheckBox checkBox_rememberMe;
    Button button_login;
    SharedPreferences sharedPreferences;

    public static String User_ID;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore fbfs = FirebaseFirestore.getInstance();


    boolean hasUsername, hasPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextUsername = findViewById(R.id.editText_input_username);
        editTextPassword = findViewById(R.id.editText_input_password);
        button_login = findViewById(R.id.button_login);
        checkBox_rememberMe = findViewById(R.id.checkbox_rememberMe);

        editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                hasUsername = !s.toString().trim().isEmpty();
                updateBtnLogin();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                hasPassword = s.toString().trim().length() > 5;
                updateBtnLogin();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard(LoginActivity.this, view);
                mAuth.signInWithEmailAndPassword(editTextUsername.getText().toString(), editTextPassword.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                checkUserAccessLevel(authResult.getUser().getUid());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(findViewById(R.id.constraint_layout_login),"Username or password incorrect!",
                                BaseTransientBottomBar.LENGTH_INDEFINITE)
                                .setAction("Close", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        editTextUsername.setText("");
                                        editTextPassword.setText("");
                                        editTextUsername.requestFocus();
                                    }
                                }).show();
                    }
                });
            }
        });
        getLoginDetails();
    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fbfs.collection("Users").document(uid);
        //extract data from the document
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess: "+documentSnapshot.getData());
                //identify the user access level
                if(documentSnapshot.getString("isUser")!=null){
                    //user is patient
                    startActivity(new Intent(getApplicationContext(),UserActivity.class));
                    finish();
                }
                if(documentSnapshot.getString("isAdmin")!=null){
                    //user is admin
                    startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                    finish();
                }
            }
        });
    }

    public void getLoginDetails(){
        try{
            sharedPreferences = getSharedPreferences("loginData",MODE_PRIVATE);
            String username = sharedPreferences.getString("username","");
            String password = sharedPreferences.getString("password","");
            boolean remember = checkBox_rememberMe.isChecked();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username",username);
            editor.putString("password",password);
            editor.putBoolean("remember",remember);
            editor.apply();
        }catch(Exception ex){
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void RememberMeClick(View view){
        if(checkBox_rememberMe.isChecked()){
            saveLoginDetails();
        } else{
            clearLoginDetails();
        }
    }

    private void clearLoginDetails() {
        sharedPreferences = getSharedPreferences("loginData",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username","");
        editor.putString("password", "");
        editor.putBoolean("remember me", false);
        editor.apply();
    }

    private void saveLoginDetails() {
        sharedPreferences = getSharedPreferences("LoginDate", MODE_PRIVATE);
        String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        boolean remember = checkBox_rememberMe.isChecked();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("remember me", remember);
        editor.apply();
    }

    public void updateBtnLogin(){
        button_login.setEnabled(hasUsername&&hasPassword);
    }

    private void hideKeyboard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void ShowHiddenPasswordOnClick(View view) {
        if(view.getId()==R.id.show_password_button){
            if(editTextPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }else{
                editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        }

    }
}
