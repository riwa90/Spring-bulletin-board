package se.kth.id1212.gladagrabbar.ID1212Project.repositories;

import se.kth.id1212.gladagrabbar.ID1212Project.model.PlacardBoard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlacardBoardRepository extends CrudRepository<PlacardBoard, String>{

	@Query("SELECT p FROM PlacardBoard p WHERE p.owner = :user")
	List<PlacardBoard> findByUser(@Param("user") User user);
	
	@Query("SELECT p.name FROM PlacardBoard p")
	List<String> findAllIds();
	
	@Query("SELECT p.members FROM PlacardBoard p WHERE p.name = :id")
	List<User> findAllMembers(@Param("id") String id);
	
	@Query("SELECT p.owner FROM PlacardBoard p WHERE p.name = :id")
	Optional<User> findOwner(@Param("id") String id);
}
