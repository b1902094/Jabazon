
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class UserActivity extends AppCompatActivity {
    CardView vaccinationAppointmentCard;
    CardView logoutCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        vaccinationAppointmentCard = findViewById(R.id.cardView_vaccination_appointmentUser);
        logoutCard = findViewById(R.id.cardView_logout);

        vaccinationAppointmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vaccinationAppointmentIntent = new Intent(UserActivity.this,vaccinationAppointment.class);
                startActivity(vaccinationAppointmentIntent);
            }
        });

        logoutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


    }
}
