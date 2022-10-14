package app.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerDao extends CrudRepository<CustomerEntity, String> {

}
