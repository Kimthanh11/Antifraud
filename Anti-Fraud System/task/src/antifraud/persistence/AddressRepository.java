package antifraud.persistence;

import antifraud.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
    Optional<Address> findByIp(String ip);

    boolean existsByIp(String ip);
}
