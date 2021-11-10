
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {

        CardView mngApptsCardView;
        CardView mngVaccinesCardView;
        CardView mngHealthcareCentresCardView;
        CardView cardViewLogout;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mngApptsCardView = findViewById(R.id.card_view_manage_appts);
        mngVaccinesCardView = findViewById(R.id.card_view_manage_vaccines);
        mngHealthcareCentresCardView = findViewById(R.id.card_view_manage_healthcare_centres);
        cardViewLogout = findViewById(R.id.cardView_logout);

        mngVaccinesCardView.setOnClickListener(v ->{
            Intent manageVaccinesIntent = new Intent(AdminActivity.this, ManageVaccines.class);
            startActivity(manageVaccinesIntent);
        });
        
        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        
         mngHealthcareCentresCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManageHealthcareHome.class));
            }
        });


    }
}
