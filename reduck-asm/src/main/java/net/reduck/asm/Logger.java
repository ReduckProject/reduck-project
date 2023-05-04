package net.reduck.asm;

import java.io.*;

/**
 * @author Reduck
 * @since 2022/9/2 16:41
 */
public class Logger {
    private OutputStream os;

    {
        try {
            os = new FileOutputStream("");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        System.out.println("bbb");
    }
    public Logger() {
        System.out.println("aaaaaa");
    }

    public void log(String message){
        if(message != null){
            try {
                os = getOs();
                os.write((message + "\n").getBytes());
                os.flush();
                os.close();
            } catch (IOException e) {
                //
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    OutputStream getOs(){
        File file = new File(System.getProperty("user.home") + "/Downloads/compile.log");
        if(file.exists()){
            try {
                os = new FileOutputStream(file, true);
            } catch (FileNotFoundException e) {
                // ignnore
            }
        }

        if(os == null){
            try {
                os = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return os;
    }
}
