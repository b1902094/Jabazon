
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;


import java.util.Calendar;

public class vaccinationAppointment extends AppCompatActivity {
    Spinner spinnerHealthProblems1;
    Spinner spinnerHealthProblems2;
    Spinner vaccinesChosen;
    ImageButton imageButtonCalendar ;
    EditText edittextName;
    EditText phoneNo;
    EditText ICPassport;
    EditText appointmentDateDOB;
    EditText editTextEmail;
    EditText otherHealthProblems;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[com]+";
    boolean isUsernameValid,isEmailValid,isPhoneNoValid,isICPassportValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_appointment);

        try {
            appointmentDateDOB = findViewById(R.id.edit_text_dateofAppointment);
            edittextName = findViewById(R.id.edit_text_name);
            editTextEmail = findViewById(R.id.edit_text_email);
            phoneNo = findViewById(R.id.edit_text_contactNumber);
            ICPassport = findViewById(R.id.edit_text_ICPassport);
            otherHealthProblems=findViewById(R.id.edit_text_other_health_problems);

            spinnerHealthProblems1 = findViewById(R.id.spinner_healthproblem1);
            ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.health_problems, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerHealthProblems1.setAdapter(adapter);

            spinnerHealthProblems2 = findViewById(R.id.spinner_healthproblem2);
            spinnerHealthProblems2.setAdapter(adapter);

            vaccinesChosen = findViewById(R.id.spinner_vaccinesChosen);
            ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.vaccines, android.R.layout.simple_spinner_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            vaccinesChosen.setAdapter(adapter1);

            imageButtonCalendar = findViewById(R.id.image_button_calendar_icon);
            imageButtonCalendar.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                int todayYear = calendar.get(Calendar.YEAR);
                int todayMonth = calendar.get(Calendar.MONTH);
                int todayDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(vaccinationAppointment.this,
                        (view, year1, month1, dayOfMonth1) -> appointmentDateDOB.setText(dayOfMonth1 + "/" + month1 + "/" + year1),
                        todayYear, todayMonth, todayDayOfMonth);
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
        if(edittextName.getText().toString().isEmpty()){
            edittextName.setError("Please fill in the field");
            isUsernameValid = false;
        }else{
            isUsernameValid = true;
        }
        if(editTextEmail.getText().toString().isEmpty()){
            editTextEmail.setError("Please fill in the field");
            isEmailValid = false;
        }else if(!(editTextEmail.getText().toString()).matches(emailPattern)){
            editTextEmail.setError("Please enter a valid email.");
            isEmailValid = false;
        }else{
            isEmailValid = true;
        }
        if(phoneNo.getText().toString().isEmpty()){
            phoneNo.setError("Please fill in the field");
            isPhoneNoValid = false;
        }else{
            isPhoneNoValid = true;
        }
        if(ICPassport.getText().toString().isEmpty()){
            ICPassport.setError("Please fill in the field");
            isICPassportValid = false;
        }else{
            isICPassportValid = true;
        }
        if(isUsernameValid && isEmailValid && isPhoneNoValid && isICPassportValid ) {
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
            alert11.show();
        }
        else{
            Toast.makeText(getApplicationContext(),"Please fill in all the information!",Toast.LENGTH_LONG).show();
        }
    }
}
