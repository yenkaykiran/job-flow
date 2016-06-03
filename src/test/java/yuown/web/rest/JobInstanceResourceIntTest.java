package yuown.web.rest;

import yuown.JobflowApp;
import yuown.domain.JobInstance;
import yuown.repository.JobInstanceRepository;
import yuown.service.JobInstanceService;

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


/**
 * Test class for the JobInstanceResource REST controller.
 *
 * @see JobInstanceResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JobflowApp.class)
@WebAppConfiguration
@IntegrationTest
public class JobInstanceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    @Inject
    private JobInstanceRepository jobInstanceRepository;

    @Inject
    private JobInstanceService jobInstanceService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restJobInstanceMockMvc;

    private JobInstance jobInstance;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        JobInstanceResource jobInstanceResource = new JobInstanceResource();
        ReflectionTestUtils.setField(jobInstanceResource, "jobInstanceService", jobInstanceService);
        this.restJobInstanceMockMvc = MockMvcBuilders.standaloneSetup(jobInstanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        jobInstance = new JobInstance();
        jobInstance.setName(DEFAULT_NAME);
        jobInstance.setEnabled(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void createJobInstance() throws Exception {
        int databaseSizeBeforeCreate = jobInstanceRepository.findAll().size();

        // Create the JobInstance

        restJobInstanceMockMvc.perform(post("/api/job-instances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(jobInstance)))
                .andExpect(status().isCreated());

        // Validate the JobInstance in the database
        List<JobInstance> jobInstances = jobInstanceRepository.findAll();
        assertThat(jobInstances).hasSize(databaseSizeBeforeCreate + 1);
        JobInstance testJobInstance = jobInstances.get(jobInstances.size() - 1);
        assertThat(testJobInstance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testJobInstance.isEnabled()).isEqualTo(DEFAULT_ENABLED);
    }

    @Test
    @Transactional
    public void getAllJobInstances() throws Exception {
        // Initialize the database
        jobInstanceRepository.saveAndFlush(jobInstance);

        // Get all the jobInstances
        restJobInstanceMockMvc.perform(get("/api/job-instances?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(jobInstance.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())));
    }

    @Test
    @Transactional
    public void getJobInstance() throws Exception {
        // Initialize the database
        jobInstanceRepository.saveAndFlush(jobInstance);

        // Get the jobInstance
        restJobInstanceMockMvc.perform(get("/api/job-instances/{id}", jobInstance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(jobInstance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingJobInstance() throws Exception {
        // Get the jobInstance
        restJobInstanceMockMvc.perform(get("/api/job-instances/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobInstance() throws Exception {
        // Initialize the database
        jobInstanceService.save(jobInstance);

        int databaseSizeBeforeUpdate = jobInstanceRepository.findAll().size();

        // Update the jobInstance
        JobInstance updatedJobInstance = new JobInstance();
        updatedJobInstance.setId(jobInstance.getId());
        updatedJobInstance.setName(UPDATED_NAME);
        updatedJobInstance.setEnabled(UPDATED_ENABLED);

        restJobInstanceMockMvc.perform(put("/api/job-instances")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedJobInstance)))
                .andExpect(status().isOk());

        // Validate the JobInstance in the database
        List<JobInstance> jobInstances = jobInstanceRepository.findAll();
        assertThat(jobInstances).hasSize(databaseSizeBeforeUpdate);
        JobInstance testJobInstance = jobInstances.get(jobInstances.size() - 1);
        assertThat(testJobInstance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testJobInstance.isEnabled()).isEqualTo(UPDATED_ENABLED);
    }

    @Test
    @Transactional
    public void deleteJobInstance() throws Exception {
        // Initialize the database
        jobInstanceService.save(jobInstance);

        int databaseSizeBeforeDelete = jobInstanceRepository.findAll().size();

        // Get the jobInstance
        restJobInstanceMockMvc.perform(delete("/api/job-instances/{id}", jobInstance.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<JobInstance> jobInstances = jobInstanceRepository.findAll();
        assertThat(jobInstances).hasSize(databaseSizeBeforeDelete - 1);
    }
}
