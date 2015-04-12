package ee.devclub.rest.infra;

import ee.devclub.model.PhotoSpot;
import ee.devclub.rest.PhotoSpotResource;

public enum PathHelper {
    PHOTOSPOT(PhotoSpotResource.ROOT_PATH, PhotoSpot.class);

    private final String path;
    private final Class<? extends AbstractEntity> associatedClass;

    PathHelper(String elt, Class<? extends AbstractEntity> clazz) {
        path = elt;
        associatedClass = clazz;
    }

    public static PathHelper forClass(Class<? extends AbstractEntity> clazz) {
        for (PathHelper rp : values()) {
            //Cannot use equals because of hibernate proxied object
            //Cannot use instanceof because type not fixed at compile time
            if (rp.associatedClass.isAssignableFrom(clazz)) {
                return rp;
            }
        }
        throw new IllegalArgumentException("No ResourcePath for class '" + clazz.getName() + "'");
    }

    public String getPath() {
        return path;
    }

    public Class<? extends AbstractEntity> getAssociatedClass() {
        return associatedClass;
    }
}
