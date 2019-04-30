package logic;

import java.util.ArrayList;

/**
 * Represents the roles/type any given user could be.
 * 
 * @author Morten Kargo Lyngesen <mortenkargo@gmail.com>
 */
public class UserType {
    
        private ArrayList<String> list;
    
        public UserType (String[] names) {
            list = new ArrayList<>();
            
            for (String n : names) {
                list.add(n);
            }
        }
        
        public int get (String type) {
            return list.indexOf(type);
        }
        
        public String getName (int index) {
            return list.get(index);
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
