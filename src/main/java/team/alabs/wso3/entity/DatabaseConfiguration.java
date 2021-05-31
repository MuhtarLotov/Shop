package team.alabs.wso3.entity;

import javax.persistence.*;

import lombok.*;
import lombok.Data;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseConfiguration {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String connectionString;
    private String username;
    private String password;
}
