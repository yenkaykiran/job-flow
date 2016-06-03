package yuown.service;

import yuown.domain.JobInstance;
import yuown.repository.JobInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing JobInstance.
 */
@Service
@Transactional
public class JobInstanceService {

    private final Logger log = LoggerFactory.getLogger(JobInstanceService.class);
    
    @Inject
    private JobInstanceRepository jobInstanceRepository;
    
    /**
     * Save a jobInstance.
     * 
     * @param jobInstance the entity to save
     * @return the persisted entity
     */
    public JobInstance save(JobInstance jobInstance) {
        log.debug("Request to save JobInstance : {}", jobInstance);
        JobInstance result = jobInstanceRepository.save(jobInstance);
        return result;
    }

    /**
     *  Get all the jobInstances.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<JobInstance> findAll(Pageable pageable) {
        log.debug("Request to get all JobInstances");
        Page<JobInstance> result = jobInstanceRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one jobInstance by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public JobInstance findOne(Long id) {
        log.debug("Request to get JobInstance : {}", id);
        JobInstance jobInstance = jobInstanceRepository.findOne(id);
        return jobInstance;
    }

    /**
     *  Delete the  jobInstance by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete JobInstance : {}", id);
        jobInstanceRepository.delete(id);
    }
}
