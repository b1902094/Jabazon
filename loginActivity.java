
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    CheckBox checkBox_rememberMe;
    Button button_login;
    SharedPreferences sharedPreferences;

    boolean hasUsername, hasPassword,isUserFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().setTitle("Jabazon");

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
                DatabaseHandler Jab_DB = new DatabaseHandler((LoginActivity.this));
                User user = Jab_DB.getUser(editTextUsername.getText().toString(), editTextPassword.getText().toString());
                if(user != null){
                    Util.USER = user;
                    //Toast.makeText(LoginActivity.this,String.valueOf(user.getUserName()),Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),UserActivity.class));
                }else if(editTextUsername.getText().toString().equals("jabazonAdmin")&& editTextPassword.getText().toString().equals("Jabazon123*")){
                    startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                }
                else {
                    Snackbar.make(findViewById(R.id.constraint_layout_login),"Username or password is wrong !", BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            editTextPassword.setText("");
                            editTextUsername.setText("");
                            editTextUsername.requestFocus();
                        }
                    }).show();
                }
            }
        });
        getLoginDetails();
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

}
