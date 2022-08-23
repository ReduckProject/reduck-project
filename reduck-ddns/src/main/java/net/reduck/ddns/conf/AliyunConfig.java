package net.reduck.ddns.conf;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import net.reduck.ddns.service.DnsService;
import net.reduck.ddns.service.impl.AliyunDnsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gin
 * @since 2022/8/23 13:51
 */
@Configuration
public class AliyunConfig {

    @Bean
    public IAcsClient iAcsClient(SystemProperties systemProperties) {
        SystemProperties.Domain domain = systemProperties.getDomain();
        IClientProfile profile = DefaultProfile.getProfile(domain.getRegionId()
                , systemProperties.getAccess().getAccessKey()
                , systemProperties.getAccess().getSecretKey()
        );

        // 若报Can not find endpoint to access异常，请添加以下此行代码
        // DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Alidns", "alidns.aliyuncs.com");
        return new DefaultAcsClient(profile);
    }

    @Bean
    public DnsService dnsService(IAcsClient iAcsClient){
        return new AliyunDnsServiceImpl(iAcsClient);
    }
}
