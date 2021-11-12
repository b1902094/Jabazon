
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    FirebaseFirestore db;

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
    }
}
