package com.yashika.jabazon_admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
            ImageButton imgBtnCalendar = findViewById(R.id.img_btn_calendar);
            ImageButton imgBtnAdd = findViewById(R.id.action_btn_add);
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

            imgBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(record_vaccine.this, "Vaccine Recorded",Toast.LENGTH_SHORT);
                }
            });

            imgBtnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(record_vaccine.this, admin_activity.class);
                    startActivity(intent);
                }
            });


        } catch (Exception exception){
            Toast.makeText(record_vaccine.this, exception.getMessage(), Toast.LENGTH_LONG);
        }

    }
}
