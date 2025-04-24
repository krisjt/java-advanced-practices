package pl.edu.pwr.library.application.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.pwr.library.application.models.dto.ClientDTO;
import pl.edu.pwr.library.application.models.payload.ClientPayload;
import pl.edu.pwr.library.database.models.Client;
import pl.edu.pwr.library.database.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ClientService {

    private final Logger logger = Logger.getLogger(ClientService.class.getName());
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<List<ClientDTO>> findAll() {
        try {
            List<Client> clients = clientRepository.findAll();
            List<ClientDTO> clientDTOS = new ArrayList<>();
            clients.forEach(client -> clientDTOS.add(new ClientDTO(client.getName(),client.getSurname())));
            logger.info("All clients were loaded.");
            return ResponseEntity.ok(clientDTOS);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while loading clients: ", e.getMessage());
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addClient(ClientDTO clientDTO) {
        try {
            if (clientDTO != null && clientDTO.name() != null && clientDTO.surname() != null) {
                Client client = new Client(clientDTO.name(),clientDTO.surname());
                clientRepository.save(client);
                logger.info("Client added: " + client);
                return ResponseEntity.ok("Client added");
            } else {
                logger.warning("Wrong client credentials.");
                return new ResponseEntity<>("Wrong client credentials",HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while loading clients: ", e.getMessage());
            return new ResponseEntity<>("Error while loading clients", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void addClient(String name, String surname) {
        try {
            if (name != null && !name.isBlank() && surname != null && !surname.isBlank()) {
                Client client = new Client(name, surname);
                clientRepository.save(client);
                logger.info("Client added: " + client);
            } else {
                logger.warning("Name and surname cannot be empty.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while adding client: ", e.getMessage());
        }
    }

    public void removeClient(Client client) {
        try {
            if (client != null && clientRepository.existsById(client.getId())) {
                clientRepository.delete(client);
                logger.info("Client deleted: " + client);
            } else {
                logger.warning("Client doesn't exist.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while deleting cleint: ", e.getMessage());
        }
    }

    public void removeClient(int id) {
        try {
            Optional<Client> client = clientRepository.findById(id);
            if (client.isPresent()) {
                clientRepository.delete(client.get());
                logger.info("Client deleted.");
            } else {
                logger.warning("Client coldn't be found");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while deleting client: " + e.getMessage());
        }
    }

    public Client findById(int id) {
        try {
            Optional<Client> client = clientRepository.findById(id);
            if (client.isPresent()) {
                logger.info("Client found");
                return client.get();
            } else {
                logger.warning("Client could be found.");
                return null;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while searching for client: ", e.getMessage());
            return null;
        }
    }

    public ResponseEntity<String> modifyClient(ClientPayload clientPayload) {
        try {
            Optional<Client> clientOptional = clientRepository.findById(clientPayload.id());
            if (clientOptional.isPresent()) {
                Client client = clientOptional.get();
                if (clientPayload.name() != null && !clientPayload.name().isBlank()) {
                    client.setName(clientPayload.name());
                }
                if (clientPayload.surname() != null && !clientPayload.surname().isBlank()) {
                    client.setSurname(clientPayload.surname());
                }
                clientRepository.save(client);
                logger.info("Client modified.");
                return ResponseEntity.ok("Client modified");
            } else {
                logger.warning("Client couldn't be found.");
                return new ResponseEntity<>("Client couldn't be found",HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while modifying client: ", e.getMessage());
            return new ResponseEntity<>("Error while modifying client", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ClientDTO> findDTOById(int id) {
        try {
            Optional<Client> client = clientRepository.findById(id);
            if (client.isPresent()) {
                logger.info("Client found");
                return new ResponseEntity<>(new ClientDTO(client.get().getName(),client.get().getSurname()),HttpStatus.OK);
            } else {
                logger.warning("Client could be found.");
                return new ResponseEntity<>(new ClientDTO(null,null),HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while searching for client: ", e.getMessage());
            return new ResponseEntity<>(new ClientDTO(null,null),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
