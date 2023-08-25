package antifraud.service;

import antifraud.dto.request.AddressRequest;
import antifraud.dto.response.AddressResponse;
import antifraud.dto.response.DeleteAddressResponse;
import antifraud.entity.Address;
import antifraud.persistence.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    //saveSuspiciousIp
    public AddressResponse saveSuspiciousIp(AddressRequest addressRequest) {
        if (addressRepository.existsByIp(addressRequest.getIp())) {
            throw new UnsupportedOperationException("Ip already exists");
        }
        String ipAddressRegex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        if (!addressRequest.getIp().matches(ipAddressRegex)) {
            throw new UnsupportedOperationException("Wrong format");
        }

        Address address = new Address(addressRequest.getIp());
        addressRepository.save(address);
        return new AddressResponse(address.getId(), address.getIp());
    }

    //deleteSuspiciousIp
    public DeleteAddressResponse deleteSuspiciousIp(String ipAddress){
        Address ip = addressRepository.findByIp(ipAddress).orElse(null);

        if (ip != null) {
            addressRepository.delete(ip);
        } else {
            throw new UnsupportedOperationException("ADDRESS NOT FOUND");
        }

        return new DeleteAddressResponse(ipAddress);
    }


    //getSuspiciousIps
    public List<Address> getAllAddress() {
        return (List<Address>) addressRepository.findAll();
    }

}
