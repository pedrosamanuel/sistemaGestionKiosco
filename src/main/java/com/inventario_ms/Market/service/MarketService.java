package com.inventario_ms.Market.service;

import com.inventario_ms.Generic.NonDependent.NonDependentGenericService;
import com.inventario_ms.Market.domain.Market;
import com.inventario_ms.Market.dto.MarketDTO;
import com.inventario_ms.Market.repository.MarketRepository;
import com.inventario_ms.Security.service.RoleService;
import com.inventario_ms.Security.model.ERole;
import com.inventario_ms.Security.model.Role;
import com.inventario_ms.Security.model.User;
import com.inventario_ms.Security.model.UserMarketRole;
import com.inventario_ms.Security.repository.UserMarketRoleRepository;
import com.inventario_ms.Security.service.UserDetailsServiceImpl;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketService extends NonDependentGenericService<Market, MarketDTO, MarketRepository, Long> {

    private final MarketRepository marketRepository;
    private final RoleService roleService;
    private final UserDetailsServiceImpl userDetailsService;

    private final UserMarketRoleRepository userMarketRoleRepository;
    public MarketService(MarketRepository marketRepository, RoleService roleService, UserDetailsServiceImpl userDetailsService, UserMarketRoleRepository userMarketRoleRepository) {
        super(marketRepository);
        this.marketRepository = marketRepository;
        this.roleService = roleService;
        this.userDetailsService = userDetailsService;
        this.userMarketRoleRepository = userMarketRoleRepository;
    }

    @Transactional
    public void save(Market market, Long userId){
        Market marketSaved = marketRepository.save(market);
        User user = userDetailsService.loadUserById(userId);
        Role role = roleService.findByName(ERole.ROLE_ADMIN).orElse(null);

        UserMarketRole userMarketRole = new UserMarketRole(user,marketSaved,role);
        userMarketRoleRepository.save(userMarketRole);
    }
    @Override
    protected Market updateEntity(Market entity, Market updatedEntity) {
        entity.setName(updatedEntity.getName());
        return entity;
    }

    @Override
    protected MarketDTO convertToDTO(Market entity) {
        MarketDTO marketDTO = new MarketDTO();
        marketDTO.setId(entity.getId());
        marketDTO.setName(entity.getName());
        return marketDTO;
    }

    public boolean marketBelongsToUser(Long marketId, Long userId) {
        return marketRepository.marketBelongsToUser(marketId, userId);
    }


    public List<MarketDTO> findAllByUserId(Long userId) {
        List<Market> markets = marketRepository.findAllByUserId(userId);
        return markets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


}
