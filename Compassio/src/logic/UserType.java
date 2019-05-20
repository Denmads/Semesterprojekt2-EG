package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A utillity class, for representing all usertypes in the database, converting between id and name
 * 
 * @author Morten Kargo Lyngesen
 */
public class UserType {
    
        private HashMap<Integer, String> types;
    
        public UserType (String[] names) {
            types = new HashMap<>();
            
            for (String n : names) {
                String[] tokens = n.split(",");
                
                types.put(Integer.parseInt(tokens[0]), tokens[1]);
            }
        }
        
        /**
         * Gets the id of a type from the type name
         * @param type The name of the type
         * @return The id of the type
         */
        public int get (String type) {
            for (Map.Entry<Integer, String> ent : types.entrySet() ) {
                if (ent.getValue().equals(type)) {
                    return ent.getKey();
                }
            }
            
            return -1;
        }
        
        /**
         * Gets the name of a type from an id
         * @param index The id of the type
         * @return The name of the type
         */
        public String getName (int index) {
            return types.get(index);
        }
        
        /**
         * Gets an array of all the types
         * @return An array of all types
         */
        public String[] getTypes () {
            String[] allTypes = new String[types.size()];
            int count = 0;
            
            for (Map.Entry<Integer, String> ent : types.entrySet() ) {
                allTypes[count++] = ent.getValue();
            } 
            
            return allTypes;
        }
        
//    /**
//     * This is the default user. This user has no editing rights and can only
//     * see their own information
//     */
//    USER,
//
//    /**
//     * Represents a social worker at a institution. This user can only see the
//     * cases related to their specific department.
//     */
//    SOCIALWORKER,
//    
//    /**
//     * Represents a caseworker that works for the municipality. This user can
//     * only see the the cases that they're assigned to. They have the right to
//     * create and edit new cases. As well as closing existing ones.
//     */
//    CASEWORKER,
//
//    /**
//     * Represents system administrators. System admins can't see any cases. They
//     * have the ability to change the passwords of the other users.
//     */
//    ADMIN,
//    
//    /**
//     * If the user type is unrecognizable or an error happens the usertype gets set to this
//     */
//    UNKNOWN
}
