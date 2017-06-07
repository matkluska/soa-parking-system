package pl.edu.agh.soa.view.dashboard;

import javax.faces.bean.ManagedBean;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
public class PlaceMB {
    private long id;
    private boolean isBusy;
    private boolean isPaid;
    private boolean toCheck;

    public PlaceMB() {
    }

    public PlaceMB(long id, boolean isBusy, boolean isPaid, boolean toCheck) {
        this.id = id;
        this.isBusy = isBusy;
        this.isPaid = isPaid;
        this.toCheck = toCheck;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isToCheck() {
        return toCheck;
    }

    public void setToCheck(boolean toCheck) {
        this.toCheck = toCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceMB placeMB = (PlaceMB) o;

        return id == placeMB.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
