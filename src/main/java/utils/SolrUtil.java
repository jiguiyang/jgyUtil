package utils;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;

/**
 * @jiguiyang
 *
 * solr 操作
 */
public class SolrUtil {

    private static final String SOLRCORE = "http://blackbox01.jry.com:8983/solr/jgycollection";
    private static SolrClient client;
    private static String url;
    static {
        url = SOLRCORE;
        client = new HttpSolrClient.Builder(url).build();
    }

    /**
     * 保存或者更新solr数据
     *
     * @param solrEntity
     */
    public static <T> boolean saveSolrResource(T solrEntity) {

        DocumentObjectBinder binder = new DocumentObjectBinder();
        SolrInputDocument doc = binder.toSolrInputDocument(solrEntity);
        try {
            client.add(doc);
            client.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 删除solr 数据
     *
     * @param id
     */
    public static boolean removeSolrData(String id) {
        try {
            client.deleteById(id);
            client.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 查询
     *
     * @param keywords
     */
    public static QueryResponse query(String keywords) throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.setQuery(keywords);
        QueryResponse rsp = client.query(query);
        return rsp;
    }
}
