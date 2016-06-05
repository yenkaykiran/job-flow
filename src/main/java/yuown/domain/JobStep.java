package yuown.domain;


import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import yuown.domain.enumeration.StepStatus;
import yuown.domain.enumeration.StepType;

/**
 * A JobStep.
 */
@Entity
@Table(name = "job_steps")
public class JobStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_type")
    private StepType stepType;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_status")
    private StepStatus stepStatus;

    @Column(name = "message")
    private String message;

    @ManyToOne
    private JobInstance jobInstance;

    @ManyToOne
    private JobStep yesPath;

    @ManyToOne
    private JobStep noPath;

    @ManyToOne
    private JobStep nextStep;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StepType getStepType() {
        return stepType;
    }

    public void setStepType(StepType stepType) {
        this.stepType = stepType;
    }

    public StepStatus getStepStatus() {
        return stepStatus;
    }

    public void setStepStatus(StepStatus stepStatus) {
        this.stepStatus = stepStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JobInstance getJobInstance() {
        return jobInstance;
    }

    public void setJobInstance(JobInstance jobInstance) {
        this.jobInstance = jobInstance;
    }

    public JobStep getYesPath() {
        return yesPath;
    }

    public void setYesPath(JobStep jobStep) {
        this.yesPath = jobStep;
    }

    public JobStep getNoPath() {
        return noPath;
    }

    public void setNoPath(JobStep jobStep) {
        this.noPath = jobStep;
    }

    public JobStep getNextStep() {
        return nextStep;
    }

    public void setNextStep(JobStep jobStep) {
        this.nextStep = jobStep;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobStep jobStep = (JobStep) o;
        if(jobStep.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, jobStep.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobStep{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", stepType='" + stepType + "'" +
            ", stepStatus='" + stepStatus + "'" +
            ", message='" + message + "'" +
            '}';
    }
}
