package pe.edu.anespiju.empresabackend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.anespiju.empresabackend.model.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

  Optional<Client> findByEmail(String email);
}
