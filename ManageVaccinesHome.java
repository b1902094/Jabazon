
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vicky.jabazon.Util.Util;
import com.vicky.jabazon.Util.Vaccines;

import java.util.ArrayList;
import java.util.List;

public class ManageVaccinesHome extends AppCompatActivity {

    ListView listViewVaccines;
    List<Vaccines> vaccinesArray;
    ListAdapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ConstraintLayout constraintLayoutVaccines;
    FloatingActionButton addVaccine;
    Vaccines vaccines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vaccines_home);

        listViewVaccines = findViewById(R.id.list_view_vaccines_list);
        constraintLayoutVaccines = findViewById(R.id.layout_vaccines);
        addVaccine = findViewById(R.id.floating_action_btn_add_vaccine);

        getAllVaccines();

        addVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageVaccinesHome.this, RecordVaccine.class));

            }
        });

        listViewVaccines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ManageVaccinesHome.this, RecordVaccine.class);
                Vaccines vaccines = vaccinesArray.get(i);
                intent.putExtra("vacccine", vaccines);
                startActivity(intent);
            }
        });
    }

    private void getAllVaccines(){
        db.collection("Vaccines")
                .whereEqualTo("vaccineId", Util.VACCINES.getVaccineID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            vaccinesArray = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult())
                                vaccines = document.toObject(Vaccines.class);
                            vaccinesArray.add(vaccines);
                        }
                        adapter = new ArrayAdapter<Vaccines>(ManageVaccinesHome.this, android.R.layout.simple_list_item_1, vaccinesArray);
                        listViewVaccines.setAdapter(adapter);

                    }
                });
    }
}
