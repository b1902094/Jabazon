
import java.io.Serializable;

public class BatchItems implements Serializable{

    String batchNo;
    String vName;
    String numAppts;
    String expDate;
    String qtyAvail;
    String qtyAdmind;

    public BatchItems(String batchNo, String numAppts, String expDate, String qtyAvail, String qtyAdmind) {
        this.batchNo = batchNo;
        this.vName = vName;
        this.numAppts = numAppts;
        this.expDate = expDate;
        this.qtyAvail = qtyAvail;
        this.qtyAdmind = qtyAdmind;
    }
}
