package persistence.dataaccessobjects;

import java.util.List;

/**
 * The purpose of this interface is to provide access to the persistent storage.
 * The methods provided in this interface are for creating, retrieving,
 * updating, and deleting data.
 * @author Morten Kargo Lyngesen
 * @param <T>
 */
public interface DataAccessInterface <T> {

    /**
     *
     * @param id
     * @return
     */
    T get (String id);

    /**
     *
     * @return
     */
    List <T> getAll();

    /**
     *
     * @param args
     * @return
     */
    boolean create (String[] args);

    /**
     *
     * @param id
     * @param args
     * @return
     */
    boolean update (String id, String[] args);
    
    /**
     *
     * @param id
     * @return
     */
    boolean delete (String id);
}