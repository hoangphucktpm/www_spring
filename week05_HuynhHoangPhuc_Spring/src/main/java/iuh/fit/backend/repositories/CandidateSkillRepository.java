package iuh.fit.backend.repositories;


import iuh.fit.backend.models.CandidateSkill;
import iuh.fit.backend.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Skill> {
}
