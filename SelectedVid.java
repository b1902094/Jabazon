import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class SelectedVid extends AppCompatActivity {

    ImageButton imgBtnCal;
    EditText newBatchNo, newBatchExpDate, newBatchQtyAvail, newBatchQtyAdmin ;
    boolean isBatchNumValid, isExpDateValid, isQtyAvailVaild, isQtyAdminValid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    BatchItems batch;
    Button btnSubmit;
    ConstraintLayout clNewBatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_vid);

        clNewBatch = findViewById(R.id.layout_new_batch);
        newBatchNo = findViewById(R.id.et_new_batchNo);
        newBatchExpDate = findViewById(R.id.et_new_expDate);
        newBatchQtyAvail = findViewById(R.id.et_new_qtyAvail);
        newBatchQtyAdmin = findViewById(R.id.et_new_qtyAdmin);

        btnSubmit = findViewById(R.id.btn_submit_batch);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!newBatchNo.getText().toString().isEmpty()){
                    if(batch == null){
                        DocumentReference newBatchRef = db.collection("Batches").document();
                        batch = new BatchItems();
                        batch.setBatchNo(newBatchNo.getText().toString());
                        batch.setExpDate(newBatchExpDate.getText().toString());
                        batch.setQtyAdmin(newBatchQtyAdmin.getText().toString());
                        batch.setQtyAvail(newBatchQtyAvail.getText().toString());

                        db.collection("Batches")
                                .document(batch.getBatchNo())
                                .set(batch)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SelectedVid.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        DocumentReference BatchRef = db.collection("Batches")
                                .document(batch.getBatchNo());
                        BatchRef.update("batchNo", newBatchNo.getText().toString(),
                                "expDate", newBatchExpDate.getText().toString(),
                                "qtyAdmin", newBatchQtyAdmin.getText().toString(),
                                "qtyAvail", newBatchQtyAvail.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(SelectedVid.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }
        });

        imgBtnCal = findViewById(R.id.img_btn_selectedvid_calender);
        imgBtnCal.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int todayYear = calendar.get(Calendar.YEAR);
            int todayMonth = calendar.get(Calendar.MONTH);
            int todayDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(SelectedVid.this,
                    (view, year1, month1, dayOfMonth1) -> newBatchExpDate.setText(dayOfMonth1 + "/" + (month1+1) + "/" + year1),
                    todayYear, todayMonth, todayDayOfMonth);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();

        });
        dataValidation();

    }

    private void dataValidation() {
        if(newBatchNo.getText() == null){
            isBatchNumValid = false;
        }
        else{
            isBatchNumValid = true;
        }
        if(newBatchExpDate.getText().toString().isEmpty()){
            isExpDateValid = false;
        }else{
            isExpDateValid = true;
        }
        if(newBatchQtyAdmin.getText() == null){
            isQtyAdminValid = false;
        }else{
            isQtyAdminValid = true;
        }
        if(newBatchQtyAvail.getText() == null){
            isQtyAvailVaild = false;
        }else{
            isQtyAvailVaild = true;
        }

        if( isBatchNumValid && isExpDateValid && isQtyAdminValid && isQtyAvailVaild) {
            AlertDialog.Builder ADBuilder1 = new AlertDialog.Builder(this);
            ADBuilder1.setMessage("Batch has been recorded");
            ADBuilder1.setCancelable(true);
            ADBuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = new Intent(getApplicationContext(), ManageVaccines.class);
                    startActivity(intent);
                }
            });
            AlertDialog alert11 = ADBuilder1.create();
            alert11.setTitle("Batch Record Successful");
            alert11.show();

        }
        else{
            Snackbar.make(findViewById(R.id.layout_new_batch),"Please fill in all the fields !", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
        }
    }
}
