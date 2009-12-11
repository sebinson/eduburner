package readerside.crawler.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import readerside.model.*;
import readerside.persistence.IDao;
import readerside.persistence.Page;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-27
 * Time: 21:11:11
 */
@Service("crawlerService")
@Transactional
public class DefaultCrawlerService implements ICrawlerService{
    private static final Logger logger = LoggerFactory.getLogger(DefaultCrawlerService.class);

    @Autowired
    @Qualifier("dao")
    private IDao dao;

    @Override
    public GrUser getGrUser(String grUserId) {
        return dao.findUniqueBy(GrUser.class, "grUserId", grUserId);
    }

    @Override
    public GrEntry getGrEntry(String grEntryId) {
        return dao.findUniqueBy(GrEntry.class,"grEntryId", grEntryId);
    }
    
    @Override
    public GrFeed getGrFeed(String grFeedId){
    	return dao.findUniqueBy(GrFeed.class, "grFeedId", grFeedId);
    }
    
    @Override
    public EntryData getEntryData(String grEntryId){
    	return dao.findUniqueBy(EntryData.class, "grEntryId", grEntryId);
    }
    
    @Override
    public SourceFeed getSourceFeed(String sourceFeedId){
    	return dao.findUniqueBy(SourceFeed.class, "grSourceFeedId", sourceFeedId);
    }

    @Override
    public void saveGrUser(GrUser user) {
        dao.saveOrUpdate(user);
    }

    @Override
    public void saveGrFeed(GrFeed grFeed) {
        dao.saveOrUpdate(grFeed);
    }

    @Override
    public void saveGrEntry(GrEntry grEntry) {
       dao.saveOrUpdate(grEntry);
    }

    @Override
    public void saveEntryData(EntryData data) {
        dao.saveOrUpdate(data);
    }

    @Override
    public void saveSourceFeed(SourceFeed sourceFeed) {
        dao.saveOrUpdate(sourceFeed);
    }

	@Override
	public List<GrUser> getAllCnUsers() {
		return dao.findBy(GrUser.class, "cnUser", true);
	}

	@Override
	public Page<GrEntry> getEntryPage(int pageIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GrEntry> getAllEntries() {
		return dao.getAllInstances(GrEntry.class);
	}
}
