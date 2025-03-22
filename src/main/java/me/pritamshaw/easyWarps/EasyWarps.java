package me.pritamshaw.easyWarps;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import me.pritamshaw.easyWarps.warp.Warp;
import me.pritamshaw.easyWarps.warp.WarpsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyWarps extends JavaPlugin {
    @Getter
    private static WarpsManager warpsManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        Metrics metrics = new Metrics(this, 25202);

        warpsManager = new WarpsManager(this);

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, eHandler -> {
            LiteralCommandNode<CommandSourceStack> warpManagerCommand = Commands
                    .literal("warp-manager")
                    .requires(cSS -> (cSS.getSender().hasPermission("ew.admin") && cSS.getSender() instanceof Player))
                    .executes(cSS -> {
                        cSS.getSource().getSender().sendMessage(Component
                                .text("Please enter an argument!")
                                .color(NamedTextColor.RED)
                                .decoration(TextDecoration.ITALIC, false)
                        );

                        return Command.SINGLE_SUCCESS;
                    })
                    .then(Commands
                            .literal("add")
                            .executes(cSS -> {
                                cSS.getSource().getSender().sendMessage(Component
                                        .text("Please enter an argument!")
                                        .color(NamedTextColor.RED)
                                        .decoration(TextDecoration.ITALIC, false)
                                );

                                return Command.SINGLE_SUCCESS;
                            })
                            .then(Commands
                                    .argument("warp-name", StringArgumentType.word())
                                    .executes(cSS -> {
                                        String warpName = StringArgumentType.getString(cSS, "warp-name");

                                        if (warpsManager.registerNewWarp(new Warp(warpName, cSS.getSource().getLocation()))) {
                                            cSS.getSource().getSender().sendMessage(Component
                                                    .text("Created a warp " + warpName + " at your current location!")
                                                    .color(NamedTextColor.GREEN)
                                                    .decoration(TextDecoration.ITALIC, false)
                                            );
                                        } else {
                                            cSS.getSource().getSender().sendMessage(Component
                                                    .text("A warp with that name already exists!")
                                                    .color(NamedTextColor.RED)
                                                    .decoration(TextDecoration.ITALIC, false)
                                            );
                                        }

                                        return Command.SINGLE_SUCCESS;
                                    })))
                    .then(Commands
                            .literal("remove")
                            .executes(cSS -> {
                                cSS.getSource().getSender().sendMessage(Component
                                        .text("Please enter an argument!")
                                        .color(NamedTextColor.RED)
                                        .decoration(TextDecoration.ITALIC, false)
                                );

                                return Command.SINGLE_SUCCESS;
                            })
                            .then(Commands
                                    .argument("warp-name", StringArgumentType.word())
                                    .suggests((context, builder) -> {
                                        warpsManager.getAllWarps().forEach(warp -> builder.suggest(warp.name()));

                                        return builder.buildFuture();
                                    })
                                    .executes(cSS -> {
                                        String warpName = StringArgumentType.getString(cSS, "warp-name");

                                        if (warpsManager.unregisterWarp(warpName)) {
                                            cSS.getSource().getSender().sendMessage(Component
                                                    .text("Removed warp " + warpName + "!")
                                                    .color(NamedTextColor.GREEN)
                                                    .decoration(TextDecoration.ITALIC, false)
                                            );
                                        } else {
                                            cSS.getSource().getSender().sendMessage(Component
                                                    .text("A warp with that name doesn't exist!")
                                                    .color(NamedTextColor.RED)
                                                    .decoration(TextDecoration.ITALIC, false)
                                            );
                                        }

                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
                    )
                    .build();

            LiteralCommandNode<CommandSourceStack> warpCommand = Commands
                    .literal("warp")
                    .requires(cSS -> (cSS.getSender() instanceof Player))
                    .executes(cSS -> {
                        cSS.getSource().getSender().sendMessage(Component
                                .text("Please enter an argument!")
                                .color(NamedTextColor.RED)
                                .decoration(TextDecoration.ITALIC, false)
                        );

                        return Command.SINGLE_SUCCESS;
                    })
                    .then(Commands
                            .argument("warp-name", StringArgumentType.word())
                            .suggests((context, builder) -> {
                                warpsManager.getAllWarps().forEach(warp -> builder.suggest(warp.name()));

                                return builder.buildFuture();
                            })
                            .executes(cSS -> {
                                String warpName = cSS.getArgument("warp-name", String.class);

                                if (warpsManager.warpExists(warpName)) {
                                    warpsManager.warpPlayer((Player) cSS.getSource().getSender(), warpName);
                                } else {
                                    cSS.getSource().getSender().sendMessage(Component
                                            .text("A warp with that name doesn't exist!")
                                            .color(NamedTextColor.RED)
                                            .decoration(TextDecoration.ITALIC, false)
                                    );
                                }

                                return Command.SINGLE_SUCCESS;
                            })
                    )
                    .build();

            eHandler.registrar().register(warpManagerCommand);
            eHandler.registrar().register(warpCommand);
        });

        getLogger().info("EasyWarp has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
