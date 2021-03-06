package org.to2mbn.basiscommands.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import org.to2mbn.basiscommands.BasisCommands;
import org.to2mbn.basiscommands.i18n.I18n;
import org.to2mbn.basiscommands.util.PluginUtils;
import org.to2mbn.basiscommands.util.command.CommandArgumentTemplet;
import org.to2mbn.basiscommands.util.command.CommandArguments;

public class CommandTp implements Command {
    @Override
    public String getName() {
        return "tp";
    }

    @Override
    public boolean canExecute(CommandSender sender) {
        return sender instanceof Player;
    }

    @Override
    public CommandArgumentTemplet<?>[] getArgumentTemplets() {
        return new CommandArgumentTemplet<?>[]{
                new CommandArgumentTemplet<>(String.class, true)
        };
    }

    @Override
    public void execute(CommandSender sender, CommandArguments args) {
        Player player = (Player) sender;
        Player target = Server.getInstance().getPlayer(args.nextString());

        if (target == null) {
            player.sendMessage("This player is not online");
            return;
        }

        player.teleportImmediate(target.getPosition());

        BasisCommands.logger().info("Teleported player " + player.getName() + " to " + target.getName());
        PluginUtils.sendMessage(player, I18n.format("command.tp.teleported_msg", target.getName()));
        if (BasisCommands.getConfiguration().getBoolean("player.notice_when_tp")) {
            PluginUtils.sendMessage(target, I18n.format("command.tp.target_teleported_msg", player.getName()));
        }
    }
}
