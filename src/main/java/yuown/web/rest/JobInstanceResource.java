package yuown.web.rest;

import com.codahale.metrics.annotation.Timed;
import yuown.domain.JobInstance;
import yuown.service.JobInstanceService;
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
 * REST controller for managing JobInstance.
 */
@RestController
@RequestMapping("/api")
public class JobInstanceResource {

    private final Logger log = LoggerFactory.getLogger(JobInstanceResource.class);
        
    @Inject
    private JobInstanceService jobInstanceService;
    
    /**
     * POST  /job-instances : Create a new jobInstance.
     *
     * @param jobInstance the jobInstance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobInstance, or with status 400 (Bad Request) if the jobInstance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/job-instances",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobInstance> createJobInstance(@RequestBody JobInstance jobInstance) throws URISyntaxException {
        log.debug("REST request to save JobInstance : {}", jobInstance);
        if (jobInstance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("jobInstance", "idexists", "A new jobInstance cannot already have an ID")).body(null);
        }
        JobInstance result = jobInstanceService.save(jobInstance);
        return ResponseEntity.created(new URI("/api/job-instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobInstance", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-instances : Updates an existing jobInstance.
     *
     * @param jobInstance the jobInstance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobInstance,
     * or with status 400 (Bad Request) if the jobInstance is not valid,
     * or with status 500 (Internal Server Error) if the jobInstance couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/job-instances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobInstance> updateJobInstance(@RequestBody JobInstance jobInstance) throws URISyntaxException {
        log.debug("REST request to update JobInstance : {}", jobInstance);
        if (jobInstance.getId() == null) {
            return createJobInstance(jobInstance);
        }
        JobInstance result = jobInstanceService.save(jobInstance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobInstance", jobInstance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-instances : get all the jobInstances.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobInstances in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/job-instances",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JobInstance>> getAllJobInstances(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of JobInstances");
        Page<JobInstance> page = jobInstanceService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-instances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /job-instances/:id : get the "id" jobInstance.
     *
     * @param id the id of the jobInstance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobInstance, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/job-instances/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JobInstance> getJobInstance(@PathVariable Long id) {
        log.debug("REST request to get JobInstance : {}", id);
        JobInstance jobInstance = jobInstanceService.findOne(id);
        return Optional.ofNullable(jobInstance)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /job-instances/:id : delete the "id" jobInstance.
     *
     * @param id the id of the jobInstance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/job-instances/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJobInstance(@PathVariable Long id) {
        log.debug("REST request to delete JobInstance : {}", id);
        jobInstanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jobInstance", id.toString())).build();
    }

}
