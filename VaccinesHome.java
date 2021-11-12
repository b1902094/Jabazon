
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VaccinesHome extends AppCompatActivity {

    Button btnViewVaccineList;
    Button btnRecordNewVaccine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccines_home);

        btnViewVaccineList = findViewById(R.id.btn_view_list);
        btnRecordNewVaccine = findViewById(R.id.btn_record_vaccine);

        btnViewVaccineList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent listIntent = new Intent(getApplicationContext(), ManageVaccines.class);
                startActivity(listIntent);
            }
        });

        btnRecordNewVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recordIntent = new Intent(getApplicationContext(), RecordNewVaccine.class);
                startActivity(recordIntent);
            }
        });
    }
}
