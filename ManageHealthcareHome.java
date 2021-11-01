package com.vicky.jabazon;

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

import java.util.ArrayList;
import java.util.List;


public class ManageHealthcareHome extends AppCompatActivity {

    FloatingActionButton addBtn;
    ListView listViewHCC;
    List<HealthcareCentres> HCCArray;
    ListAdapter adapter;
    ConstraintLayout clHCC;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_healthcare_home);

        listViewHCC = findViewById(R.id.list_view_hc_list);
        addBtn = findViewById(R.id.floating_action_btn_add_healthcare);
        clHCC = findViewById(R.id.layout_hcc);

        getCurrentHCCList();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManageHealthcareHome.this, RecordHealthcareCentre.class));
            }
        });

        listViewHCC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ManageHealthcareHome.this, RecordHealthcareCentre.class);
                HealthcareCentres hcc = HCCArray.get(i);
                intent.putExtra("healthcareCentres", hcc);
                startActivity(intent);
            }
        });

}

    private void getCurrentHCCList() {
        db.collection("HealthcareCentres")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            HCCArray = new ArrayList<>();
                            for(QueryDocumentSnapshot doc : task.getResult()){
                                HealthcareCentres hcc = doc.toObject(HealthcareCentres.class);
                                HCCArray.add(hcc);
                            }
                            adapter = new ArrayAdapter<HealthcareCentres>(ManageHealthcareHome.this,
                                    android.R.layout.simple_list_item_1, HCCArray);
                            listViewHCC.setAdapter(adapter);
                        }
                    }
                });
    }
    protected void onResume(){
        super.onResume();
        getCurrentHCCList();
    }
    }
