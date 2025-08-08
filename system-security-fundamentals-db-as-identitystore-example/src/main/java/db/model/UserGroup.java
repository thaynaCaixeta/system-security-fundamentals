package db.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "user_groups")
public class UserGroup {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 64)
    private String username;

    @Column(name = "group_name", length = 64)
    private String groupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserGroup userGroup = (UserGroup) o;
        return Objects.equals(id, userGroup.id) && Objects.equals(username, userGroup.username) && Objects.equals(groupName, userGroup.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, groupName);
    }
}
