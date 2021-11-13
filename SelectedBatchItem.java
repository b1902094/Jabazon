
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SelectedBatchItem extends AppCompatActivity {

    TextView tv_batchNo;
    TextView tv_expDate;
    TextView tv_qtyAdmin;
    TextView tv_qtyAvail;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_batch_item);

        try{
            tv_batchNo = findViewById(R.id.tv_selected_batchNo);
            tv_expDate = findViewById(R.id.tv_selected_exp_date);
            tv_qtyAdmin = findViewById(R.id.tv_selected_qty_admind);
            tv_qtyAvail = findViewById(R.id.tv_selected_qty_avail);

            BatchItems selectedBatch = (BatchItems) getIntent().getSerializableExtra("Batches");
            getSupportActionBar().setTitle(selectedBatch.batchNo);

            if(selectedBatch != null){
                tv_batchNo.setText("Batch Number: " + selectedBatch.getBatchNo());
                tv_expDate.setText("Batch Expiry Date: " + selectedBatch.getExpDate());
                tv_qtyAdmin.setText("Quantity Administered: " + selectedBatch.getQtyAdmin());
                tv_qtyAvail.setText("Quantity Available: " + selectedBatch.getQtyAvail());
            }

        } catch (Exception exception) {
            Toast.makeText(SelectedBatchItem.this, exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        Button btnViewAppts = findViewById(R.id.btn_view_appts);
        btnViewAppts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectedBatchItem.this, UserVaccineAppointment.class);
                startActivity(intent);
            }
        });
    }
