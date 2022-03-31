package competition;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Participation implements HasId<Long>, Serializable {

    private Long id;
    private Participant participant;
    private Trial trial;
    private Date dateOfSubmission;
    private Registry registry;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long newId) {
        this.id = newId;
    }

    public Participation(Participant participant, Trial trial, Date dateOfSubmission, Registry registry) {
        this.participant = participant;
        this.trial = trial;
        this.dateOfSubmission = dateOfSubmission;
        this.registry = registry;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Trial getTrial() {
        return trial;
    }

    public void setTrial(Trial trial) {
        this.trial = trial;
    }

    public Date getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void setDateOfSubmission(Date dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    public Registry getRegistry() {
        return registry;
    }

    public void setRegistry(Registry registry) {
        this.registry = registry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participation)) return false;
        Participation that = (Participation) o;
        return getId().equals(that.getId()) && getParticipant().equals(that.getParticipant()) && getTrial().equals(that.getTrial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getParticipant(), getTrial());
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id=" + id +
                ", participant=" + participant +
                ", trial=" + trial +
                ", dateOfSubmission=" + dateOfSubmission +
                ", registry=" + registry +
                '}';
    }
}
