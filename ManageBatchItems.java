
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

public class ManageBatchItems extends AppCompatActivity {

    ListView listViewBatches;
    List<BatchItems> batchArray;
    ListAdapter batchAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ConstraintLayout clManageBatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_batch_items);

        listViewBatches = findViewById(R.id.list_view_batch_list);
        clManageBatch = findViewById(R.id.layout_batch_list);

        listViewBatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ManageBatchItems.this, SelectedBatchItem.class);
                BatchItems batch = batchArray.get(i);
                intent.putExtra("Batches", batch);
                startActivity(intent);
            }
        });
        getCurrentBatchList();

    }

    private void getCurrentBatchList() {
        db.collection("Batches").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    batchArray = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        BatchItems batch = document.toObject(BatchItems.class);
                        batchArray.add(batch);
                    }
                    batchAdapter = new ArrayAdapter<BatchItems>(ManageBatchItems.this, android.R.layout.simple_list_item_1,batchArray);
                    listViewBatches.setAdapter(batchAdapter);
                }
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        getCurrentBatchList();
    }
}
