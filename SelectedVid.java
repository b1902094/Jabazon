
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class SelectedVid extends AppCompatActivity {

    ImageView imgBtnCal;
    EditText batchNo, expDate, qtyAvail, qtyAdmin ;
    boolean isBatchNoValid, isDateValid, isQtyAvailVaild, isQtyAdminValid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Vaccines vaccines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_vid);

        try {
            TextView tvName = findViewById(R.id.tv_selected_vName);
            TextView tvManufacturer = findViewById(R.id.tv_selected_manufacturer);

            VaccineItems selectedVID = (VaccineItems) getIntent().getSerializableExtra("Vaccines");
            getSupportActionBar().setTitle(selectedVID.vID);

            tvName.setText(selectedVID.vName);
            tvManufacturer.setText(selectedVID.manuFacturer);
            EditText batchNo = findViewById(R.id.et_new_batchNo);
            EditText expDate = findViewById(R.id.et_new_expDate);
            EditText qtyAvail = findViewById(R.id.et_new_qtyAvail);
            EditText qtyAdmin = findViewById(R.id.et_new_qtyAdmin);

            imgBtnCal = findViewById(R.id.img_btn_calender);
            imgBtnCal.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                int todayYear = calendar.get(Calendar.YEAR);
                int todayMonth = calendar.get(Calendar.MONTH);
                int todayDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(SelectedVid.this,
                        (view, year1, month1, dayOfMonth1) -> expDate.setText(dayOfMonth1 + "/" + (month1 + 1) + "/" + year1),
                        todayYear, todayMonth, todayDayOfMonth);
                datePickerDialog.getDatePicker();
                datePickerDialog.show();

            });
            Button btnSubmit = findViewById(R.id.btn_submit);
            btnSubmit.setOnClickListener(view -> {
                dataValidation2();
            });
        } catch (Exception exception) {
            Toast.makeText(SelectedVid.this, exception.getMessage(), Toast.LENGTH_LONG).show();

            Button btnSubmit = findViewById(R.id.btn_submit);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!batchNo.getText().toString().isEmpty()) {
                        if (vaccines == null) {
                            DocumentReference newVacRef = db.collection("New Batch").document();
                            vaccines = new Vaccines();
                            vaccines.setVaccineID(newVacRef.getId());
                            vaccines.setBatchNo(batchNo.getText().toString());
                            vaccines.setExpiryDate(expDate.getText().toString());
                            vaccines.setQuantityAvailable(qtyAvail.getText().toString());
                            vaccines.setQuantityAdministered(qtyAdmin.getText().toString());

                            db.collection("New Batch")
                                    .document(newVacRef.getId())
                                    .set(vaccines)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(SelectedVid.this, "Successfully added!", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener((new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SelectedVid.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }));
                        } else {
                            DocumentReference vacRef = db.collection("New Batch")
                                    .document(vaccines.getVaccineID());
                            vacRef.update(
                                    "batchNo", batchNo.getText().toString(),
                                    "expDate", expDate.getText().toString(),
                                    "qtyAvail", qtyAvail.getText().toString(),
                                    "qtyAdmin", qtyAdmin.getText().toString())
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
        }
    }

    private void dataValidation2() {
        if(batchNo.getText() == null){
            isBatchNoValid = false;
        } else{
            isBatchNoValid = true;
        }
        if(expDate.getText() == null){
            isDateValid = false;
        } else{
            isDateValid = true;
        }
        if(qtyAvail.getText() == null){
            isQtyAvailVaild = false;
        } else{
            isQtyAvailVaild = true;
        }
        if(qtyAdmin.getText() == null){
            isQtyAdminValid = false;
        } else{
            isQtyAdminValid = true;
        }

        if( isBatchNoValid && isDateValid && isQtyAvailVaild && isQtyAdminValid) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage("New Batch has been recorded");
            adb.setCancelable(true);
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog ad = adb.create();
            ad.setTitle("Batch Added");
            ad.show();
        } else{
            Snackbar.make(findViewById(R.id.constraint_layout_vaccination),"Field Empty!", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
        }
    }
}
