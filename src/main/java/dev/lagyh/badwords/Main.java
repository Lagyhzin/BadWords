/* Decompiler 3ms, total 483ms, lines 21 */
package dev.lagyh.badwords;

import dev.lagyh.badwords.commands.AddBadWordCommand;
import dev.lagyh.badwords.listener.ChatListener;
import dev.lagyh.badwords.manager.BadWordsManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main extends JavaPlugin {
    private BadWordsManager censuraManager;

    private Connection connection;


    public void onEnable() {
        this.censuraManager = new BadWordsManager(this);
        this.getServer().getPluginManager().registerEvents(new ChatListener(this.censuraManager), this);
        this.getCommand("addword").setExecutor(new AddBadWordCommand(this.censuraManager));
        getLogger().info("--------------------------");
        getLogger().info("                            ");
        getLogger().info("Plugin made by: Lagyhzin");
        getLogger().info("                            ");
        getLogger().info("--------------------------");
        setupDatabase();
    }

    public BadWordsManager getCensorshipManager() {
        return this.censuraManager;
    }

    private void setupDatabase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + getDataFolder().getAbsolutePath() + "/database.db");
            getLogger().info("Connection to SQLite successfully established!");

            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS banned_words (id INTEGER PRIMARY KEY, word TEXT)"
            );
            statement.executeUpdate();
            statement.close();
        } catch (ClassNotFoundException | SQLException e) {
            getLogger().severe("Error connecting to SQLite: " + e.getMessage());
        }
    }

    public Connection getConnection() {
            return connection;
    }
}