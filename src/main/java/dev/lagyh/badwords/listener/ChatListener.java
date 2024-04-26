package dev.lagyh.badwords.listener;

import dev.lagyh.badwords.manager.BadWordsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final BadWordsManager badWordsManager;

    public ChatListener(BadWordsManager badWordsManager) {
        this.badWordsManager = badWordsManager;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String originalMessage = event.getMessage();
        String censoredMessage = this.badWordsManager.censorMessage(originalMessage);
        event.setMessage(censoredMessage);
    }
}
