package blog.springrepo.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEFAULTVALUES")
public class DefaultValues implements  Serializable{

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long abdID;
    @Column(name = "SWITCHOFF")
    private String switchOff;

    
    public long getAbdID() {
        return abdID;
    }

    /**
     * @param abdID
     *            the abdID to set
     */
    public void setAbdID(long abdID) {
        this.abdID = abdID;
    }

    /**
     * @return the switchOff
     */
    public String getSwitchOff() {
        return switchOff;
    }

    /**
     * @param switchOff
     *            the switchOff to set
     */
    public void setSwitchOff(String switchOff) {
        this.switchOff = switchOff;
    }

}
