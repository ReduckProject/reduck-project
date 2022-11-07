package net.reduck.ddns;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.reduck.ddns.conf.SystemProperties;
import net.reduck.ddns.service.DnsService;
import net.reduck.ddns.service.IpService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author Reduck
 * @since 2022/8/23 14:12
 */
@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class Executor implements InitializingBean {
    private final DnsService dnsService;
    private final SystemProperties properties;
    private final IpService ipService;

    @Override
    public void afterPropertiesSet(){
        String latestIp = "0.0.0.0";
        while (true) {
            try {
                String ip = ipService.getSelfIp();
                if (StringUtils.isEmpty(ip)) {
                    log.warn("Obtain ip is empty , waiting for next !");
                    continue;
                }

                if (!latestIp.equals(ip)) {
                    SystemProperties.Domain domain = properties.getDomain();
                    boolean success = dnsService.refreshOrNewDns(domain.getSubDomains(), domain.getDomainName(), domain.getType(), ip);

                    if (success) {
                        latestIp = ip;
                        log.info("Ip has changed, current ip is {}", latestIp);
                    }
                }


            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                try {
                    Thread.sleep(1000 * 60 * properties.getPeriod());
                } catch (InterruptedException e) {
                    log.warn("InterruptedException happened !");
                }
            }

        }
    }
}
