package persistence.dataaccessobjects;

import java.util.List;

/**
 * The purpose of this interface is to provide access to the persistent storage.
 * The methods provided in this interface are for creating, retrieving,
 * updating, and deleting data.
 * @author Morten Kargo Lyngesen
 */
public interface DataAccessObject {

    /**
     *
     * @param id
     * @return
     */
    String[] get (long id);

    /**
     *
     * @return
     */
    List <String[]> getAll();

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
    boolean update (long id, String[] args);
    
    /**
     *
     * @param id
     * @return
     */
    boolean delete (long id);
}