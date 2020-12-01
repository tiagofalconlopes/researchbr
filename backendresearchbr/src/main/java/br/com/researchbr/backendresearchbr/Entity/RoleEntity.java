package br.com.researchbr.backendresearchbr.Entity;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleType name;
    @Column(name = "description")
    private String description;


    public RoleType getName() {
        return name;
    }

    public void setName(RoleType name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
