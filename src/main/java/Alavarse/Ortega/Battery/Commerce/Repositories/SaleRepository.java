package Alavarse.Ortega.Battery.Commerce.Repositories;

import Alavarse.Ortega.Battery.Commerce.Entities.SaleEntity;
import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<SaleEntity, String> {
    List<SaleEntity> findByUser(UserEntity user);
}
