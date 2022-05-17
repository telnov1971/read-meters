package ru.omel.rm.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Table;

import ru.omel.rm.data.Role;

@Entity
@Table(name = "application_user")
public class User extends AbstractEntity {
    private String username;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    private boolean changePassword = false;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String hashedPassword) {
        this.password = hashedPassword;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public boolean isChangePassword() {
        return changePassword;
    }
    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }
}
