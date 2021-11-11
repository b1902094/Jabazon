
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;


import org.w3c.dom.Document;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class vaccinationAppointment extends AppCompatActivity {

    Spinner vaccinesChosen;
    ImageButton imageButtonCalendar ;
    TextView vaccinesChosenTextView;
    EditText appointmentDateDOB;
    TextView healthcareTextView;
    TextView statusTextView;
    String healthcareChosen;
    TextView batchNoTextView;
    Spinner batchNoChosen;
    UserVaccineAppointment vaxAppt;
    Object batchNumChosen, vaccineChosen;
    FirebaseFirestore db =  FirebaseFirestore.getInstance();
    boolean isVaccineChosenValid, isBatchNoValid, isHealthcareValid,isDateValid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_appointment);


        try {
            appointmentDateDOB = findViewById(R.id.edit_text_dateofAppointment);
            vaccinesChosenTextView = findViewById(R.id.text_view_select_vaccine);
            healthcareTextView = findViewById(R.id.text_view_healthcareCentre);
            batchNoTextView = findViewById(R.id.text_view_batchNo);
            statusTextView = findViewById(R.id.tv_statusChangeable);


            CollectionReference cfVac = db.collection("Vaccines");
            vaccinesChosen = findViewById(R.id.spinner_vaccinesChosen);
            List<String> vaccines = new ArrayList<>();
            ArrayAdapter<String>vaccinesArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,vaccines);
            vaccinesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vaccinesChosen.setAdapter(vaccinesArrayAdapter);
            cfVac.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document: task.getResult()){
                            String VacName = document.getString("vaccineName");
                            vaccines.add(VacName);
                        }
                        vaccinesArrayAdapter.notifyDataSetChanged();
                    }
                }
            });
            vaccinesChosen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    vaccineChosen = String.valueOf(adapterView.getItemAtPosition(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            CollectionReference cfHealthcare = db.collection("Healthcare");
            Spinner HealthcareChosen = findViewById(R.id.spinner_healthcareCentres);
            List<String> healthcare = new ArrayList<>();
            ArrayAdapter<String>healthcareArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,healthcare);
            healthcareArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            HealthcareChosen.setAdapter(healthcareArrayAdapter);
            cfHealthcare.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document: task.getResult()){
                            String HCName = document.getString("centreName");
                            healthcare.add(HCName);
                        }
                        healthcareArrayAdapter.notifyDataSetChanged();
                    }
                }
            });
            HealthcareChosen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    healthcareChosen = String.valueOf(adapterView.getItemAtPosition(i));

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            CollectionReference cfbatchNo = db.collection("Vaccines");
            Spinner batchNoChosen = findViewById(R.id.spinner_batchNo);
            List<String> batchNo = new ArrayList<>();
            ArrayAdapter<String>batchNoArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,batchNo);
            batchNoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            batchNoChosen.setAdapter(batchNoArrayAdapter);
            cfbatchNo.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document: task.getResult()){
                            String batchNumber = document.getString("batchNo");
                            batchNo.add(batchNumber);
                        }
                        batchNoArrayAdapter.notifyDataSetChanged();
                    }
                }
            });
            batchNoChosen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    batchNumChosen = String.valueOf(adapterView.getItemAtPosition(i));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            imageButtonCalendar = findViewById(R.id.image_button_calendar_icon);
            imageButtonCalendar.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                int todayYear = calendar.get(Calendar.YEAR);
                int todayMonth = calendar.get(Calendar.MONTH);
                int todayDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(vaccinationAppointment.this,
                        (view, year1, month1, dayOfMonth1) -> appointmentDateDOB.setText(dayOfMonth1 + "/" + (month1+1) + "/" + year1),
                        todayYear, todayMonth, todayDayOfMonth);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            });
            Button submitButton = findViewById(R.id.button_submit);
            submitButton.setOnClickListener(view -> {
                dataValidation();
                if(vaxAppt == null){
                    DocumentReference newVaxRef = db.collection("Vaccination Appointment").document();
                    vaxAppt = new UserVaccineAppointment();
                    vaxAppt.setBatchNo(String.valueOf(batchNumChosen));
                    vaxAppt.setSelectedVaccine(String.valueOf(vaccineChosen));
                    vaxAppt.setSelectedHealthcare(healthcareChosen);
                    vaxAppt.setAppointmentDate(appointmentDateDOB.getText().toString());
                    vaxAppt.setVaccinationAppointmentID(newVaxRef.getId());
                    vaxAppt.setStatus(statusTextView.getText().toString());
                    vaxAppt.setUserID(LoginActivity.User_ID);

                    db.collection("Vaccination Appointment")
                            .document(newVaxRef.getId())
                            .set(vaxAppt).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(vaccinationAppointment.this,"Successfully added!",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(vaccinationAppointment.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    DocumentReference vaxApptRef = db.collection("Vaccination Appointment").document(vaxAppt.getVaccinationAppointmentID());
                    vaxApptRef.update("batchNo",batchNumChosen,"vaccine"
                            ,vaccineChosen,"healthcare centre", healthcareChosen
                            , "appointment Date", appointmentDateDOB.getText().toString(),"status", statusTextView.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(vaccinationAppointment.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });


        }catch(Exception ex){
            Toast.makeText(vaccinationAppointment.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    private void dataValidation(){

        if(vaccineChosen == null){
            isVaccineChosenValid = false;
        }
        else{
            isVaccineChosenValid = true;
        }

        if(batchNumChosen == null){
            isBatchNoValid = false;
        }else{
            isBatchNoValid = true;
        }
        if(healthcareChosen == null){
            isHealthcareValid = false;
        }else{
            isHealthcareValid = true;
        }
        if(appointmentDateDOB.getText().toString().isEmpty()){
            isDateValid = false;
        }else{
            isDateValid = true;
        }

        if( isVaccineChosenValid && isBatchNoValid && isHealthcareValid && isDateValid) {
            AlertDialog.Builder ADBuilder1 = new AlertDialog.Builder(this);
            ADBuilder1.setMessage("Appointment request has been made. Please wait for confirmation.");
            ADBuilder1.setCancelable(true);
            ADBuilder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                    startActivity(intent);
                }
            });
            AlertDialog alert11 = ADBuilder1.create();
            alert11.setTitle("Request successful");
            alert11.show();
        }
        else{
            Snackbar.make(findViewById(R.id.constraint_layout_vaccination),"Please fill in all the fields !", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .setAction("Close", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
        }

    }
}
