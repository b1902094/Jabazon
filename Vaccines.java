import java.io.Serializable;

public class Vaccines implements Serializable{

    private String vaccineID;
    private String vaccineName;
    private String manufacturer;
    private String batchNo;
    private String expiryDate;
    private String quantityAvailable;
    private String quantityAdministered;

    public Vaccines(){
        this.vaccineID = vaccineID;
        this.vaccineName = vaccineName;
        this.manufacturer = manufacturer;
    }

    public String getVaccineID() {
        return vaccineID;
    }

    public void setVaccineID(String vaccineID) {
        this.vaccineID = vaccineID;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(String quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public String getQuantityAdministered() {
        return quantityAdministered;
    }

    public void setQuantityAdministered(String quantityAdministered) {
        this.quantityAdministered = quantityAdministered;
    }

    @Override
    public String toString() {
        return vaccineName;
    }
}
