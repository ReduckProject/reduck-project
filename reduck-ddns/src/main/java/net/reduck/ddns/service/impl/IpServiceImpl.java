package net.reduck.ddns.service.impl;

import net.reduck.ddns.HttpClient;
import net.reduck.ddns.conf.SystemProperties;
import net.reduck.ddns.service.IpService;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Gin
 * @since 2022/8/15 11:30
 */
@Component
public class IpServiceImpl implements IpService {
    static final Pattern ipPattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)");
    static final HttpClient client = new HttpClient();

    final SystemProperties properties;

    public IpServiceImpl(SystemProperties properties) {
        this.properties = properties;
    }

    @Override
    public String getSelfIp() {
        Matcher matcher = ipPattern.matcher(client.get(properties.getIpServiceUrl()));
        return matcher.find() ? matcher.group() : null;
    }
}
