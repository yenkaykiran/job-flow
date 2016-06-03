package yuown.service;

import yuown.domain.JobStep;
import yuown.repository.JobStepRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing JobStep.
 */
@Service
@Transactional
public class JobStepService {

    private final Logger log = LoggerFactory.getLogger(JobStepService.class);
    
    @Inject
    private JobStepRepository jobStepRepository;
    
    /**
     * Save a jobStep.
     * 
     * @param jobStep the entity to save
     * @return the persisted entity
     */
    public JobStep save(JobStep jobStep) {
        log.debug("Request to save JobStep : {}", jobStep);
        JobStep result = jobStepRepository.save(jobStep);
        return result;
    }

    /**
     *  Get all the jobSteps.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<JobStep> findAll(Pageable pageable) {
        log.debug("Request to get all JobSteps");
        Page<JobStep> result = jobStepRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one jobStep by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public JobStep findOne(Long id) {
        log.debug("Request to get JobStep : {}", id);
        JobStep jobStep = jobStepRepository.findOne(id);
        return jobStep;
    }

    /**
     *  Delete the  jobStep by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete JobStep : {}", id);
        jobStepRepository.delete(id);
    }
}
