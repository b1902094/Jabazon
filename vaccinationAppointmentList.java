
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class vaccinationAppointmentList extends AppCompatActivity {
    ListView listviewVaccinationAppointment;
    List<UserVaccineAppointment> vaccineAppointmentArray;
    ListAdapter adapter;
    ConstraintLayout constraintLayoutVaccinationAppointment;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_appointment_list);
        
        listviewVaccinationAppointment = findViewById(R.id.list_view_vaccination_appointmentList);
        constraintLayoutVaccinationAppointment = findViewById(R.id.layout_vaccinationAppointmentList);
        getVaccinationAppointment();

        listviewVaccinationAppointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(vaccinationAppointmentList.this, vaccinationAppointmentDetails.class);
                UserVaccineAppointment uva = vaccineAppointmentArray.get(i);
                intent.putExtra("Vaccination Appointment", uva);
                startActivity(intent);
            }
        });
    }

    private void getVaccinationAppointment() {
        db.collection("Vaccination Appointment").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    vaccineAppointmentArray = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        UserVaccineAppointment uva = document.toObject(UserVaccineAppointment.class);
                        vaccineAppointmentArray.add(uva);
                    }
                    adapter = new ArrayAdapter<UserVaccineAppointment>(vaccinationAppointmentList.this, android.R.layout.simple_list_item_1,vaccineAppointmentArray);
                    listviewVaccinationAppointment.setAdapter(adapter);
                }
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        getVaccinationAppointment();
    }
}
