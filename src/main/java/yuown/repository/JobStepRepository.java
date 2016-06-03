package yuown.repository;

import yuown.domain.JobStep;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JobStep entity.
 */
public interface JobStepRepository extends JpaRepository<JobStep,Long> {

}
