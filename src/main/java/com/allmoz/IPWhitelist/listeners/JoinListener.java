package com.allmoz.IPWhitelist.listeners;

import com.allmoz.IPWhitelist.config.Configs;
import com.allmoz.IPWhitelist.helpers.WhitelistHelper;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class JoinListener {

    @Subscribe(order = PostOrder.EARLY)
    public void onPlayerJoin(LoginEvent event) {
        Player player = event.getPlayer();
        if(Configs.getConfig().isEnabled()) {
            if(!WhitelistHelper.check(player)) {
                TextComponent kickMessage = LegacyComponentSerializer.legacyAmpersand().deserialize(Configs.getConfig().getMessage());
                event.setResult(ResultedEvent.ComponentResult.denied(kickMessage));
            }
        }
    }

}
