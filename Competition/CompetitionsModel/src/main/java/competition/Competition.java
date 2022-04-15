package competition;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Competition implements HasId<Long>, Serializable {

    public Long id;
    private Date startDate;
    private Date endDate;


    public Competition(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long newId) {
        this.id = newId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Competition)) return false;
        Competition that = (Competition) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getStartDate(), that.getStartDate()) && Objects.equals(getEndDate(), that.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStartDate(), getEndDate());
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
