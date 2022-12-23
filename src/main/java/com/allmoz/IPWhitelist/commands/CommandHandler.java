package com.allmoz.IPWhitelist.commands;

import com.allmoz.IPWhitelist.IPWhitelist;
import com.allmoz.IPWhitelist.config.Configs;
import com.allmoz.IPWhitelist.helpers.WhitelistHelper;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.ParsedArgument;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class CommandHandler {

    private IPWhitelist ipWhitelist;

    public CommandHandler(IPWhitelist ipWhitelist) {
        this.ipWhitelist = ipWhitelist;
    }

    /**
     * A bit of basic about information
     * @param commandSourceCommandContext
     * @return
     */
    public int about(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        String status = Configs.getConfig().isEnabled() ? "&2&lON" : "&c&lOFF";
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + ipWhitelist.PREFIX + "IPWhitelist is " + status));
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + ipWhitelist.PREFIX + "IPWhitelist by james090500, moded by allmoz"));
        return 1;
    }

    /**
     * Turn on the whitelist
     * @param commandSourceCommandContext
     * @return
     */
    public int turnOn(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        if(Configs.getConfig().isEnabled()) {
            source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&c" + ipWhitelist.PREFIX + "IPWhitelist is already turned on"));
        } else {
            Configs.getConfig().setEnabled(true);
            Configs.saveConfig(ipWhitelist);
            source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + ipWhitelist.PREFIX + "IPWhitelist turned &2&lON"));
        }
        return 1;
    }

    /**
     * Turn off the whitelist
     * @param commandSourceCommandContext
     * @return
     */
    public int turnOff(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        if(!Configs.getConfig().isEnabled()) {
            source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&c" + ipWhitelist.PREFIX + "IPWhitelist is already turned off"));
        } else {
            Configs.getConfig().setEnabled(false);
            Configs.saveConfig(ipWhitelist);
            source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + ipWhitelist.PREFIX + "IPWhitelist turned &c&lOFF"));
        }
        return 1;
    }

    /**
     * The command for /vwhitelist add <username>
     * Handles adding a user to the whitelist
     * @param commandSourceCommandContext
     * @return
     */
    public int add(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        ParsedArgument<CommandSource, ?> ip = commandSourceCommandContext.getArguments().get("ip");
        if(ip == null) {
            source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&c" + ipWhitelist.PREFIX + "Syntax /ipwhitelist add <ip>"));
            return 1;
        }

        new WhitelistHelper(ipWhitelist, source).add((String) ip.getResult());
        return 1;
    }

    /**
     * The command for /vwhitelist remove <username>
     * Handles removing a user from the whitelist
     * @param commandSourceCommandContext
     * @return
     */
    public int remove(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        ParsedArgument<CommandSource, ?> ip = commandSourceCommandContext.getArguments().get("ip");
        if(ip == null) {
            source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&c" + ipWhitelist.PREFIX + "Syntax /ipwhitelist remove <ip>"));
            return 1;
        }

        new WhitelistHelper(ipWhitelist, source).remove((String) ip.getResult());
        return 1;
    }

    /**
     * Reloads the configs
     * @param commandSourceCommandContext
     * @return
     */
    public int reload(CommandContext<CommandSource> commandSourceCommandContext) {
        Configs.loadConfigs(ipWhitelist);
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize("&a" + ipWhitelist.PREFIX + "Reloaded"));
        return 1;
    }
}