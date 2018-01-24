package softuniblog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniblog.demo.entity.Role;


public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findByName(String name);
}
