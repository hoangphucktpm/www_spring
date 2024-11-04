package iuh.fit.backend.repositories;

import iuh.fit.backend.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CompanyRepository extends JpaRepository<Company, Long> {
}