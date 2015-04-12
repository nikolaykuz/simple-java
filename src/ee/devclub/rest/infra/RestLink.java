package ee.devclub.rest.infra;

import javax.ws.rs.core.UriInfo;
import java.util.LinkedHashMap;


public class RestLink extends LinkedHashMap<String, Object> {
    public static final String SLASH = "/";
    public static final String HREF = "href";

    public RestLink(UriInfo info, AbstractEntity entity) {
        this(getFullyQualifiedContextPath(info), entity);
    }

    public RestLink(String fqBasePath, AbstractEntity entity) {
        String href = createHref(fqBasePath, entity);
        put(HREF, href);
    }

/*
    public RestLink(UriInfo info, String subPath) {
        this(getFullyQualifiedContextPath(info), subPath);
    }
*/
/*
    public RestLink(String fqBasePath, String subPath) {
        String href = fqBasePath + subPath;
        put(HREF, href);
    }
*/
    protected static String getFullyQualifiedContextPath(UriInfo info) {
        String fqPath = info.getBaseUri().toString();
        if (fqPath.endsWith(SLASH)) {
            return fqPath.substring(0, fqPath.length() - 1);
        }
        return fqPath;
    }

    protected String createHref(String fqBasePath, AbstractEntity entity) {
        StringBuilder sb = new StringBuilder(fqBasePath);
        PathHelper path = PathHelper.forClass(entity.getClass());
        sb.append(path.getPath()).append(SLASH).append(entity.getId());
        return sb.toString();
    }

    public String getHref() {
        return (String)get("href");
    }

}
