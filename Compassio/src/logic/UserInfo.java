/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean active) {
        this.inactive = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return username + " - " + name;
    }
    
    
}
