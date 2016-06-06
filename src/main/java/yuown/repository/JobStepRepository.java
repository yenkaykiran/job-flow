package yuown.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import yuown.domain.JobInstance;
import yuown.domain.JobStep;

import java.util.List;

/**
 * Spring Data JPA repository for the JobStep entity.
 */
public interface JobStepRepository extends JpaRepository<JobStep, Long> {

	List<JobStep> findAllByJobInstance(JobInstance jobInstance);

}
