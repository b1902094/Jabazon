

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class BatchItems implements Serializable{

    String batchNo;
    String expDate;
    String qtyAdmin;
    String qtyAvail;

    public BatchItems(){
        //empty constructor
    }
    public BatchItems(String batchNo, String expDate, String qtyAdmin, String qtyAvail) {
        this.batchNo = batchNo;
        this.expDate = expDate;
        this.qtyAvail = qtyAvail;
        this.qtyAdmin = qtyAdmin;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getQtyAdmin() {
        return qtyAdmin;
    }

    public void setQtyAdmin(String qtyAdmin) {
        this.qtyAdmin = qtyAdmin;
    }

    public String getQtyAvail() {
        return qtyAvail;
    }

    public void setQtyAvail(String qtyAvail) {
        this.qtyAvail = qtyAvail;

    }

    @Override
    public String toString() {
        return batchNo;

    }
}
