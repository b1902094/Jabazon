
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vicky.jabazon.Util.Healthcare;
import com.vicky.jabazon.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class ManageHealthcareHome extends AppCompatActivity {

    SearchView searchView;
    ListView listViewHC;
    ArrayList<String> list;
    ArrayAdapter<String> searchAdapter;
    ListAdapter adapter;
    List<Healthcare> healthcaresArray;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FloatingActionButton addHC;
    Healthcare healthcare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_healthcare_home);

        searchView = findViewById(R.id.searchView);
        listViewHC = findViewById(R.id.list_view_hc_list);
        addHC = findViewById(R.id.floating_action_btn_add_healthcare);

        getAllHealthcare();

        addHC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageHealthcareHome.this, RecordHealthcareCentre.class));
            }
        });


        adapter = new ArrayAdapter<>(ManageHealthcareHome.this, android.R.layout.simple_list_item_1, list);
        listViewHC.setAdapter(adapter);

        listViewHC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ManageHealthcareHome.this, RecordHealthcareCentre.class);
                Healthcare healthcare = healthcaresArray.get(i);
                intent.putExtra("HealthcareCentre", healthcare);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(list.contains(query)){
                    searchAdapter.getFilter().filter(query);
                } else{
                    Toast.makeText(ManageHealthcareHome.this, "Batch Number does not exist", Toast.LENGTH_LONG);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getAllHealthcare() {
        db.collection("HealthcareCentres")
                .whereEqualTo("centreName", Util.HEALTHCARECENTRES.getCentreName())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            healthcaresArray = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult())
                                healthcare = document.toObject(Healthcare.class);
                            healthcaresArray.add(healthcare);
                        }
                        adapter = new ArrayAdapter<Healthcare>(ManageHealthcareHome.this, android.R.layout.simple_list_item_1, healthcaresArray);
                        listViewHC.setAdapter(adapter);
                    }
                });
    }
}
