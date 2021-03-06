package daos;

/** Object factory for working with DB */
public interface DAOFactory<Context> {

    interface DAOCreator<Context> {
        GenericDAO create(Context context);
    }

    /** Returns an object for object persistence management */
    GenericDAO getDAO(Context context, Class dtoClass) throws PersistException;
}