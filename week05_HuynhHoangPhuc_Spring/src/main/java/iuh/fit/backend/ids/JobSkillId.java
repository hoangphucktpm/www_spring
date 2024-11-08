package iuh.fit.backend.ids;

import iuh.fit.backend.models.Skill;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class JobSkillId extends Skill implements Serializable {
    private static final long serialVersionUID = -4733981701765719882L;
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @Column(name = "skill_id", nullable = false)
    private Long skillId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JobSkillId entity = (JobSkillId) o;
        return Objects.equals(this.jobId, entity.jobId) &&
                Objects.equals(this.skillId, entity.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, skillId);
    }

    @Override
    public String toString() {
        return "JobSkillId{" +
                "jobId=" + jobId +
                ", skillId=" + skillId +
                '}';
    }

    public JobSkillId(Long id) {
    }

    public JobSkillId(Long jobId, Long skillId) {
        this.jobId = jobId;
        this.skillId = skillId;
    }

    public JobSkillId() {
    }
    

}