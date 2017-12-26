/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.model;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of setups. This is used for saving the
 * list of setups to XML.
 * 
 * @
 */
@XmlRootElement(name = "setups")
public class SetupListWrapper {

    private List<Setup> setups;

    @XmlElement(name = "setup")
    public List<Setup> getSetups() {
        return setups;
    }

    public void setSetups(List<Setup> setups) {
        this.setups = setups;
    }
}
