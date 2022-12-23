package com.allmoz.IPWhitelist.helpers;

import com.allmoz.IPWhitelist.IPWhitelist;
import com.allmoz.IPWhitelist.config.Configs;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import org.apache.commons.validator.routines.InetAddressValidator;

public class WhitelistHelper {

    private IPWhitelist ipWhitelist;
    private CommandSource source;

    public WhitelistHelper(IPWhitelist ipWhitelist, CommandSource source) {
        this.ipWhitelist = ipWhitelist;
        this.source = source;
    }

    /**
     * Check if the Player is in the whitelist OR has bypass permissions
     * @param player Player instance
     * @return If Player is whitelisted or has a bypass
     */
    public static boolean check(Player player) {
        String address = player.getRemoteAddress().getAddress().getHostAddress();
        return player.hasPermission("ipwhitelist.bypass") || Configs.getWhitelist().contains(address);
    }

    /**
     * Add an IP to the whitelist
     * @param ip IP string
     */
    public void add(String ip) {
        ipWhitelist.getServer().getScheduler().buildTask(ipWhitelist, () -> {
            if(!InetAddressValidator.getInstance().isValid(ip)) {
                source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&c" + ipWhitelist.PREFIX + ip + " is not a valid ip"));
            } else if(Configs.getWhitelist().contains(ip)) {
                source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + ipWhitelist.PREFIX + ip + " is already in the whitelist"));
            } else {
                Configs.getWhitelist().add(ip);
                Configs.saveWhitelist(ipWhitelist);
                source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + ipWhitelist.PREFIX + ip + " has been added to the whitelist"));
            }
        }).schedule();
    }

    /**
     * Remove a player from the whitelist
     * @param ip IP string
     */
    public void remove(String ip) {
        ipWhitelist.getServer().getScheduler().buildTask(ipWhitelist, () -> {
            if(!InetAddressValidator.getInstance().isValid(ip)) {
                source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&c" + ipWhitelist.PREFIX + ip + " is not a valid ip"));
            } else if(!Configs.getWhitelist().contains(ip)) {
                source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + ipWhitelist.PREFIX + ip + " is not in the whitelist"));
            } else {
                Configs.getWhitelist().remove(ip);
                Configs.saveWhitelist(ipWhitelist);
                source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + ipWhitelist.PREFIX + ip + " has been removed from the whitelist"));
            }
        }).schedule();
    }
}
