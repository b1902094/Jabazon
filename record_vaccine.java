
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.Calendar;

public class record_vaccine extends AppCompatActivity {

    EditText editTextVacId;
    EditText editTextVacName;
    EditText editTextVacManufacturer;
    EditText editTextVacBatchNo;
    EditText editTextExpDate;
    EditText editTextQtyAvail;
    EditText editTextQtyAdmin;
    ImageButton imgBtnCalendar;
    boolean isVaccIDValid, isVaccNameValid, isManufacturerValid, isBatchValid, isDateValid, isQtyAdminValid, isQtyAvailValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_vaccine);

        try {
            editTextVacId = findViewById(R.id.edit_text_vaccineID);
            editTextVacName = findViewById(R.id.edit_text_vaccineName);
            editTextVacManufacturer = findViewById(R.id.edit_text_vaccineManufacturer);
            editTextVacBatchNo = findViewById(R.id.edit_text_vaccineBatchNo);
            editTextExpDate = findViewById(R.id.edit_text_vaccineExpDate);
            editTextQtyAvail = findViewById(R.id.edit_text_vaccineQtyAvail);
           editTextQtyAdmin = findViewById(R.id.edit_text_vaccineQtyAdmin);

            imgBtnCalendar = findViewById(R.id.image_button_calendar_icon);

            imgBtnCalendar.setOnClickListener(v -> {

                Calendar calendar = Calendar.getInstance();
                int todayYear = calendar.get(Calendar.YEAR);
                int todayMonth = calendar.get(Calendar.MONTH);
                int todayDayofMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(record_vaccine.this,
                        (view, year1, month1, dayOfMonth1) -> editTextExpDate.setText(dayOfMonth1 + "/" + month1 + "/" + year1),
                        todayYear, todayMonth, todayDayofMonth);
                datePickerDialog.show();
            });
            ImageButton imgBtnAdd = findViewById(R.id.action_btn_add);
            imgBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dataValidation();
                }
            });


        } catch (Exception ex) {
            Toast.makeText(record_vaccine.this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    private void dataValidation() {
        if (editTextVacId.getText().toString().isEmpty()) {
            editTextVacId.setError("Please fill in the field");
            isVaccIDValid = false;
        } else {
            isVaccIDValid = true;
        }

        if (editTextVacName.getText().toString().isEmpty()) {
            editTextVacName.setError("Please fill in the field");
            isVaccNameValid = false;
        } else {
            isVaccNameValid = true;
        }

        if (editTextVacManufacturer.getText().toString().isEmpty()) {
            editTextVacManufacturer.setError("Please fill in the field");
            isManufacturerValid = false;
        } else {
            isManufacturerValid = true;
        }

        if (editTextVacBatchNo.getText().toString().isEmpty()) {
            editTextVacBatchNo.setError("Please fill in the field");
            isBatchValid = false;
        } else {
            isBatchValid = true;
        }

        if (editTextExpDate.getText().toString().isEmpty()) {
            editTextExpDate.setError("Please fill in the field");
            isDateValid = false;
        } else {
            isDateValid = true;
        }

        if (editTextQtyAdmin.getText().toString().isEmpty()) {
            editTextQtyAdmin.setError("Please fill in the field");
            isQtyAdminValid = false;
        } else {
            isQtyAdminValid = true;
        }

        if (editTextQtyAvail.getText().toString().isEmpty()) {
            editTextQtyAvail.setError("Please fill in the field");
            isQtyAvailValid = false;
        } else {
            isQtyAvailValid = true;
        }

        if (isVaccIDValid && isVaccNameValid && isManufacturerValid && isBatchValid && isDateValid && isQtyAdminValid && isQtyAvailValid) {
            AlertDialog.Builder ADB = new AlertDialog.Builder(this);
            ADB.setMessage("Vaccine Recorded");
            ADB.setCancelable(true);
            ADB.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                            startActivity(intent);
                        }
                    });
            AlertDialog alert = ADB.create();
            alert.setTitle("Vaccine Recorded");
            alert.show();
        } else {
            Toast.makeText(getApplicationContext(),"Invalid/field empty",Toast.LENGTH_LONG).show();
        }
    }
}

    private void dataValidation() {
        if (editTextVacId.getText().toString().isEmpty()) {
            isVaccIDValid = false;
        } else {
            isVaccIDValid = true;
        }

        if (editTextVacName.getText().toString().isEmpty()) {
            isVaccNameValid = false;
        } else {
            isVaccNameValid = true;
        }

        if (editTextVacManufacturer.getText().toString().isEmpty()) {
            isManufacturerValid = false;
        } else {
            isManufacturerValid = true;
        }

        if (editTextVacBatchNo.getText().toString().isEmpty()) {
            isBatchValid = false;
        } else {
            isBatchValid = true;
        }

        if (editTextExpDate.getText().toString().isEmpty()) {
            isDateValid = false;
        } else {
            isDateValid = true;
        }

        if (editTextQtyAdmin.getText().toString().isEmpty()) {
            isQtyAdminValid = false;
        } else {
            isQtyAdminValid = true;
        }

        if (editTextQtyAvail.getText().toString().isEmpty()) {
            isQtyAvailValid = false;
        } else {
            isQtyAvailValid = true;
        }

        if (isVaccIDValid && isVaccNameValid && isManufacturerValid && isBatchValid && isDateValid && isQtyAdminValid && isQtyAvailValid) {
            Context context = getApplicationContext();
            AlertDialog.Builder ADB = new AlertDialog.Builder(context);
            ADB.setMessage("Vaccine Recorded");
            ADB.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), admin_activity.class);
                        }
                    });
            AlertDialog alert = ADB.create();
            alert.setTitle("Vaccine Recorded");
            alert.show();
        } else {
            Toast.makeText(record_vaccine.this, "Data invalid/Field Empty", Toast.LENGTH_LONG);
        }
    }
}

