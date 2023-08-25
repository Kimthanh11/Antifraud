package antifraud.service;

import antifraud.persistence.AddressRepository;
import antifraud.persistence.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private AddressRepository addressRepository;
    private CardRepository cardRepository;

    @Autowired
    public TransactionService(AddressRepository addressRepository, CardRepository cardRepository) {
        this.addressRepository = addressRepository;
        this.cardRepository = cardRepository;
    }

    public boolean isBlacklistedIp(String ip) {
        // Implement the logic to check if the IP address is blacklisted
        return addressRepository.existsByIp(ip);
    }

    public boolean isBlacklistedCard(String cardNumber) {
        // Implement the logic to check if the card number is blacklisted
        return cardRepository.existsByNumber(cardNumber);
    }
}
