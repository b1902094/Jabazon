
import java.io.Serializable;

public class VaccineItems implements Serializable {

    String vID;
    String vName;
    String manuFacturer;

    public VaccineItems(String vID, String vName, String manuFacturer){
        this.vID = vID;
        this.vName = vName;
        this.manuFacturer = manuFacturer;
    }
}
