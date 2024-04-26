/* Decompiler 16ms, total 182ms, lines 36 */
package dev.lagyh.badwords.commands;

import dev.lagyh.badwords.manager.BadWordsManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddBadWordCommand implements CommandExecutor {
    private final BadWordsManager badWordsManager;

    public AddBadWordCommand(BadWordsManager badWordsManager) {
        this.badWordsManager = badWordsManager;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be executed by players.");
            return true;
        } else if (((Player)sender).getPlayer().hasPermission("ban.word")) {
            if (args.length != 1) {
                sender.sendMessage("§cCorrect usage: /addword [badword]");
                return true;
            } else {
                String bannedWord = args[0].toLowerCase();
                this.badWordsManager.addBannedWord(bannedWord);
                sender.sendMessage("§aThe word '" + bannedWord + "' has been successfully added to the list of banned words.");
                return true;
            }
        } else {
            sender.sendMessage("§cThis command can only be executed by staffers.");
            return true;
        }
    }
}