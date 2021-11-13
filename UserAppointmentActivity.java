
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserVaccineAppointment implements Serializable {

    private String appointmentDate;
    private String batchNo;
    private String selectedHealthcare;
    private String selectedVaccine;
    private String vaccinationAppointmentID;
    private String status;
    private String userID;
    private String username;
    private String ICPassport;
    private String manufacturer;
    private String remarks;
    private String expiryDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getICPassport() {
        return ICPassport;
    }

    public void setICPassport(String ICPassport) {
        this.ICPassport = ICPassport;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String toString(){
        return vaccinationAppointmentID;
    }

}
