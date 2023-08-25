package antifraud.controller;

import antifraud.dto.request.AddressRequest;
import antifraud.dto.response.AddressResponse;
import antifraud.dto.response.DeleteAddressResponse;
import antifraud.dto.response.DeleteCardResponse;
import antifraud.dto.response.UserDTO;
import antifraud.entity.Address;
import antifraud.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/antifraud")
public class AddressController {
    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService){
        this.addressService = addressService;
    }

    @PostMapping("/suspicious-ip")
    public ResponseEntity<?> saveSuspiciousIpAddress(@Validated @RequestBody AddressRequest addressRequest) {

        try {
            AddressResponse addressResponse =  addressService.saveSuspiciousIp(addressRequest);

            return ResponseEntity.status(HttpStatus.OK).body(addressResponse);

        } catch (UnsupportedOperationException e) {
            if (("Ip already exists").equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Ip already assigned
            } else if (("Wrong format").equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Default error response
        }
    }

    @DeleteMapping("/suspicious-ip/{ip}")
    public ResponseEntity<?> deleteSuspiciousIp(@PathVariable String ip) {
        try {
            String ipAddressRegex = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
            if (!ip.matches(ipAddressRegex)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            DeleteAddressResponse deleteAddressResponse = addressService.deleteSuspiciousIp(ip);
            return ResponseEntity.status(HttpStatus.OK).body(deleteAddressResponse);
        } catch (UnsupportedOperationException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/suspicious-ip")
    public List<Address> listIpAddresses() {
        return addressService.getAllAddress();
    }
}
