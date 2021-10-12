package main;
import main.model.Socks;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SocksRepositiry extends CrudRepository<Socks,Integer>
{

}
