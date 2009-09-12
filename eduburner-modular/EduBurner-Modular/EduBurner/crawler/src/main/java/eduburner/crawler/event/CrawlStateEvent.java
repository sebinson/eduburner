package eduburner.crawler.event;

import org.springframework.context.ApplicationEvent;

import eduburner.crawler.Crawler.State;

public class CrawlStateEvent extends ApplicationEvent {
    private static final long serialVersionUID = 1L;
    protected State state;
    protected String message;

    public CrawlStateEvent(Object source, State state, String message) {
        super(source);
        this.state = state;
        this.message = message; 
    }

    public String getMessage() {
        return message;
    }

    public State getState() {
        return state;
    }
}
