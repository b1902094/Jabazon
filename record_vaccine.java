package com.yashika.adminhomepage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Calendar;

public class record_vaccine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_vaccine);

        try{
            EditText editTextVacId = findViewById(R.id.edit_text_vaccineID);
            EditText editTextVacName = findViewById(R.id.edit_text_vaccineName);
            EditText editTextVacManufacturer = findViewById(R.id.edit_text_vaccineManufacturer);
            EditText editTextVacBatchNo = findViewById(R.id.edit_text_vaccineBatchNo);
            EditText editTextExpDate = findViewById(R.id.edit_text_vaccineExpDate);
            EditText editTextQtyAvail = findViewById(R.id.edit_text_vaccineQtyAvail);
            EditText editTextQtyAdmin = findViewById(R.id.edit_text_vaccineQtyAdmin);

            ImageButton imgBtnHome = findViewById(R.id.image_btn_home);
            ImageButton imgBtnCalendar = findViewById(R.id.image_btn_calendar);
            imgBtnCalendar.setOnClickListener(v -> {

                Calendar calendar = Calendar.getInstance();
                int todayYear = calendar.get(Calendar.YEAR);
                int todayMonth = calendar.get(Calendar.MONTH);
                int todayDayofMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(record_vaccine.this,
                        (view, year1, month1, dayOfMonth1) -> editTextExpDate.setText(dayOfMonth1 +  "/" + month1 + "/" + year1),
                        todayYear, todayMonth, todayDayofMonth);
                datePickerDialog.show();
            });


        } catch (Exception exception){
            Toast.makeText(record_vaccine.this, exception.getMessage(), Toast.LENGTH_LONG);
        }
    }
}
