package Alavarse.Ortega.Battery.Commerce.Repositories;

import Alavarse.Ortega.Battery.Commerce.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserDetails findByEmail(String email);

    @Query(value = "SELECT * FROM users u WHERE u.role = :role AND u.status = 'ACTIVE'", nativeQuery = true)
    List<UserEntity> findAllByRoleAndActive(@Param("role") String role);

    @Query(value = "SELECT * FROM users u WHERE u.status = 'ACTIVE'", nativeQuery = true)
    List<UserEntity> findAllActiveUsers();

    Optional<UserEntity> findByDocument(String document);
}
