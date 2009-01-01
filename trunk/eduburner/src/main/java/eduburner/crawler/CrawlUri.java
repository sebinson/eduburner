package eduburner.crawler;

import static eduburner.crawler.FetchStatusCodes.*;

public class CrawlUri {
	
	public static String fetchStatusCodesToString(int code){
        switch(code){
            // DNS
            case S_DNS_SUCCESS : return "DNS-1-OK";
            // HTTP Informational 1xx
            case 100  : return "HTTP-100-Info-Continue";
            case 101  : return "HTTP-101-Info-Switching Protocols";
            // HTTP Successful 2xx
            case 200  : return "HTTP-200-Success-OK";
            case 201  : return "HTTP-201-Success-Created";
            case 202  : return "HTTP-202-Success-Accepted";
            case 203  : return "HTTP-203-Success-Non-Authoritative";
            case 204  : return "HTTP-204-Success-No Content ";
            case 205  : return "HTTP-205-Success-Reset Content";
            case 206  : return "HTTP-206-Success-Partial Content";
            // HTTP Redirection 3xx
            case 300  : return "HTTP-300-Redirect-Multiple Choices";
            case 301  : return "HTTP-301-Redirect-Moved Permanently";
            case 302  : return "HTTP-302-Redirect-Found";
            case 303  : return "HTTP-303-Redirect-See Other";
            case 304  : return "HTTP-304-Redirect-Not Modified";
            case 305  : return "HTTP-305-Redirect-Use Proxy";
            case 307  : return "HTTP-307-Redirect-Temporary Redirect";
            // HTTP Client Error 4xx
            case 400  : return "HTTP-400-ClientErr-Bad Request";
            case 401  : return "HTTP-401-ClientErr-Unauthorized";
            case 402  : return "HTTP-402-ClientErr-Payment Required";
            case 403  : return "HTTP-403-ClientErr-Forbidden";
            case 404  : return "HTTP-404-ClientErr-Not Found";
            case 405  : return "HTTP-405-ClientErr-Method Not Allowed";
            case 407  : return "HTTP-406-ClientErr-Not Acceptable";
            case 408  : return "HTTP-407-ClientErr-Proxy Authentication Required";
            case 409  : return "HTTP-408-ClientErr-Request Timeout";
            case 410  : return "HTTP-409-ClientErr-Conflict";
            case 406  : return "HTTP-410-ClientErr-Gone";
            case 411  : return "HTTP-411-ClientErr-Length Required";
            case 412  : return "HTTP-412-ClientErr-Precondition Failed";
            case 413  : return "HTTP-413-ClientErr-Request Entity Too Large";
            case 414  : return "HTTP-414-ClientErr-Request-URI Too Long";
            case 415  : return "HTTP-415-ClientErr-Unsupported Media Type";
            case 416  : return "HTTP-416-ClientErr-Requested Range Not Satisfiable";
            case 417  : return "HTTP-417-ClientErr-Expectation Failed";
            // HTTP Server Error 5xx
            case 500  : return "HTTP-500-ServerErr-Internal Server Error";
            case 501  : return "HTTP-501-ServerErr-Not Implemented";
            case 502  : return "HTTP-502-ServerErr-Bad Gateway";
            case 503  : return "HTTP-503-ServerErr-Service Unavailable";
            case 504  : return "HTTP-504-ServerErr-Gateway Timeout";
            case 505  : return "HTTP-505-ServerErr-HTTP Version Not Supported";
            // Heritrix internal codes (all negative numbers
            case S_BLOCKED_BY_USER:
                return "Heritrix(" + S_BLOCKED_BY_USER + ")-Blocked by user";
            case S_BLOCKED_BY_CUSTOM_PROCESSOR:
                return "Heritrix(" + S_BLOCKED_BY_CUSTOM_PROCESSOR +
                ")-Blocked by custom prefetch processor";
            case S_DELETED_BY_USER:
                return "Heritrix(" + S_DELETED_BY_USER + ")-Deleted by user";
            case S_CONNECT_FAILED:
                return "Heritrix(" + S_CONNECT_FAILED + ")-Connection failed";
            case S_CONNECT_LOST:
                return "Heritrix(" + S_CONNECT_LOST + ")-Connection lost";
            case S_DEEMED_CHAFF:
                return "Heritrix(" + S_DEEMED_CHAFF + ")-Deemed chaff";
            case S_DEFERRED:
                return "Heritrix(" + S_DEFERRED + ")-Deferred";
            case S_DOMAIN_UNRESOLVABLE:
                return "Heritrix(" + S_DOMAIN_UNRESOLVABLE
                        + ")-Domain unresolvable";
            case S_OUT_OF_SCOPE:
                return "Heritrix(" + S_OUT_OF_SCOPE + ")-Out of scope";
            case S_DOMAIN_PREREQUISITE_FAILURE:
                return "Heritrix(" + S_DOMAIN_PREREQUISITE_FAILURE
                        + ")-Domain prerequisite failure";
            case S_ROBOTS_PREREQUISITE_FAILURE:
                return "Heritrix(" + S_ROBOTS_PREREQUISITE_FAILURE
                        + ")-Robots prerequisite failure";
            case S_OTHER_PREREQUISITE_FAILURE:
                return "Heritrix(" + S_OTHER_PREREQUISITE_FAILURE
                        + ")-Other prerequisite failure";
            case S_PREREQUISITE_UNSCHEDULABLE_FAILURE:
                return "Heritrix(" + S_PREREQUISITE_UNSCHEDULABLE_FAILURE
                        + ")-Prerequisite unschedulable failure";
            case S_ROBOTS_PRECLUDED:
                return "Heritrix(" + S_ROBOTS_PRECLUDED + ")-Robots precluded";
            case S_RUNTIME_EXCEPTION:
                return "Heritrix(" + S_RUNTIME_EXCEPTION
                        + ")-Runtime exception";
            case S_SERIOUS_ERROR:
                return "Heritrix(" + S_SERIOUS_ERROR + ")-Serious error";
            case S_TIMEOUT:
                return "Heritrix(" + S_TIMEOUT + ")-Timeout";
            case S_TOO_MANY_EMBED_HOPS:
                return "Heritrix(" + S_TOO_MANY_EMBED_HOPS
                        + ")-Too many embed hops";
            case S_TOO_MANY_LINK_HOPS:
                return "Heritrix(" + S_TOO_MANY_LINK_HOPS
                        + ")-Too many link hops";
            case S_TOO_MANY_RETRIES:
                return "Heritrix(" + S_TOO_MANY_RETRIES + ")-Too many retries";
            case S_UNATTEMPTED:
                return "Heritrix(" + S_UNATTEMPTED + ")-Unattempted";
            case S_UNFETCHABLE_URI:
                return "Heritrix(" + S_UNFETCHABLE_URI + ")-Unfetchable URI";
            case S_PROCESSING_THREAD_KILLED:
                return "Heritrix(" + S_PROCESSING_THREAD_KILLED + ")-" +
                    "Processing thread killed";
            // Unknown return code
            default : return Integer.toString(code);
        }
    }

}
