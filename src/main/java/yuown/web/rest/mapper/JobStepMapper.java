package yuown.web.rest.mapper;

import yuown.domain.*;
import yuown.web.rest.dto.JobStepDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity JobStep and its DTO JobStepDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobStepMapper {

    @Mapping(source = "jobInstance.id", target = "jobInstanceId")
    @Mapping(source = "jobInstance.name", target = "jobInstanceName")
    @Mapping(source = "yesPath.id", target = "yesPathId")
    @Mapping(source = "yesPath.name", target = "yesPathName")
    @Mapping(source = "noPath.id", target = "noPathId")
    @Mapping(source = "noPath.name", target = "noPathName")
    @Mapping(source = "nextStep.id", target = "nextStepId")
    @Mapping(source = "nextStep.name", target = "nextStepName")
    JobStepDTO jobStepToJobStepDTO(JobStep jobStep);

    List<JobStepDTO> jobStepsToJobStepDTOs(List<JobStep> jobSteps);

    @Mapping(source = "jobInstanceId", target = "jobInstance")
    @Mapping(source = "yesPathId", target = "yesPath")
    @Mapping(source = "noPathId", target = "noPath")
    @Mapping(source = "nextStepId", target = "nextStep")
    JobStep jobStepDTOToJobStep(JobStepDTO jobStepDTO);

    List<JobStep> jobStepDTOsToJobSteps(List<JobStepDTO> jobStepDTOs);

    default JobInstance jobInstanceFromId(Long id) {
        if (id == null) {
            return null;
        }
        JobInstance jobInstance = new JobInstance();
        jobInstance.setId(id);
        return jobInstance;
    }

    default JobStep jobStepFromId(Long id) {
        if (id == null) {
            return null;
        }
        JobStep jobStep = new JobStep();
        jobStep.setId(id);
        return jobStep;
    }
}
