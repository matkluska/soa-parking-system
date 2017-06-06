package pl.edu.agh.soa.view.dashboard.account_management;

import pl.edu.agh.soa.controller.ParkingDAO;
import pl.edu.agh.soa.model.ParkingUser;
import pl.edu.agh.soa.model.UserType;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
public class AccountMB {
    private String username;
    private long userId;
    private String newPass;
    private String confirmPass;

    @Inject
    private ParkingDAO parkingDAO;

    @PostConstruct
    public void init() {
        username = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

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
        ParkingUser parkingUser = parkingDAO.findUserByName(username).orElseThrow(RuntimeException::new);
        parkingDAO.changeUserPassword(parkingUser.getId(), newPass);

        return "/employee/dashboard.xhtml";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/employee/dashboard.xhtml?faces-redirect=true";
    }

    public boolean isAdmin() {
        ParkingUser parkingUser = parkingDAO.findUserByName(username).orElseThrow(RuntimeException::new);
        return UserType.ADMIN.equals(parkingUser.getUserType());
    }
}
