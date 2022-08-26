package net.reduck.ddns.service;

import java.util.List;

/**
 * @author Reduck
 * @since 2022/8/15 11:38
 */
public interface DnsService {

    boolean refreshOrNewDns(String dn, String domainName, String type, String ip);

    boolean refreshOrNewDns(List<String> dnList, String domainName, String type, String ip);
}
