import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vicky.jabazon.Util.User;
import com.vicky.jabazon.Util.Util;
import com.vicky.jabazon.data.DatabaseHandler;

public class updatePersonalDetails extends AppCompatActivity {

    EditText editTextUpdateUsername;
    EditText editTextUpdatePassword;
    Button btnSaveUpdate;
    User user;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_personal_details);

        editTextUpdateUsername = findViewById(R.id.edit_text_update_username);
        editTextUpdatePassword = findViewById(R.id.edit_text_update_password);
        btnSaveUpdate = findViewById(R.id.btn_save_update);

        db = new DatabaseHandler(this);

        user = (User) getIntent().getSerializableExtra("login");

        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTextUpdateUsername.getText().toString().isEmpty()){
                    if (user == null){
                        user = new User();
                        user.setUserName(editTextUpdateUsername.getText().toString());
                        user.setUserPassword(editTextUpdatePassword.getText().toString());
                        db.updateUser(user);
                    }
                    db.close();
                    finish();
                }
            }
        });
    }
}
