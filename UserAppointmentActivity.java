
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserVaccineAppointment {

    private final String appointmentDate;
    private String username;
    private String email;
    private String phoneNo;
    private String ICPassport;
    private String otherHealthProblems;


    public UserVaccineAppointment(){
        DateFormat sdf = SimpleDateFormat.getDateInstance();
        this.appointmentDate =sdf.format(new Date());

    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getICPassport() {
        return ICPassport;
    }

    public void setICPassport(String ICPassport) {
        this.ICPassport = ICPassport;
    }

    public String getOtherHealthProblems() {
        return otherHealthProblems;
    }

    public void setOtherHealthProblems(String otherHealthProblems) {
        this.otherHealthProblems = otherHealthProblems;
    }
}
