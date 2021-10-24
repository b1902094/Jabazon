
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        CardView mngApptsCardView;
        CardView mngVaccinesCardView;
        CardView mngHealthcareCentresCardView;
        CardView cardViewLogout;

        mngApptsCardView = findViewById(R.id.card_view_manage_appts);
        mngVaccinesCardView = findViewById(R.id.card_view_manage_vaccines);
        mngHealthcareCentresCardView = findViewById(R.id.card_view_manage_healthcare_centres);
        cardViewLogout = findViewById(R.id.cardView_logout);

        mngVaccinesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminActivity.this, record_vaccine.class);
                startActivity(intent);
            }
        });
        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


    }
}
