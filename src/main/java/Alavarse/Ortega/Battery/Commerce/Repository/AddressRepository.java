package Alavarse.Ortega.Battery.Commerce.Repository;

import Alavarse.Ortega.Battery.Commerce.Entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, String> {
    List<AddressEntity> findByUserId(String userId);
}
