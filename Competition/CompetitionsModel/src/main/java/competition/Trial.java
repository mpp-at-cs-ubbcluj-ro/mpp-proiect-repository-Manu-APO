package competition;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Trial implements HasId<Long>, Serializable {

    private Long id;
    private Competition parentCompetition;
    private int maxNumberOfParticipants;
    private TRIAL_TYPE trialType;
    private AGE_CATEGORY ageCategory;

    private Date startDate;
    private Date endDate;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long newId) {
        this.id = newId;
    }

    public Trial(Competition parentCompetition, int maxNumberOfParticipants, TRIAL_TYPE trialType, AGE_CATEGORY ageCategory, Date startDate, Date endDate) {
        this.parentCompetition = parentCompetition;
        this.maxNumberOfParticipants = maxNumberOfParticipants;
        this.trialType = trialType;
        this.ageCategory = ageCategory;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Competition getParentCompetition() {
        return parentCompetition;
    }

    public void setParentCompetition(Competition parentCompetition) {
        this.parentCompetition = parentCompetition;
    }

    public int getMaxNumberOfParticipants() {
        return maxNumberOfParticipants;
    }

    public void setMaxNumberOfParticipants(int maxNumberOfParticipants) {
        this.maxNumberOfParticipants = maxNumberOfParticipants;
    }

    public TRIAL_TYPE getTrialType() {
        return trialType;
    }

    public void setTrialType(TRIAL_TYPE trialType) {
        this.trialType = trialType;
    }

    public AGE_CATEGORY getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AGE_CATEGORY ageCategory) {
        this.ageCategory = ageCategory;
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
        if (!(o instanceof Trial)) return false;
        Trial trial = (Trial) o;
        return getParentCompetition().equals(trial.getParentCompetition()) && getTrialType() == trial.getTrialType() && getAgeCategory() == trial.getAgeCategory();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getParentCompetition(), getTrialType(), getAgeCategory());
    }

    @Override
    public String toString() {
        return "Trial{" +
                "id=" + id +
                ", parentCompetition=" + parentCompetition +
                ", maxNumberOfParticipants=" + maxNumberOfParticipants +
                ", trialType=" + trialType +
                ", ageCategory=" + ageCategory +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
