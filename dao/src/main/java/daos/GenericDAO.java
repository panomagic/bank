package daos;

import java.io.Serializable;
import java.util.List;

/**
 @param <T> persistence object type
 @param <PK> primary key type
 */
public interface GenericDAO<T, PK extends Serializable> {
    /** Creates new record and related object */
    T create() throws PersistException;

    /** Creates new record related to object */
    T persist(T object) throws PersistException;

    /** Returns an object with related PK 'key' or null */
    T getByPK(PK key) throws PersistException;

    /** Saves present object status to DB */
    void update(T object) throws PersistException;

    /** Deletes the record about the object from DB */
    void delete(T object) throws PersistException;

    /** Returns object list of all related records in the DB */
    List<T> getAll() throws PersistException;
}