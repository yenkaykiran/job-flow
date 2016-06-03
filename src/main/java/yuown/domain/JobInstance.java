package yuown.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A JobInstance.
 */
@Entity
@Table(name = "job_instances")
public class JobInstance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private Boolean enabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JobInstance jobInstance = (JobInstance) o;
        if(jobInstance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, jobInstance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobInstance{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", enabled='" + enabled + "'" +
            '}';
    }
}
