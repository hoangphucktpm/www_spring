package iuh.fit.backend.repositories;

import iuh.fit.backend.models.JobSkill;
import iuh.fit.backend.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobSkillRepository extends JpaRepository<JobSkill, Skill> {
}
