package pl.edu.pwr.library.application.service;

import org.springframework.stereotype.Service;
import pl.edu.pwr.library.database.models.Client;
import pl.edu.pwr.library.database.repository.ClientRepository;

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

    public List<Client> findAll() {
        try {
            List<Client> clients = clientRepository.findAll();
            logger.info("All clients were loaded.");
            return clients;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while loading clients: ", e.getMessage());
            return List.of();
        }
    }

    public void addClient(Client client) {
        try {
            if (client != null && client.getName() != null && client.getSurname() != null) {
                clientRepository.save(client);
                logger.info("Client added: " + client);
            } else {
                logger.warning("Wrong client credentials.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while loading clients: ", e.getMessage());
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

    public void modifyClient(int id, String name, String surname) {
        try {
            Optional<Client> clientOptional = clientRepository.findById(id);
            if (clientOptional.isPresent()) {
                Client client = clientOptional.get();
                if (name != null && !name.isBlank()) {
                    client.setName(name);
                }
                if (surname != null && !surname.isBlank()) {
                    client.setSurname(surname);
                }
                clientRepository.save(client);
                logger.info("Client modified.");
            } else {
                logger.warning("Client couldn't be found.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while modifying client: ", e.getMessage());
        }
    }
}
