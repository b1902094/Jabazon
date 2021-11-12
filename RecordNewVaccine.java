
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RecordNewVaccine extends AppCompatActivity {

    ImageButton imgBtnCal;
    EditText et_vacId, et_vacName, et_vacMf, et_batchNum, et_expDate, et_qtyAdmin, et_qtyAvail;
    TextView tv_vacId, tv_vacName, tv_vacMf, tv_batchNum, tv_expDate, tv_qtyAdmin, tv_qtyAvail;
    boolean isIDvalid, isNameValid, isMfValid, isBatchNumValid, isExpDate3Valid, isQtyAdminValid, isQtyAvailValid;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Vaccines vaccine;
    Button btnRecordVaccine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_new_vaccine);

        try{
            et_vacId = findViewById(R.id.et_new_vaccine_id);
            et_vacName = findViewById(R.id.et_new_vaccine_name);
            et_vacMf = findViewById(R.id.et_new_vaccine_mf);
            et_expDate = findViewById(R.id.et_new_vaccine_exp_date);
            et_batchNum = findViewById(R.id.et_new_vaccine_batch_num);
            et_qtyAdmin = findViewById(R.id.et_new_vaccine_qty_admin);
            et_qtyAvail = findViewById(R.id.et_new_vaccine_qty_avail);

            btnRecordVaccine = findViewById(R.id.btn_record_vaccine);
            btnRecordVaccine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!et_vacName.getText().toString().isEmpty()){
                        if(vaccine == null){
                            DocumentReference newVacRef = db.collection("Vaccines").document();
                            vaccine = new Vaccines();
                            vaccine.setVaccineID(et_vacId.getText().toString());
                            vaccine.setVaccineName(et_vacName.getText().toString());
                            vaccine.setManufacturer(et_vacMf.getText().toString());
                            vaccine.setBatchNo(et_batchNum.getText().toString());
                            vaccine.setExpiryDate(et_expDate.getText().toString());
                            vaccine.setQuantityAdministered(et_qtyAdmin.getText().toString());
                            vaccine.setQuantityAvailable(et_qtyAvail.getText().toString());

                            db.collection("Vaccines")
                                    .document(newVacRef.getId())
                                    .set(vaccine)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RecordNewVaccine.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else{
                            DocumentReference VacRef = db.collection("Vacciens")
                                    .document(vaccine.getVaccineName());
                            VacRef.update("vaccineId", et_vacId.getText().toString(),
                                    "vaccineName", et_vacName.getText().toString(),
                                    "manufacturer", et_vacMf.getText().toString(),
                                    "batchNum", et_batchNum.getText().toString(),
                                    "expDate", et_expDate.getText().toString(),
                                    "qtyAdministered", et_qtyAdmin.getText().toString(),
                                    "qtyAvailable", et_qtyAvail.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RecordNewVaccine.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    }
                }
            });

        } catch(Exception ex){
            Toast.makeText(RecordNewVaccine.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void dataValidation() {
        if (et_vacId.getText().toString().isEmpty()) {
            et_vacId.setError("Please fill in the field");
        } else {
            isIDvalid = true;
        }

        if (et_vacName.getText().toString().isEmpty()) {
            et_vacName.setError("PLease fill in this field");
        } else {
            isNameValid = true;
        }

        if (et_vacMf.getText().toString().isEmpty()) {
            et_vacMf.setError("PLease fill in this field");
        } else {
            isMfValid = true;
        }

        if (et_batchNum.getText().toString().isEmpty()) {
            et_batchNum.setError("PLease fill in this field");
        } else {
            isBatchNumValid = true;
        }

        if (et_expDate.getText().toString().isEmpty()) {
            et_expDate.setError("PLease fill in this field");
        } else {
            isExpDate3Valid = true;
        }

        if (et_qtyAdmin.getText().toString().isEmpty()) {
            et_qtyAdmin.setError("PLease fill in this field");
        } else {
            isQtyAdminValid = true;
        }

        if (et_qtyAvail.getText().toString().isEmpty()) {
            et_qtyAvail.setError("PLease fill in this field");
        } else {
            isQtyAvailValid = true;
        }

        if(isIDvalid && isNameValid && isMfValid && isBatchNumValid && isExpDate3Valid && isQtyAdminValid && isQtyAvailValid){
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setMessage("New Vaccine has been recorded");
            adb.setCancelable(true);
            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                }
            });
            AlertDialog ad = adb.create();
            ad.setTitle("Vaccine Recorded");
            ad.show();
        } else{
            Toast.makeText(getApplicationContext(), "Invalid/Field Empty", Toast.LENGTH_SHORT).show();
        }
    }

}
