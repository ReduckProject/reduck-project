package net.reduck.netools;

import javax.sound.sampled.*;
import java.io.File;

/**
 * @author Gin
 * @since 2023/4/12 17:00
 */
public class AudioTest {
    public static void main(String[] args) {
        playMusic("/Users/zhanjinkai/Documents/GitHub/reduck-project/reduck-netools/src/test/resources/test.mp3");
    }

    public static void playMusic(String filePath) {
        try {
            // 打开音频文件
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));

            // 获取音频格式
            AudioFormat audioFormat = audioInputStream.getFormat();

            // 创建音频数据源
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat);
            SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

            // 打开音频数据源
            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

            // 播放音频数据
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = audioInputStream.read(buffer)) != -1) {
                sourceDataLine.write(buffer, 0, bytesRead);
            }

            // 关闭音频数据源
            sourceDataLine.drain();
            sourceDataLine.stop();
            sourceDataLine.close();
            audioInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
