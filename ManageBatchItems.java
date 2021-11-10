
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageBatchItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_batch_items);

        RecyclerView recyclerView = findViewById(R.id.batchItem_rv);

        BatchItems[] batches = {
                new BatchItems("56","16", "16/02/2023", "1900", "0"),
                new BatchItems("67","25", "19/10/2023", "2000", "1"),
                new BatchItems("10", "12", "30/10/2022", "1400", "16"),
                new BatchItems("12","20", "30/10/2023", "1400", "0")
        };

        BatchItemAdapter adapter = new BatchItemAdapter(batches);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ManageBatchItems.this, SelectedBatchItem.class);
                intent.putExtra("Batch Items", batches[position]);
                startActivity(intent);
            }
        }));
    }
}
