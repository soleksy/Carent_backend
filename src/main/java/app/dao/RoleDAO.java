package app.dao;

import app.entity.RoleEntity;
import java.util.List;


public interface RoleDAO {

    RoleEntity getRole(int id);

    List<RoleEntity> getRoles();
}
