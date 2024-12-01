package net.sinodata.business.util.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @Description
 * @Author zcb
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/10/14
 */
public class EsJdbc {
    private String httpHost;

    private RestHighLevelClient restHighLevelClient;

    public EsJdbc(String httpHost) {
        this.httpHost=httpHost;
    }

    public void init() {
        String[] httpHostStrArr = this.httpHost.split(",");
        HttpHost[] httpHosts = new HttpHost[httpHostStrArr.length];
        for (int i = 0; i < httpHostStrArr.length; i++) {
            String httpHostStr = httpHostStrArr[i];
            String scheme = httpHostStr.split(":")[0];
            String hostName = httpHostStr.split(":")[1].substring(2);
            Integer port = Integer.valueOf(httpHostStr.split(":")[2]);
            HttpHost host = new HttpHost(hostName, port, scheme);
            httpHosts[i] = host;
        }

        restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        httpHosts));
    }


    public String getHttpHost() {
        return httpHost;
    }

    public void setHttpHost(String httpHost) {
        this.httpHost = httpHost;
    }

    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }
}
