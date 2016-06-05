package yuown.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import yuown.domain.JobInstance;
import yuown.domain.JobStep;

/**
 * Spring Data JPA repository for the JobStep entity.
 */
public interface JobStepRepository extends JpaRepository<JobStep, Long> {

	Page<JobStep> findAllByJobInstance(JobInstance jobInstance, Pageable pageable);

}
