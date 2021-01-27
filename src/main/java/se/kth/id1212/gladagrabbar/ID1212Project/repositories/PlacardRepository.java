package se.kth.id1212.gladagrabbar.ID1212Project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import se.kth.id1212.gladagrabbar.ID1212Project.model.Placard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.PlacardBoard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.User;

public interface PlacardRepository extends CrudRepository<Placard, Integer>{

	@Query("SELECT p FROM Placard p WHERE p.user = :user")
	List<Placard> findByUser(@Param("user") User user);
	
	@Query("SELECT p.placardBoard FROM Placard p WHERE p.id = :id")
	PlacardBoard findBoard(@Param("id") int id);
}
