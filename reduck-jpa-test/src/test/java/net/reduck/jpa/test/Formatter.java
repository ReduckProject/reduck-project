package net.reduck.jpa.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Gin
 * @since 2023/6/13 20:45
 */
public class Formatter {

    public static void main(String[] args) throws ParseException {
        System.out.println("在 Linux 系统中，`systemctl` 命令用于管理系统服务。要查看服务所在的位置，可以使用以下命令：\n\n```shell\nsystemctl show -p FragmentPath <service-name>\n```\n\n将 `<service-name>` 替换为你要查看的服务名称。该命令将显示服务文件的路径（包括文件名）。通过查看服务文件的位置，你可以找到服务的配置信息和其他相关文件。");

        System.out.println(System.currentTimeMillis());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = simpleDateFormat.parse("2023-06-02 20:53:46");
        Date date2 = simpleDateFormat.parse("2023-06-02 21:03:46");
        System.out.println(date.getTime());
        System.out.println(date2.getTime());


        System.out.println(System.nanoTime());
        System.out.println("485762154749725");
    }
}
