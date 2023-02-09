package net.reduck.jpa.test;

import net.reduck.validator.JsonUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Reduck
 * @since 2023/1/3 14:53
 */
public class JsonTest {

    public static void main(String[] args) {
        String data = "{\n" +
                "                \"webUserBizName\": null,\n" +
                "                \"operationObjBizName\": [],\n" +
                "                \"secondOperationObjBizName\": null,\n" +
                "                \"operationCommandBizName\": \"\",\n" +
                "                \"webIpBizName\": \"\",\n" +
                "                \"accountBizName\": \"\",\n" +
                "                \"clientIpBusinessName\": \"\",\n" +
                "                \"serverIpBusinessName\": \"\",\n" +
                "                \"statementTemplate\": \"\",\n" +
                "                \"id\": \"2116878618310213634\",\n" +
                "                \"engineId\": 4,\n" +
                "                \"protectedDatabase\": 4,\n" +
                "                \"sessionId\": 2887166425,\n" +
                "                \"status\": 1,\n" +
                "                \"reqTime\": 1672393191000,\n" +
                "                \"execTime\": 0,\n" +
                "                \"rowsCount\": 0,\n" +
                "                \"sql\": \"SHOW CREATE TABLE `everfort`.`assets_account`\",\n" +
                "                \"reply\": \"\",\n" +
                "                \"dbUser\": \"gin\",\n" +
                "                \"dbUserLow\": \"gin\",\n" +
                "                \"appName\": \"\",\n" +
                "                \"osUser\": \"\",\n" +
                "                \"hostName\": \"\",\n" +
                "                \"dbName\": \"everfort\",\n" +
                "                \"loginReply\": \"\",\n" +
                "                \"clientPort\": 49406,\n" +
                "                \"dbPort\": 3306,\n" +
                "                \"clientIp\": \"192.168.10.230\",\n" +
                "                \"terminalIp\": \"\",\n" +
                "                \"dbIp\": \"127.0.0.1\",\n" +
                "                \"clientMac\": \"00-00-00-00-00-00\",\n" +
                "                \"appUser\": \"\",\n" +
                "                \"dbMac\": \"00-00-00-00-00-00\",\n" +
                "                \"tableType\": 0,\n" +
                "                \"sqlCatalog\": 19,\n" +
                "                \"action\": 1,\n" +
                "                \"logLevel\": 4,\n" +
                "                \"threat\": 0,\n" +
                "                \"pattern\": \"SHOW CREATE TABLE `everfort`.`assets_account`\",\n" +
                "                \"schema\": \"everfort\",\n" +
                "                \"privilegeTarget\": \"\",\n" +
                "                \"sqlGroups\": \"\",\n" +
                "                \"isSensitive\": 0,\n" +
                "                \"table\": \"\",\n" +
                "                \"field\": \"\",\n" +
                "                \"rule\": \"[{\\\"name\\\":\\\"全部记录\\\",\\\"action\\\":1,\\\"threat\\\":0}]\",\n" +
                "                \"policyId\": \"-1\",\n" +
                "                \"isIpv6\": 0,\n" +
                "                \"engineName\": \"31.119_2\",\n" +
                "                \"policyName\": \"默认策略\",\n" +
                "                \"appIp\": \"\",\n" +
                "                \"appUrl\": \"\",\n" +
                "                \"clientIpLong\": 3232238310,\n" +
                "                \"dbIpLong\": 2130706433,\n" +
                "                \"year\": 2022,\n" +
                "                \"month\": 12,\n" +
                "                \"weekday\": 6,\n" +
                "                \"day\": 30,\n" +
                "                \"accessDate\": 20221230,\n" +
                "                \"createTime\": \"2022-12-30 17:39:51\",\n" +
                "                \"accessTime\": 1672393191000,\n" +
                "                \"appNameLow\": \"\",\n" +
                "                \"osUserLow\": \"\",\n" +
                "                \"hostNameLow\": \"\",\n" +
                "                \"dbNameLow\": \"everfort\",\n" +
                "                \"associatedTables\": 1,\n" +
                "                \"ipv6Src\": \"\",\n" +
                "                \"ipv6Dst\": \"\",\n" +
                "                \"dbType\": 3,\n" +
                "                \"flowLen\": 1567,\n" +
                "                \"backupId\": \"\",\n" +
                "                \"lockFactor\": 0,\n" +
                "                \"hour\": 17\n" +
                "            }";

        Map<String, Object> map = JsonUtils.json2Object(data, TreeMap.class);
        for (Map.Entry entry : map.entrySet()) {
            System.out.println("|" + entry.getKey() + "|" + (entry.getValue() == null ? "         " : entry.getValue().getClass().getSimpleName()) + "|          " + "|          " +entry.getValue()+ "|");
        }
        System.out.println(map.toString());
    }
}
