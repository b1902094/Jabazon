
import java.io.Serializable;

public class Healthcare implements Serializable {

    private String centreName;
    private String centreAddress;

    public String getCentreName() {
        return centreName;
    }

    public void setCentreName(String centreName) {
        this.centreName = centreName;
    }

    public String getCentreAddress() {
        return centreAddress;
    }

    public void setCentreAddress(String centreAddress) {
        this.centreAddress = centreAddress;
    }

    @Override
    public String toString() {
        return "Healthcare{" +
                "centreName='" + centreName + '\'' +
                ", centreAddress='" + centreAddress + '\'' +
                '}';
    }
}
