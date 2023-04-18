package net.reduck.netools;

import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Gin
 * @since 2023/4/12 17:09
 */
public class Mp3PlayerTest {

    public static void main(String[] args) {
        String filePath = "/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-netools/src/test/resources/test.mp3"; // MP3 文件路径
        try {
            FileInputStream fis = new FileInputStream(filePath);
            Player player = new Player(fis);
            player.play();

            if(player.isComplete()) {
                player.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
