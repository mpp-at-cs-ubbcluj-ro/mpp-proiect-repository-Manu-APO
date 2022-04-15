package competition;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class ParticipationDTO implements HasId<Long>, Serializable {

    private Long id;
    private Long participantId;
    private Long trialId;
    private Date dateOfSubmission;
    private Long registryId;

    public ParticipationDTO(Long participantId, Long trialId, Date dateOfSubmission, Long registryId) {
        this.participantId = participantId;
        this.trialId = trialId;
        this.dateOfSubmission = dateOfSubmission;
        this.registryId = registryId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long newId) {
        this.id = newId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Long getTrialId() {
        return trialId;
    }

    public void setTrialId(Long trialId) {
        this.trialId = trialId;
    }

    public Date getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void setDateOfSubmission(Date dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    public Long getRegistryId() {
        return registryId;
    }

    public void setRegistryId(Long registryId) {
        this.registryId = registryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Participation)) return false;
        Participation that = (Participation) o;
        return getId().equals(that.getId()) && getParticipantId().equals(that.getParticipant()) && getTrialId().equals(that.getTrial());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getParticipantId(), getTrialId());
    }

    @Override
    public String toString() {
        return "Participation{" +
                "id=" + id +
                ", participant=" + participantId +
                ", trial=" + trialId +
                ", dateOfSubmission=" + dateOfSubmission +
                ", registry=" + registryId +
                '}';
    }
}
