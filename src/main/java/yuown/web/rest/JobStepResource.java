package yuown.web.rest;

import com.codahale.metrics.annotation.Timed;
import yuown.domain.JobStep;
import yuown.service.JobStepService;
import yuown.web.rest.util.HeaderUtil;
import yuown.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JobStep.
 */
@RestController
@RequestMapping("/api")
public class JobStepResource {

    private final Logger log = LoggerFactory.getLogger(JobStepResource.class);
        
    @Inject
    private JobStepService jobStepService;
    
    /**
     * POST  /job-steps : Create a new jobStep.
     *
     * @param jobStep the jobStep to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobStep, or with status 400 (Bad Request) if the jobStep has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/job-steps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobStep> createJobStep(@RequestBody JobStep jobStep) throws URISyntaxException {
        log.debug("REST request to save JobStep : {}", jobStep);
        if (jobStep.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("jobStep", "idexists", "A new jobStep cannot already have an ID")).body(null);
        }
        JobStep result = jobStepService.save(jobStep);
        return ResponseEntity.created(new URI("/api/job-steps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobStep", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-steps : Updates an existing jobStep.
     *
     * @param jobStep the jobStep to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobStep,
     * or with status 400 (Bad Request) if the jobStep is not valid,
     * or with status 500 (Internal Server Error) if the jobStep couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/job-steps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobStep> updateJobStep(@RequestBody JobStep jobStep) throws URISyntaxException {
        log.debug("REST request to update JobStep : {}", jobStep);
        if (jobStep.getId() == null) {
            return createJobStep(jobStep);
        }
        JobStep result = jobStepService.save(jobStep);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobStep", jobStep.getId().toString()))
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
    public ResponseEntity<List<JobStep>> getAllJobSteps(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of JobSteps");
        Page<JobStep> page = jobStepService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-steps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /job-steps/:id : get the "id" jobStep.
     *
     * @param id the id of the jobStep to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobStep, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/job-steps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobStep> getJobStep(@PathVariable Long id) {
        log.debug("REST request to get JobStep : {}", id);
        JobStep jobStep = jobStepService.findOne(id);
        return Optional.ofNullable(jobStep)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /job-steps/:id : delete the "id" jobStep.
     *
     * @param id the id of the jobStep to delete
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
