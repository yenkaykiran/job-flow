package yuown.repository;

import yuown.domain.JobInstance;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the JobInstance entity.
 */
public interface JobInstanceRepository extends JpaRepository<JobInstance,Long> {

}
