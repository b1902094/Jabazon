
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class vaccinationAppointmentDetails extends AppCompatActivity {
    UserVaccineAppointment uva;
    TextView selectedPatientName;
    TextView selectedPatientICP;
    TextView selectedVaccineBatchNum;
    TextView selectedPatientVaccineName;
    TextView selectedPatientVaccineManufacturer;
    EditText selectedPatientRemarks ;
    Button confirmButton;
    Button rejectedButton;
    FirebaseFirestore db= FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_appointment_details);

        selectedPatientName =findViewById(R.id.tv_selected_patient_name);
        selectedPatientICP=findViewById(R.id.tv_selected_patient_ic_pp);
        selectedPatientVaccineName = findViewById(R.id.tv_selected_patient_vaccine_name);
        selectedVaccineBatchNum=findViewById(R.id.tv_selected_patient_batch_num);
        selectedPatientVaccineManufacturer = findViewById(R.id.tv_selected_patient_vaccine_mf);
        selectedPatientRemarks = findViewById(R.id.et_remarks);
        confirmButton = findViewById(R.id.btn_confirm_appt);
        rejectedButton = findViewById(R.id.btn_reject_appt);

        uva = (UserVaccineAppointment) getIntent().getSerializableExtra("Vaccination Appointment");

        if(uva != null){
            selectedPatientName.setText(uva.getUsername());
            selectedPatientICP.setText(uva.getICPassport());
            selectedVaccineBatchNum.setText(uva.getBatchNo());
            selectedPatientVaccineName.setText(uva.getSelectedVaccine());
            selectedPatientRemarks.getText().toString();
            selectedPatientVaccineManufacturer.setText(uva.getManufacturer());
        }
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference uvaRef = db.collection("Vaccination Appointment")
                        .document(uva.getVaccinationAppointmentID());
                uvaRef.update("status", "Appointment Made! ",
                        "remarks", "none")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG",""+uva.getStatus());
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(vaccinationAppointmentDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        rejectedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference uvaRef = db.collection("Vaccines")
                        .document(uva.getVaccinationAppointmentID());
                uvaRef.update("status", "Appointment failed ",
                        "remarks", selectedPatientRemarks.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                if(selectedPatientRemarks.getText().toString().isEmpty()){
                                    selectedPatientRemarks.requestFocus();
                                }
                                else{
                                    finish();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(vaccinationAppointmentDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}
