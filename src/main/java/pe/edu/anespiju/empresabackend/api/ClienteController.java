package pe.edu.anespiju.empresabackend.api;

import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.anespiju.empresabackend.model.entity.Client;
import pe.edu.anespiju.empresabackend.service.ClientService;

@RestController
@RequestMapping("/clientes")
@PreAuthorize("hasAuthority('APPROLE_Admin')")
public class ClienteController {

  private final ClientService clientService;

  public ClienteController(ClientService clientService) {
    this.clientService = clientService;
  }

  @GetMapping
  public ResponseEntity<Page<Client>> listar(
      @RequestParam(name = "pagina", defaultValue = "0") int pagina,
      @RequestParam(name = "tamanio", defaultValue = "2") int tamanio) {
    Page<Client> resultado = clientService.listar(pagina, tamanio);
    return ResponseEntity.ok(resultado);
  }

  @GetMapping(value = "{id}")
  public ResponseEntity<Client> findById(@PathVariable Long id) {
    return clientService.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Client> save(@RequestBody @Valid Client client) {
    var clienteCreado = clientService.crear(client);
    return ResponseEntity.created(URI.create("/clientes/" + clienteCreado.getId()))
        .body(clienteCreado);
  }

  @PutMapping(value = "{id}")
  public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
    try {
      return ResponseEntity.ok(clientService.actualizar(id, client));

    } catch (IllegalArgumentException _) {
      return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping(value = "{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    try {
      clientService.eliminar(id);
      return ResponseEntity.noContent().build();
    } catch (IllegalArgumentException _) {
      return ResponseEntity.notFound().build();
    }
  }
}
