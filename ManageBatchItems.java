import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

        listViewBatches.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ManageBatchItems.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete this vaccine batch?");
                alert.setCancelable(true);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BatchItems batch = batchArray.get(position);
                        db.collection("Batches").document(batch.getBatchNo())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        getCurrentBatchList();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ManageBatchItems.this,
                                                e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
                return true;
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
