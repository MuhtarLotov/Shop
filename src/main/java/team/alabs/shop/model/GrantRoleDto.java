package team.alabs.shop.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class GrantRoleDto {
    @NotNull
    private Integer entityId;
    @NotNull
    private String role;

    public GrantRoleDto(Integer entityId, String role) {
        this.entityId = entityId;
        this.role = role;
    }
}