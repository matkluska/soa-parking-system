package pl.edu.agh.soa.view.dashboard.account_management;

import pl.edu.agh.soa.controller.ParkingDAO;
import pl.edu.agh.soa.model.ParkingUser;
import pl.edu.agh.soa.model.UserType;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
public class EmployeesMB {
    private List<AccountMB> accounts;

    @Inject
    private ParkingDAO parkingDAO;

    @PostConstruct
    public void init() {
        accounts = parkingDAO.findAllUsers().stream()
                .filter(u -> u.getUserType().equals(UserType.EMPLOYEE))
                .map(this::toAccountMB)
                .collect(Collectors.toList());
    }

    public List<AccountMB> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountMB> accounts) {
        this.accounts = accounts;
    }

    private AccountMB toAccountMB(ParkingUser user) {
        AccountMB accountMB = new AccountMB();
        accountMB.setUsername(user.getUsername());
        accountMB.setUserId(user.getId());
        return accountMB;
    }
}
