package iuh.fit.backend.repositories;

import iuh.fit.backend.models.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}