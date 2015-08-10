package beans;

import java.io.Serializable;

/**
 * Interface for identified objects
 */
public interface Identified<PK extends Serializable> {
    /** Returns object ID */
    public PK getid();
}
