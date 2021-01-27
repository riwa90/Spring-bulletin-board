package se.kth.id1212.gladagrabbar.ID1212Project.repositories;

import org.springframework.data.repository.CrudRepository;
import se.kth.id1212.gladagrabbar.ID1212Project.model.User;

public interface UserRepository extends CrudRepository<User, String>{

}
