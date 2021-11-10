
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageVaccines extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vaccines);

        RecyclerView rv = findViewById(R.id.vaccineList_rv);

        VaccineItems[] vid = {
                new VaccineItems("SIN001", "Sinovac", "Beijing Pharma"),
                new VaccineItems("AZ0001", "AstraZeneca", "SII"),
                new VaccineItems("PFI001", "Pfizer", "BioNTech"),
                new VaccineItems("PFI002", "Pfizer", "BioNTech")
        };

        VaccineListAdapter adapter = new VaccineListAdapter(vid);
        rv.setAdapter(adapter);

        rv.addOnItemTouchListener(new RecyclerItemClickListener(rv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ManageVaccines.this, SelectedVid.class);
                intent.putExtra("Vaccines", vid[position]);
                startActivity(intent);
            }
        }));
    }
}
