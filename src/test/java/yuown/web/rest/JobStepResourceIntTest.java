package yuown.web.rest;

import yuown.JobflowApp;
import yuown.domain.JobStep;
import yuown.repository.JobStepRepository;
import yuown.service.JobStepService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import yuown.domain.enumeration.StepType;
import yuown.domain.enumeration.StepStatus;

/**
 * Test class for the JobStepResource REST controller.
 *
 * @see JobStepResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JobflowApp.class)
@WebAppConfiguration
@IntegrationTest
public class JobStepResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final StepType DEFAULT_STEP_TYPE = StepType.START;
    private static final StepType UPDATED_STEP_TYPE = StepType.END;

    private static final StepStatus DEFAULT_STEP_STATUS = StepStatus.PAST;
    private static final StepStatus UPDATED_STEP_STATUS = StepStatus.FUTURE;
    private static final String DEFAULT_MESSAGE = "AAAAA";
    private static final String UPDATED_MESSAGE = "BBBBB";

    @Inject
    private JobStepRepository jobStepRepository;

    @Inject
    private JobStepService jobStepService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJobStepMockMvc;

    private JobStep jobStep;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobStepResource jobStepResource = new JobStepResource();
        ReflectionTestUtils.setField(jobStepResource, "jobStepService", jobStepService);
        this.restJobStepMockMvc = MockMvcBuilders.standaloneSetup(jobStepResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jobStep = new JobStep();
        jobStep.setName(DEFAULT_NAME);
        jobStep.setStepType(DEFAULT_STEP_TYPE);
        jobStep.setStepStatus(DEFAULT_STEP_STATUS);
        jobStep.setMessage(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void createJobStep() throws Exception {
        int databaseSizeBeforeCreate = jobStepRepository.findAll().size();

        // Create the JobStep

        restJobStepMockMvc.perform(post("/api/job-steps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobStep)))
                .andExpect(status().isCreated());

        // Validate the JobStep in the database
        List<JobStep> jobSteps = jobStepRepository.findAll();
        assertThat(jobSteps).hasSize(databaseSizeBeforeCreate + 1);
        JobStep testJobStep = jobSteps.get(jobSteps.size() - 1);
        assertThat(testJobStep.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobStep.getStepType()).isEqualTo(DEFAULT_STEP_TYPE);
        assertThat(testJobStep.getStepStatus()).isEqualTo(DEFAULT_STEP_STATUS);
        assertThat(testJobStep.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllJobSteps() throws Exception {
        // Initialize the database
        jobStepRepository.saveAndFlush(jobStep);

        // Get all the jobSteps
        restJobStepMockMvc.perform(get("/api/job-steps?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jobStep.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].stepType").value(hasItem(DEFAULT_STEP_TYPE.toString())))
                .andExpect(jsonPath("$.[*].stepStatus").value(hasItem(DEFAULT_STEP_STATUS.toString())))
                .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())));
    }

    @Test
    @Transactional
    public void getJobStep() throws Exception {
        // Initialize the database
        jobStepRepository.saveAndFlush(jobStep);

        // Get the jobStep
        restJobStepMockMvc.perform(get("/api/job-steps/{id}", jobStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jobStep.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.stepType").value(DEFAULT_STEP_TYPE.toString()))
            .andExpect(jsonPath("$.stepStatus").value(DEFAULT_STEP_STATUS.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobStep() throws Exception {
        // Get the jobStep
        restJobStepMockMvc.perform(get("/api/job-steps/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobStep() throws Exception {
        // Initialize the database
        jobStepService.save(jobStep);

        int databaseSizeBeforeUpdate = jobStepRepository.findAll().size();

        // Update the jobStep
        JobStep updatedJobStep = new JobStep();
        updatedJobStep.setId(jobStep.getId());
        updatedJobStep.setName(UPDATED_NAME);
        updatedJobStep.setStepType(UPDATED_STEP_TYPE);
        updatedJobStep.setStepStatus(UPDATED_STEP_STATUS);
        updatedJobStep.setMessage(UPDATED_MESSAGE);

        restJobStepMockMvc.perform(put("/api/job-steps")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedJobStep)))
                .andExpect(status().isOk());

        // Validate the JobStep in the database
        List<JobStep> jobSteps = jobStepRepository.findAll();
        assertThat(jobSteps).hasSize(databaseSizeBeforeUpdate);
        JobStep testJobStep = jobSteps.get(jobSteps.size() - 1);
        assertThat(testJobStep.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobStep.getStepType()).isEqualTo(UPDATED_STEP_TYPE);
        assertThat(testJobStep.getStepStatus()).isEqualTo(UPDATED_STEP_STATUS);
        assertThat(testJobStep.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void deleteJobStep() throws Exception {
        // Initialize the database
        jobStepService.save(jobStep);

        int databaseSizeBeforeDelete = jobStepRepository.findAll().size();

        // Get the jobStep
        restJobStepMockMvc.perform(delete("/api/job-steps/{id}", jobStep.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JobStep> jobSteps = jobStepRepository.findAll();
        assertThat(jobSteps).hasSize(databaseSizeBeforeDelete - 1);
    }
}
