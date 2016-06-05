package yuown.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import yuown.domain.JobInstance;
import yuown.domain.JobStep;
import yuown.service.JobInstanceService;
import yuown.service.JobStepService;
import yuown.web.rest.dto.JobStepDTO;
import yuown.web.rest.mapper.JobStepMapper;
import yuown.web.rest.util.HeaderUtil;
import yuown.web.rest.util.PaginationUtil;

import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for managing JobStep.
 */
@RestController
@RequestMapping("/api")
public class JobStepResource {

    private final Logger log = LoggerFactory.getLogger(JobStepResource.class);
        
    @Inject
    private JobStepService jobStepService;
    
    @Inject
    private JobInstanceService jobInstanceService;
    
    @Inject
    private JobStepMapper jobStepMapper;
    
    /**
     * POST  /job-steps : Create a new jobStep.
     *
     * @param jobStepDTO the jobStepDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobStepDTO, or with status 400 (Bad Request) if the jobStep has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/job-steps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobStepDTO> createJobStep(@RequestBody JobStepDTO jobStepDTO) throws URISyntaxException {
        log.debug("REST request to save JobStep : {}", jobStepDTO);
        if (jobStepDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("jobStep", "idexists", "A new jobStep cannot already have an ID")).body(null);
        }
        JobStepDTO result = jobStepService.save(jobStepDTO);
        return ResponseEntity.created(new URI("/api/job-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobStep", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-steps : Updates an existing jobStep.
     *
     * @param jobStepDTO the jobStepDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobStepDTO,
     * or with status 400 (Bad Request) if the jobStepDTO is not valid,
     * or with status 500 (Internal Server Error) if the jobStepDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/job-steps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobStepDTO> updateJobStep(@RequestBody JobStepDTO jobStepDTO) throws URISyntaxException {
        log.debug("REST request to update JobStep : {}", jobStepDTO);
        if (jobStepDTO.getId() == null) {
            return createJobStep(jobStepDTO);
        }
        JobStepDTO result = jobStepService.save(jobStepDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobStep", jobStepDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-steps : get all the jobSteps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobSteps in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/job-steps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
	public ResponseEntity<List<JobStepDTO>> getAllJobSteps(@RequestParam(name = "instance", required = false) Long instanceId, Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of JobSteps");
        Page<JobStep>  page = null;
        if(null != instanceId && instanceId > 0) {
        	JobInstance jobInstance = jobInstanceService.findOne(instanceId);
        	page = jobStepService.findAllByJobInstance(jobInstance, pageable);
        } else {
        	page = jobStepService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-steps");
        return new ResponseEntity<>(jobStepMapper.jobStepsToJobStepDTOs(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /job-steps/:id : get the "id" jobStep.
     *
     * @param id the id of the jobStepDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobStepDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/job-steps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobStepDTO> getJobStep(@PathVariable Long id) {
        log.debug("REST request to get JobStep : {}", id);
        JobStepDTO jobStepDTO = jobStepService.findOne(id);
        return Optional.ofNullable(jobStepDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /job-steps/:id : delete the "id" jobStep.
     *
     * @param id the id of the jobStepDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/job-steps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJobStep(@PathVariable Long id) {
        log.debug("REST request to delete JobStep : {}", id);
        jobStepService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jobStep", id.toString())).build();
    }

}
