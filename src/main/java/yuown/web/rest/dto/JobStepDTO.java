package yuown.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import yuown.domain.enumeration.StepType;
import yuown.domain.enumeration.StepStatus;

/**
 * A DTO for the JobStep entity.
 */
public class JobStepDTO implements Serializable {

    private Long id;

    private String name;


    private StepType stepType;


    private StepStatus stepStatus;


    private String message;


    private Long jobInstanceId;

    private String jobInstanceName;

    private Long yesPathId;

    private String yesPathName;

    private Long noPathId;

    private String noPathName;

    private Long nextStepId;

    private String nextStepName;

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

    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    public String getJobInstanceName() {
        return jobInstanceName;
    }

    public void setJobInstanceName(String jobInstanceName) {
        this.jobInstanceName = jobInstanceName;
    }

    public Long getYesPathId() {
        return yesPathId;
    }

    public void setYesPathId(Long jobStepId) {
        this.yesPathId = jobStepId;
    }

    public String getYesPathName() {
        return yesPathName;
    }

    public void setYesPathName(String jobStepName) {
        this.yesPathName = jobStepName;
    }

    public Long getNoPathId() {
        return noPathId;
    }

    public void setNoPathId(Long jobStepId) {
        this.noPathId = jobStepId;
    }

    public String getNoPathName() {
        return noPathName;
    }

    public void setNoPathName(String jobStepName) {
        this.noPathName = jobStepName;
    }

    public Long getNextStepId() {
        return nextStepId;
    }

    public void setNextStepId(Long jobStepId) {
        this.nextStepId = jobStepId;
    }

    public String getNextStepName() {
        return nextStepName;
    }

    public void setNextStepName(String jobStepName) {
        this.nextStepName = jobStepName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobStepDTO jobStepDTO = (JobStepDTO) o;

        if ( ! Objects.equals(id, jobStepDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobStepDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", stepType='" + stepType + "'" +
            ", stepStatus='" + stepStatus + "'" +
            ", message='" + message + "'" +
            '}';
    }
}
