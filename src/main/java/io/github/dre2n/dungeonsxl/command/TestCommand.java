/*
 * Copyright (C) 2012-2016 Frank Baumann
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.dre2n.dungeonsxl.command;

import io.github.dre2n.commons.command.BRCommand;
import io.github.dre2n.commons.util.messageutil.MessageUtil;
import io.github.dre2n.dungeonsxl.DungeonsXL;
import io.github.dre2n.dungeonsxl.config.DMessages;
import io.github.dre2n.dungeonsxl.game.Game;
import io.github.dre2n.dungeonsxl.game.GameTypeDefault;
import io.github.dre2n.dungeonsxl.player.DGamePlayer;
import io.github.dre2n.dungeonsxl.player.DGroup;
import io.github.dre2n.dungeonsxl.player.DPermissions;
import io.github.dre2n.dungeonsxl.world.GameWorld;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Daniel Saukel
 */
public class TestCommand extends BRCommand {

    DungeonsXL plugin = DungeonsXL.getInstance();

    public TestCommand() {
        setCommand("test");
        setMinArgs(0);
        setMaxArgs(0);
        setHelp(DMessages.HELP_CMD_TEST.getMessage());
        setPermission(DPermissions.TEST.getNode());
        setPlayerCommand(true);
    }

    @Override
    public void onExecute(String[] args, CommandSender sender) {
        Player player = (Player) sender;

        DGroup dGroup = DGroup.getByPlayer(player);
        if (dGroup == null) {
            MessageUtil.sendMessage(sender, DMessages.ERROR_JOIN_GROUP.getMessage());
            return;
        }

        if (!dGroup.getCaptain().equals(player)) {
            MessageUtil.sendMessage(sender, DMessages.ERROR_NOT_CAPTAIN.getMessage());
            return;
        }

        GameWorld gameWorld = dGroup.getGameWorld();
        if (gameWorld == null) {
            MessageUtil.sendMessage(sender, DMessages.ERROR_NOT_IN_DUNGEON.getMessage());
            return;
        }

        Game game = gameWorld.getGame();
        if (game != null) {
            MessageUtil.sendMessage(sender, DMessages.ERROR_LEAVE_DUNGEON.getMessage());
            return;
        }

        for (Player groupPlayer : dGroup.getPlayers()) {
            DGamePlayer.getByPlayer(groupPlayer).ready(GameTypeDefault.TEST);
        }
    }

}
