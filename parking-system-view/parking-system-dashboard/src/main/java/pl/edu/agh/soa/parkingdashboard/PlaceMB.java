package pl.edu.agh.soa.parkingdashboard;

import javax.faces.bean.ManagedBean;

/**
 * @author Mateusz Kluska
 */
@ManagedBean
public class PlaceMB {
    private long id;
    private boolean isBusy;
    private boolean isPaid;

    public PlaceMB() {
    }

    public PlaceMB(long id, boolean isBusy, boolean isPaid) {
        this.id = id;
        this.isBusy = isBusy;
        this.isPaid = isPaid;
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
