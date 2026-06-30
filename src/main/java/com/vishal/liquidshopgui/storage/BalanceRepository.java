package com.vishal.liquidshopgui.storage;

import com.vishal.liquidshopgui.models.PlayerBalance;

import java.util.Optional;
import java.util.UUID;

public interface BalanceRepository {

    Optional<PlayerBalance> findByPlayerId(UUID playerId);

    void save(PlayerBalance balance);

    void deleteByPlayerId(UUID playerId);
}
