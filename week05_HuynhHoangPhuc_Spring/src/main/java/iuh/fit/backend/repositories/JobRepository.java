package iuh.fit.backend.repositories;

import iuh.fit.backend.models.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByJobNameContainingOrCompany_CompNameContainingOrJobSkills_Skill_SkillNameContaining(
            String jobName, String companyName, String skillName, Pageable pageable);
}