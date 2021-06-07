package team.alabs.shop.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Role {
    @Id
    private String role;
}
