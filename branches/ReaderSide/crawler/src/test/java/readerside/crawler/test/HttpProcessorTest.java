package readerside.crawler.test;

import org.apache.abdera.Abdera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import readerside.crawler.model.CrawlURI;
import readerside.crawler.processor.HttpProcessor;
import readerside.crawler.processor.IProcessor;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-29
 * Time: 18:40:13
 */
public class HttpProcessorTest{



    String[] urls = {
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/",
            "http://www.google.com/",
            "http://www.baidu.com/",
            "http://www.163.com/",
            "http://www.youdao.com/",
            "http://www.javaeye.com/",
            "http://map.sogou.com/",
            "http://www.jetbrains.com/idea/download/index.html",
            "http://www.google.cn/search?hl=en&q=%E5%8C%97%E4%BA%AC+%E5%A4%A9%E6%B0%94&sourceid=navclient-ff&rlz=1B3GGGL_enCN348CN349&ie=UTF-8",
            "http://www.cnblogs.com/bqrm/archive/2008/08/16/1269258.html",
            "http://nzinfo.spaces.live.com/Blog/cns!67694E0B61E3E8D2!344.entry",
            "http://technology.chtsai.org/mmseg/"
    };

    //@Test
    public void testProcessHttp(){

        HttpProcessor processor = new HttpProcessor();
        processor.afterPropertiesSet();
        processor.setAbdera(new Abdera());
        
        int count = 12;
        for(String url: urls){

            CrawlURI curi = new CrawlURI(new Integer(count++).toString());
            curi.setUrl(url);

            processor.process(curi);
        }
    }
}