package dev.lagyh.badwords.manager;

import dev.lagyh.badwords.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BadWordsManager {
    private final Main plugin;
    private final List<String> bannedWords;

    public BadWordsManager(Main main) {
        this.plugin = main;
        this.bannedWords = new ArrayList<>();
        loadBannedWordsFromDatabase();
    }

    private void loadBannedWordsFromDatabase() {
        try {
            Connection connection = plugin.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT word FROM banned_words");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String word = resultSet.getString("word");
                bannedWords.add(word);
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBannedWord(String word) {
        try {
            Connection connection = plugin.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO banned_words (word) VALUES (?)");
            statement.setString(1, word.toLowerCase());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bannedWords.add(word.toLowerCase());
    }

    public String censorMessage(String message) {
        String word;
        for (String bannedWord : bannedWords) {
            word = "\\b" + Pattern.quote(bannedWord) + "\\b";
            message = message.replaceAll(word, repeatChar('*', bannedWord.length()));
        }
        return message;
    }

    private String repeatChar(char character, int count) {
        return new String(new char[count]).replace('\u0000', character);
    }
}
