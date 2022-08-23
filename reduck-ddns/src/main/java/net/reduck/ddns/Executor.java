package net.reduck.ddns;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.reduck.ddns.conf.SystemProperties;
import net.reduck.ddns.service.DnsService;
import net.reduck.ddns.service.IpService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author Gin
 * @since 2022/8/23 14:12
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class Executor implements InitializingBean {
    private final DnsService dnsService;
    private final SystemProperties properties;
    private final IpService ipService;

    @Override
    public void afterPropertiesSet() throws Exception {
        while (true){
            try {
                String ip = ipService.getSelfIp();
                SystemProperties.Domain domain = properties.getDomain();
                dnsService.refreshOrNewDns(domain.getSubDomains(), domain.getDomainName(), domain.getType(), ip);
            }catch (Exception e){
                log.error(e.getMessage(), e);
            }finally {
                Thread.sleep(1000 * 60 * 30L);
            }

        }
    }
}
