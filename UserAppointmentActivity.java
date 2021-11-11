
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserVaccineAppointment {

    private String appointmentDate;
    private String batchNo;
    private String selectedHealthcare;
    private String selectedVaccine;
    private String vaccinationAppointmentID;
    private String status;
    private String userID;

    public UserVaccineAppointment(){

    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate){
        DateFormat sdf = SimpleDateFormat.getDateInstance();
        this.appointmentDate =sdf.format(new Date());
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getSelectedHealthcare() {
        return selectedHealthcare;
    }

    public void setSelectedHealthcare(String selectedHealthcare) {
        this.selectedHealthcare = selectedHealthcare;
    }

    public String getSelectedVaccine() {
        return selectedVaccine;
    }

    public void setSelectedVaccine(String selectedVaccine) {
        this.selectedVaccine = selectedVaccine;
    }

    public String getVaccinationAppointmentID() {
        return vaccinationAppointmentID;
    }

    public void setVaccinationAppointmentID(String vaccinationAppointmentID) {
        this.vaccinationAppointmentID = vaccinationAppointmentID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
