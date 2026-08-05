package pe.edu.anespiju.empresabackend.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.edu.anespiju.empresabackend.model.entity.Client;
import pe.edu.anespiju.empresabackend.repository.ClientRepository;

@Service
public class ClientService {

  private final ClientRepository clientRepository;

  public ClientService(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  public Page<Client> listar(int pagina, int tamanio) {
    if (pagina < 0) {
      pagina = 0;
    }
    if (tamanio < 0) {
      tamanio = 0;
    }
    Pageable pageable = PageRequest.of(pagina, tamanio);
    return clientRepository.findAll(pageable);
  }

  public Client crear(Client client) {
    clientRepository.findByEmail(client.getEmail()).ifPresent(_ -> {
      throw new IllegalArgumentException("Ya existe un cliente con el correo electrónico");
    });
    return clientRepository.save(client);
  }

  public Client actualizar(Long id, Client client) {
    var clienteActual = clientRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    clienteActual.setEmail(client.getEmail());
    clienteActual.setName(client.getName());
    return clientRepository.save(clienteActual);
  }

  public void eliminar(Long id) {
    if (!clientRepository.existsById(id)) {
      throw new IllegalArgumentException("Cliente no encontrado");
    }
    clientRepository.deleteById(id);
  }

  public Optional<Client> findById(Long id) {
    return clientRepository.findById(id);
  }
}
