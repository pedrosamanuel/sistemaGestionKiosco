package com.inventario_ms.Security.repository;
import com.inventario_ms.Security.model.ERole;
import com.inventario_ms.Security.model.UserMarketRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserMarketRoleRepository extends JpaRepository<UserMarketRole, Long> {

    List<UserMarketRole> findByMarketIdAndRoleName(Long marketId, String roleName);

    List<UserMarketRole> findByUserId(Long id);

    boolean existsByUserIdAndMarketIdAndRoleName(Long id, Long marketId, String roleName);

    List<UserMarketRole> findByUserIdAndMarketIdAndIsActiveTrue(Long userId, Long marketId);


    Optional<UserMarketRole> findByUserIdAndMarketIdAndRoleNameAndIsActiveTrue(Long userId, Long marketId, ERole roleName);

    List<UserMarketRole> findByMarketIdAndIsActiveTrue(Long marketId);

    Optional<UserMarketRole> findByUserIdAndMarketIdAndRoleNameAndIsActiveFalse(Long user, Long market, ERole roleName);
}
