package iuh.fit.backend.repositories;

import iuh.fit.backend.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
