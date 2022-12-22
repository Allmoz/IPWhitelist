package com.allmoz.IPWhitelist.commands;

import com.allmoz.IPWhitelist.IPWhitelist;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;

public class CommandBuilder {

    private static ProxyServer server;

    /**
     * Reginster all commands
     * @param ipWhitelist
     */
    public static void register(IPWhitelist ipWhitelist) {
        server = ipWhitelist.getServer();
        //Setup command flow
        final CommandHandler handler = new CommandHandler(ipWhitelist);
        server.getCommandManager().register(server.getCommandManager().metaBuilder("ipwhitelist").build(), new BrigadierCommand(
                LiteralArgumentBuilder.<CommandSource>literal("ipwhitelist").requires(sender -> sender.hasPermission("ipwhitelist.admin")).executes(handler::about)
                        .then(LiteralArgumentBuilder.<CommandSource>literal("on").executes(handler::turnOn))
                        .then(LiteralArgumentBuilder.<CommandSource>literal("off").executes(handler::turnOff))

                        .then(LiteralArgumentBuilder.<CommandSource>literal("add").executes(handler::add))
                        .then(LiteralArgumentBuilder.<CommandSource>literal("add")
                                .then(RequiredArgumentBuilder.<CommandSource, String>argument("ip", StringArgumentType.word())
                                .executes(handler::add)))

                        .then(LiteralArgumentBuilder.<CommandSource>literal("remove").executes(handler::remove))
                        .then(LiteralArgumentBuilder.<CommandSource>literal("remove")
                                .then(RequiredArgumentBuilder.<CommandSource, String>argument("ip", StringArgumentType.word())
                                .executes(handler::remove)))

                        .then(LiteralArgumentBuilder.<CommandSource>literal("reload").requires(source -> source.hasPermission("vgui.admin")).executes(handler::reload))
        ));
    }
}
