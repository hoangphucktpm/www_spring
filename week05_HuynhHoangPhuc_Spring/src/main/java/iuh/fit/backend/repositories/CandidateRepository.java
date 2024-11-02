package iuh.fit.backend.repositories;

import iuh.fit.backend.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}