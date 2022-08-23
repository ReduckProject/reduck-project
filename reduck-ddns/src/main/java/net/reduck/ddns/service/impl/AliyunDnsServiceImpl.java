package net.reduck.ddns.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.*;
import lombok.SneakyThrows;
import net.reduck.ddns.service.DnsService;
import net.reduck.tool.ou.ToString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Gin
 * @since 2022/8/15 11:41
 */
public class AliyunDnsServiceImpl implements DnsService {
    private final IAcsClient client;

    public AliyunDnsServiceImpl(IAcsClient client) {
        this.client = client;
    }

    @Override
    public boolean refreshOrNewDns(String dn, String domainName, String type, String ip) {
        Map<String, DescribeDomainRecordsResponse.Record> recordMap = obtainExistRecords(domainName)
                .stream()
                .collect(Collectors.toMap(DescribeDomainRecordsResponse.Record::getRR, record -> record));

        if (recordMap.containsKey(dn)) {
            if (!ip.equals(recordMap.get(dn).getValue())) {
                update(dn, domainName, ip, recordMap.get(dn).getRecordId());
            }
        } else {
            add(dn, domainName, type, ip);
        }
        return true;
    }

    @Override
    public boolean refreshOrNewDns(List<String> dnList, String domainName, String type, String ip) {
        Map<String, DescribeDomainRecordsResponse.Record> recordMap = obtainExistRecords(domainName)
                .stream()
                .collect(Collectors.toMap(DescribeDomainRecordsResponse.Record::getRR, record -> record));
        for (String dn : dnList) {
            if (recordMap.containsKey(dn)) {
                if (!ip.equals(recordMap.get(dn).getValue())) {
                    update(dn, type, ip, recordMap.get(dn).getRecordId());
                }
            } else {
                add(dn, domainName, type, ip);
            }
        }
        return true;
    }

    @SneakyThrows
    private List<DescribeDomainRecordsResponse.Record> obtainExistRecords(String domainName) {
        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
        request.setDomainName(domainName);
        return client.getAcsResponse(request).getDomainRecords();
    }

    @SneakyThrows
    public void add(String subDomain, String domainName, String type, String ip) {
        AddDomainRecordRequest request = new AddDomainRecordRequest();
        request.setDomainName(domainName);
        request.setRR(subDomain);
        request.setType(type);
        request.setValue(ip);
        AddDomainRecordResponse response = client.getAcsResponse(request);
        System.out.println("Prepare add " + subDomain + "." + domainName + ", ip = " + ip);
        System.out.println(ToString.of(response));
    }

    @SneakyThrows
    public void update(String subDomain, String type, String ip, String recordId) {
        UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
        request.setRecordId(recordId);
        request.setRR(subDomain);
        request.setType(type);
        request.setValue(ip);
        UpdateDomainRecordResponse response = client.getAcsResponse(request);
        System.out.println("Prepare update " + subDomain + ", ip = " + ip);
        System.out.println(ToString.of(response));
    }
}
