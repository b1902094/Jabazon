import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

public class Settings extends AppCompatActivity {

    CardView updatePersonalDetails;
    CardView changePassword;
    CardView changeLanguage;
    CardView helpdesk;
    CardView privacyPolicy;
    CardView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        updatePersonalDetails = findViewById(R.id.card_view_upd_icon_container); //done
        changePassword = findViewById(R.id.card_view_change_password_icon_container); //still doing
        changeLanguage = findViewById(R.id.card_view_change_language_icon_container); //still doing
        helpdesk = findViewById(R.id.card_view_helpdesk_icon_container); //still doing
        privacyPolicy = findViewById(R.id.card_view_privacy_policy_icon_container); //still doing
        logout = findViewById(R.id.card_view_logout_icon_container); //done

        updatePersonalDetails.setOnClickListener(view -> {
            Intent updIntent = new Intent(Settings.this, updatePersonalDetails.class);
        });

        logout.setOnClickListener(view -> {
            Intent logoutIntent = new Intent(Settings.this, LoginActivity.class);
        });
    }
}
