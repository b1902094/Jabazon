
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vicky.jabazon.Util.Healthcare;

public class RecordHealthcareCentre extends AppCompatActivity {

    EditText editTextCentreName;
    EditText editTextCentreAddress;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Healthcare healthcare;
    FloatingActionButton addNewHC;
    boolean isCentreNameValid, isCentreAddressValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_healthcare_centre);

        try{
            editTextCentreName = findViewById(R.id.edit_text_centreName);
            editTextCentreAddress = findViewById(R.id.edit_text_centre_address);

            addNewHC = findViewById(R.id.action_btn_add_hc);
            addNewHC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!editTextCentreName.getText().toString().isEmpty()){
                        if(healthcare == null){
                            DocumentReference newHCRef = db.collection("Healthcare").document();
                            healthcare = new Healthcare();
                            healthcare.setCentreName(editTextCentreName.getText().toString());
                            healthcare.setCentreAddress(editTextCentreAddress.getText().toString());

                            db.collection("Healthcare")
                                    .document(newHCRef.getId())
                                    .set(healthcare)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) { finish(); }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RecordHealthcareCentre.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else{
                            DocumentReference HCRef = db.collection("Healthcare")
                                    .document(healthcare.getCentreName());
                            HCRef.update("centreName", editTextCentreName.getText().toString(),
                                    "centreAddress", editTextCentreAddress.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) { finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RecordHealthcareCentre.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                }
            });
        } catch(Exception ex){
            Toast.makeText(RecordHealthcareCentre.this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private void dataValidation(){
        if (editTextCentreName.getText().toString().isEmpty()){
            editTextCentreName.setError("Please fill in the field");
        } else{
            isCentreNameValid = true;
        }

        if(editTextCentreAddress.getText().toString().isEmpty()){
            editTextCentreAddress.setError("PLease fill in this field");
        } else{
            isCentreAddressValid = true;
        }

        if(isCentreNameValid && isCentreAddressValid){
            AlertDialog.Builder ADB = new AlertDialog.Builder(this);
            ADB.setMessage("Healthcare Centre has been recorded");
            ADB.setCancelable(true);
            ADB.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            Intent intent = new Intent(getApplicationContext(), AdminActivityMain.class);
                            startActivity(intent);
                        }
                    });
            AlertDialog alert = ADB.create();
            alert.setTitle("Healthcare Centre Recorded");
            alert.show();
        } else{
            Toast.makeText(getApplicationContext(), "Invalid/field empty", Toast.LENGTH_SHORT).show();
        }
    }
}
