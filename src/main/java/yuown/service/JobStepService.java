package yuown.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yuown.domain.JobInstance;
import yuown.domain.JobStep;
import yuown.repository.JobStepRepository;
import yuown.web.rest.dto.JobStepDTO;
import yuown.web.rest.mapper.JobStepMapper;

import java.util.List;

import javax.inject.Inject;

/**
 * Service Implementation for managing JobStep.
 */
@Service
@Transactional
public class JobStepService {

    private final Logger log = LoggerFactory.getLogger(JobStepService.class);
    
    @Inject
    private JobStepRepository jobStepRepository;
    
    @Inject
    private JobStepMapper jobStepMapper;
    
    /**
     * Save a jobStep.
     * 
     * @param jobStepDTO the entity to save
     * @return the persisted entity
     */
    public JobStepDTO save(JobStepDTO jobStepDTO) {
        log.debug("Request to save JobStep : {}", jobStepDTO);
        JobStep jobStep = jobStepMapper.jobStepDTOToJobStep(jobStepDTO);
        jobStep = jobStepRepository.save(jobStep);
        JobStepDTO result = jobStepMapper.jobStepToJobStepDTO(jobStep);
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
    public JobStepDTO findOne(Long id) {
        log.debug("Request to get JobStep : {}", id);
        JobStep jobStep = jobStepRepository.findOne(id);
        JobStepDTO jobStepDTO = jobStepMapper.jobStepToJobStepDTO(jobStep);
        return jobStepDTO;
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

	public List<JobStep> findAllByJobInstance(JobInstance jobInstance) {
		log.debug("Request to get all JobSteps By Job Instance");
		List<JobStep> result = jobStepRepository.findAllByJobInstance(jobInstance);
		return result;
	}
}
