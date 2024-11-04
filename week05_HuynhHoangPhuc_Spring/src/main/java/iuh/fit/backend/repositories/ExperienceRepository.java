package iuh.fit.backend.repositories;

import iuh.fit.backend.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
}