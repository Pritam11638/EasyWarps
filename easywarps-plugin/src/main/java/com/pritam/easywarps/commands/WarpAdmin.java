package com.pritam.easywarps.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class WarpAdmin {
    public static LiteralCommandNode<CommandSourceStack> getCommand() {
        LiteralCommandNode<CommandSourceStack> command = Commands.literal("warpadmin")
                .requires(source -> source.getSender().hasPermission("ew.admin"))
                .executes(context -> {
                    context.getSource().getSender().sendMessage(Component
                            .text("EasyWarps Admin Command.")
                            .append(Component.newline())
                            .append(Component.text("Only available to players with the 'ew.admin' permission."))
                            .color(NamedTextColor.YELLOW)
                    );

                    return Command.SINGLE_SUCCESS;
                })
                .build();

        return command;
    }

    public static LiteralCommandNode<CommandSourceStack> getAlias() {
        return Commands.literal("ewa").redirect(getCommand()).build();
    }
}
