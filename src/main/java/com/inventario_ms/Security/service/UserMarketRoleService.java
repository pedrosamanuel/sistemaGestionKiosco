package com.inventario_ms.Security.service;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Security.dto.UserMarketRoleDTO;
import com.inventario_ms.Security.model.ERole;
import com.inventario_ms.Security.model.Role;
import com.inventario_ms.Security.model.User;
import com.inventario_ms.Security.model.UserMarketRole;
import com.inventario_ms.Security.repository.UserMarketRoleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserMarketRoleService {

    private final UserMarketRoleRepository userMarketRoleRepository;

    public UserMarketRoleService(UserMarketRoleRepository userMarketRoleRepository) {
        this.userMarketRoleRepository = userMarketRoleRepository;
    }

    public boolean hasRoleInMarket(Long marketId, String roleName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

        return userMarketRoleRepository.existsByUserIdAndMarketIdAndRoleName(userDetails.getId(), marketId, roleName);
    }
    public List <UserMarketRoleDTO> findByMarketId(Long marketId){
       List<UserMarketRole> userMarketRoles =
               userMarketRoleRepository.findByMarketIdAndIsActiveTrue(marketId);
        userMarketRoles.removeIf(umr -> umr.getRole().getName().equals(ERole.ROLE_ADMIN));
       return userMarketRoles.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    public void assignRoleToUser(User user, Market market, Role role) {
        Optional<UserMarketRole> optionalUserMarketRole =
                userMarketRoleRepository.findByUserIdAndMarketIdAndRoleNameAndIsActiveFalse(user.getId(),market.getId(),role.getName());
        UserMarketRole userMarketRole;
        if(optionalUserMarketRole.isPresent()){
            userMarketRole = optionalUserMarketRole.get();
            userMarketRole.setActive(true);
        }else {
            userMarketRole = new UserMarketRole(user, market, role);
        }
        userMarketRoleRepository.save(userMarketRole);
    }
    public boolean unassignRoleToUser(User user, Market market, Role role) {

        Optional<UserMarketRole> optionalUserMarketRole =
                userMarketRoleRepository.findByUserIdAndMarketIdAndRoleNameAndIsActiveTrue(user.getId(),market.getId(), role.getName());
        if (optionalUserMarketRole.isPresent()){
            UserMarketRole userMarketRole = optionalUserMarketRole.get();
            userMarketRole.setActive(false);
            userMarketRoleRepository.save(userMarketRole);
            return true;
        }
        return false;
    }


    public List<UserMarketRoleDTO> getUserRolesInMarket(Long userId, Long marketId) {
         List<UserMarketRole> userMarketRoles =
                 userMarketRoleRepository.findByUserIdAndMarketIdAndIsActiveTrue(userId, marketId);
         return userMarketRoles.stream().map(this::convertToDto).collect(Collectors.toList());
    }



    public List<UserMarketRole> getUsersWithRoleInMarket(Long marketId, String roleName) {
        return userMarketRoleRepository.findByMarketIdAndRoleName(marketId, roleName);
    }

    public List<UserMarketRole> getUserMarketRoleByUserId(Long id) {
        return userMarketRoleRepository.findByUserId(id);
    }

    public UserMarketRoleDTO convertToDto(UserMarketRole userMarketRole){
        UserMarketRoleDTO dto = new UserMarketRoleDTO();
        dto.setMarketId(userMarketRole.getMarket().getId());
        dto.setUser(userMarketRole.getUser());
        dto.setRole(userMarketRole.getRole());
        dto.setId(userMarketRole.getId());
        return dto;
    }
}

