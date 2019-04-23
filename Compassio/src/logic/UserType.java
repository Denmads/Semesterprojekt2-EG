package logic;

/**
 * Represents the roles/type any given user could be.
 * 
 * @author Morten Kargo Lyngesen <mortenkargo@gmail.com>
 */
public enum UserType {
    /**
     * This is the default user. This user has no editing rights and can only
     * see their own information
     */
    USER,

    /**
     * Represents a social worker at a institution. This user can only see the
     * cases related to their specific department.
     */
    SOCIALWORKER,
    
    /**
     * Represents a caseworker that works for the municipality. This user can
     * only see the the cases that they're assigned to. They have the right to
     * create and edit new cases. As well as closing existing ones.
     */
    CASEWORKER,

    /**
     * Represents system administrators. System admins can't see any cases. They
     * have the ability to change the passwords of the other users.
     */
    ADMIN
}
