package readerside.crawler.service;

import java.util.List;

import readerside.model.EntryData;
import readerside.model.GrEntry;
import readerside.model.GrFeed;
import readerside.model.GrUser;
import readerside.model.SourceFeed;
import readerside.persistence.Page;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-27
 * Time: 21:10:56
 */
public interface ICrawlerService {
	
	public List<GrUser> getAllCnUsers();
	
	public List<GrEntry> getAllEntries();

    public GrUser getGrUser(String grUserId);

    public GrEntry getGrEntry(String grEntryId);
    
    public Page<GrEntry> getEntryPage(int pageIndex);

    public void saveGrUser(GrUser user);

    public void saveGrFeed(GrFeed grFeed);

    public void saveGrEntry(GrEntry grEntry);

    public void saveEntryData(EntryData data);

    public void saveSourceFeed(SourceFeed sourceFeed);

	GrFeed getGrFeed(String grFeedId);

	EntryData getEntryData(String grEntryId);

	SourceFeed getSourceFeed(String sourceFeedId);
}
