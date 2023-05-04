package net.reduck.jpa.test;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import net.reduck.jpa.test.controller.AESTool;
import net.reduck.jpa.test.controller.DataUtils;

import java.io.UnsupportedEncodingException;

/**
 * @author Gin
 * @since 2023/2/15 15:16
 */
public class DataTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String data = "c310d2bdace750a103a8633757796038b0bd297c8e3c070a965151720b51f650060163f3a75378c0ae182feb2926bffdc4bc6ffb05adba9670dafb636308c0c09dd9d816ff5fe8109b0280ec72791cb53b25edbbdb17f192b17fae6629cee9fbd4f89d391e3514433987700a6f93c6962ad087530d6f3848725a64ed5d560c13ff15e143e87a8449a30036a334fead7dd67f0fedc93b85fb1ba0fabeec9829b78050bfecd4aaccf11b7a7873440fddef6f64305d05c93cabdd163993ab448519df5a7f5f1b8c428de5e800c256a591dcf901a47204c0ae71a8a3d3e5b125d0c032c57b691955f8f73b2ad153c65ad1b6cc24f9bfeb1bb06039fa34367b47e95b6869865802c9a0805b2633e874e784c8d8f69af236c4db787f47f5aebf68a71306ed5737ecff850acb5ac6c2862476ee6c54c6b42fc3e1ccbea9d4d8f1897d85a5206d25272e2a31fde295eaf94d3e56";

        System.out.println(DataUtils.decByDefault(data));


    }
}
