
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SelectedBatchItem extends AppCompatActivity {

    TextView tv_batchNo;
    TextView tv_numAppts;
    TextView tv_expDate;
    TextView tv_qtyAdmin;
    TextView tv_qtyAvail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_batch_item);

        try{
            tv_batchNo = findViewById(R.id.tv_selected_batchNo);
            tv_numAppts = findViewById(R.id.tv_num_pending_appta);
            tv_expDate = findViewById(R.id.tv_selected_exp_date);
            tv_qtyAdmin = findViewById(R.id.tv_selected_qty_admind);
            tv_qtyAvail = findViewById(R.id.tv_selected_qty_avail);

            BatchItems selectedBatch = (BatchItems) getIntent().getSerializableExtra("Batch Item");
            getSupportActionBar().setTitle(selectedBatch.vName);

            tv_batchNo.setText("Batch Number: " + selectedBatch.batchNo);
            tv_numAppts.setText("Number of Pending Appointments: " + selectedBatch.numAppts);
            tv_expDate.setText("Batch Expires on " + selectedBatch.expDate);
            tv_qtyAdmin.setText("Quantity Administered: " + selectedBatch.qtyAdmind);
            tv_qtyAvail.setText("Quantity Available: " + selectedBatch.qtyAvail);
        } catch (Exception exception) {
            Toast.makeText(SelectedBatchItem.this, exception.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
