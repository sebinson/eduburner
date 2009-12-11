package readerside.crawler.event;

import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-28
 * Time: 17:21:14
 */
public class UrlScheduleEvent extends ApplicationEvent {

    private Set<String> fetchedUserIds;
    private Set<String> newUserIds;

    public UrlScheduleEvent(Object source, Set<String> fetchedUserIds, Set<String> newUserIds) {
        super(source);
        this.fetchedUserIds = fetchedUserIds;
        this.newUserIds = newUserIds;

    }

    public Set<String> getNewUserIds() {
        return newUserIds;
    }

    public Set<String> getFetchedUserIds() {
        return fetchedUserIds;
    }
}
