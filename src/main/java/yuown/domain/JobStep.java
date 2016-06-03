package yuown.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import yuown.domain.enumeration.StepType;

import yuown.domain.enumeration.StepStatus;

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
    private JobStep jobStep;

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

    public JobStep getJobStep() {
        return jobStep;
    }

    public void setJobStep(JobStep jobStep) {
        this.jobStep = jobStep;
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
