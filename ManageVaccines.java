
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ManageVaccines extends AppCompatActivity {

    ListView lisViewVaccines;
    List<Vaccines> vaccinesArray;
    ListAdapter vaccinesAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ConstraintLayout clManageVaccines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_vaccines);

        lisViewVaccines = findViewById(R.id.list_view_vaccines_list);
        clManageVaccines = findViewById(R.id.layout_vaccines_list);

        lisViewVaccines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ManageVaccines.this, SelectedVid.class);
                Vaccines vaccines = vaccinesArray.get(i);
                intent.putExtra("Vaccines", vaccines);
                startActivity(intent);
            }
        });
        getCurrentVaccines();
    }

    private void getCurrentVaccines() {
        db.collection("Vaccines").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    vaccinesArray = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Vaccines vaccines = document.toObject(Vaccines.class);
                        vaccinesArray.add(vaccines);
                    }
                    vaccinesAdapter = new ArrayAdapter<Vaccines>(ManageVaccines.this, android.R.layout.simple_list_item_1,vaccinesArray);
                    lisViewVaccines.setAdapter(vaccinesAdapter);
                }
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        getCurrentVaccines();
    }
}
