
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
    Spinner healthcareChosen;
    TextView batchNoTextView;
    Spinner batchNoChosen;
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

            FirebaseFirestore db =  FirebaseFirestore.getInstance();
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
            });


        }catch(Exception ex){
            Toast.makeText(vaccinationAppointment.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    private void dataValidation(){

        if(vaccinesChosen.getSelectedItem() == null){
            isVaccineChosenValid = false;
        }
        else{
            isVaccineChosenValid = true;
        }

        if(batchNoChosen.getSelectedItem() == null){
            isBatchNoValid = false;
        }else{
            isBatchNoValid = true;
        }
        if(healthcareChosen.getSelectedItem() == null){
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
