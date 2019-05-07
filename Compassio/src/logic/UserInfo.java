package logic;

/**
 * A class for storing the info of a user
 * 
 * @author madsh
 */
public class UserInfo {
    private long id;
    private String username;
    private String name;
    private String type;
    private boolean inactive;

    public UserInfo(long id, String username, String name, String type, boolean inactive) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.type = type;
        this.inactive = inactive;
    }

    /**
     * Gets the username of the user
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the full name of the user
     * @return The full name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Gets wether the users is currently inactive
     * @return The state of the user
     */
    public boolean isInactive() {
        return inactive;
    }

    /**
     * Sets the inactive state of a user
     * @param active The new inactive state
     */
    public void setInactive(boolean active) {
        this.inactive = active;
    }

    /**
     * Gets the id of the user
     * @return The id of the user
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the type of the user
     * @return The type of the user
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the user
     * @param type The new type of the user
     */
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return username + " - " + name;
    }
    
    
}