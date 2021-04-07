package app.service;


import app.entity.RoleEntity;
import java.util.List;

public interface RoleService {

    RoleEntity getRole(int id);

    List<RoleEntity> getRoles();

}
