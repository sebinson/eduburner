package readerside.crawler.processor;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Feed;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import readerside.crawler.FetchResult;
import readerside.crawler.ProcessResult;
import readerside.crawler.event.FeedFetchedEvent;
import readerside.crawler.model.CrawlURI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-28
 * Time: 9:51:47
 */
public class HttpProcessor implements IProcessor, InitializingBean, ApplicationContextAware{
    private static final Logger logger = LoggerFactory
            .getLogger(HttpProcessor.class);

    private HttpClient client;
    private String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.1) Gecko/20090715 Firefox/3.0.10";
    private int maxTotalConnections = 2;
    protected int maxConnectionsPerRoute = 1;
    private long timeout = 20000;

    private ApplicationContext appCtx;

    private Abdera abdera;

    @Override
    public void afterPropertiesSet() {
        HttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        ConnManagerParams.setMaxTotalConnections(params, maxTotalConnections);
        ConnManagerParams.setTimeout(params, timeout);
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(maxConnectionsPerRoute));

        HttpConnectionParams.setSocketBufferSize(params, 8192);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(params, userAgent);

        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory
                .getSocketFactory(), 443));
        ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(params,
                schemeRegistry);
        
        client = new DefaultHttpClient(connectionManager, params);
    }

    @Override
    public ProcessResult process(CrawlURI curi) {
        logger.debug("process uri in httpProcessor. uri: " + curi.getUrl());

        FetchResult fetchResult = fetchFeedContent(curi.getUrl(), curi.getUserId());

        if(fetchResult != null){
            appCtx.publishEvent(new FeedFetchedEvent(this, fetchResult));
        }

        return ProcessResult.FINISH;
    }

    private FetchResult fetchFeedContent(String url, String userId){

        logger.info("begin to fetch url: " + url);

        HttpGet httpget = new HttpGet(url);
        HttpResponse response = null;
        try {
            response = client.execute(httpget);
            HttpEntity entity = response.getEntity();
            StatusLine statusLine = response.getStatusLine();
            int code = statusLine.getStatusCode();

            if (code == 200 || code == 201 || code == 202
                    || code == 203 || code == 206) {
                byte[] feedContent = EntityUtils.toByteArray(entity);
                entity.consumeContent();
                Document<Feed> doc = abdera.getParser().parse(new ByteArrayInputStream(feedContent));
                Feed feed = doc.getRoot();
                Date crawltime = new Date();
                FetchResult fetchResult = new FetchResult(userId,
                        url,
                        feed,
                        crawltime);
                return fetchResult;
            } else {
                return null;
            }
        } catch (IOException e) {
            logger.warn("failed to fetch url: " + url);
            e.printStackTrace();
            return null;
        } finally{
             httpget.abort();
        }
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setAbdera(Abdera abdera) {
        this.abdera = abdera;
    }

    public void shutDown(){
        client.getConnectionManager().shutdown();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appCtx = applicationContext;
    }
}
