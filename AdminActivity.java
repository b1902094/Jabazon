import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends AppCompatActivity {
    CardView mngAppsCardView;
    CardView mngVaccinesCardView;
    CardView mngHealthcareCentresCardView;
    CardView cardViewLogout;
    String[] vaccines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mngAppsCardView = findViewById(R.id.card_view_manage_appts);
        mngVaccinesCardView = findViewById(R.id.card_view_manage_vaccines);
        mngHealthcareCentresCardView = findViewById(R.id.card_view_manage_healthcare_centres);
        cardViewLogout = findViewById(R.id.card_view_logout);

        vaccines = getResources().getStringArray(R.array.vaccines);

        TextView tvHCCName = findViewById(R.id.tv_hcc_name_ondisplay); //TODO: display hcc Name upon login


        mngVaccinesCardView.setOnClickListener(v ->{
            Intent intent = new Intent(AdminActivity.this, VaccinesHome.class);
            startActivity(intent);

            /*AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage("Select a Vaccine ID to Proceed");
            adb.setCancelable(true);
            adb.setPositiveButton("OK", (dialogInterface, i) -> {
                dialogInterface.cancel();
                Intent intent = new Intent(getApplicationContext(), VaccinesHome.class);
                startActivity(intent);
            });
            AlertDialog ad = adb.create();
            ad.setTitle("Select VID");
            ad.show();*/
        });

        cardViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        mngHealthcareCentresCardView.setOnClickListener(view -> {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage("Select a Vaccine Batch to Proceed");
            adb.setCancelable(true);
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = new Intent(getApplicationContext(), ManageBatchItems.class);
                    startActivity(intent);
                }
            });
            AlertDialog ad = adb.create();
            ad.setTitle("Select Batch");
            ad.show();
        });

        mngAppsCardView.setOnClickListener(view -> {
            Intent intent = new Intent(AdminActivity.this, ManageApptsHome.class);
            startActivity(intent);
        });
        }


    }
