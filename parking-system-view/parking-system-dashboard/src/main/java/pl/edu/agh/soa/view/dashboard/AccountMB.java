package pl.edu.agh.soa.view.dashboard;

import pl.edu.agh.soa.controller.ParkingDAO;
import pl.edu.agh.soa.model.ParkingUser;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
public class AccountMB {
    private String newPass;
    private String confirmPass;

    @Inject
    private ParkingDAO parkingDAO;

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }

    public String changePassword() {
        String username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        ParkingUser parkingUser = parkingDAO.findUserByName(username).orElseThrow(RuntimeException::new);
        parkingDAO.changeUserPassword(parkingUser.getId(), newPass);

        return "dashboard";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }
}
