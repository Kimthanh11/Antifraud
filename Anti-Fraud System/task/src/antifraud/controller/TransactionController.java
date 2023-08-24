package antifraud.controller;

import antifraud.dto.request.TransactionRequest;
import antifraud.dto.response.TransactionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @PostMapping("/api/antifraud/transaction")
    public ResponseEntity<TransactionResponseDTO> postTransaction(@RequestBody TransactionRequest request) {
        Long amount = request.getAmount();
        TransactionResponseDTO responseDTO = new TransactionResponseDTO();

        if (amount == null || amount <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (amount <= 200) {
            responseDTO.setResult("ALLOWED");
        } else if (amount <= 1500) {
            responseDTO.setResult("MANUAL_PROCESSING");
        } else {
            responseDTO.setResult("PROHIBITED");
        }

        return ResponseEntity.ok(responseDTO);
    }
}
