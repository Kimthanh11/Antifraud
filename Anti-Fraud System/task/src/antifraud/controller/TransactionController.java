package antifraud.controller;

import antifraud.dto.request.TransactionRequest;
import antifraud.dto.response.TransactionResponseDTO;
import antifraud.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/antifraud/transaction")
@Validated
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> postTransaction(@RequestBody TransactionRequest request) {
        Long amount = request.getAmount();
        TransactionResponseDTO responseDTO = new TransactionResponseDTO();

        if (amount == null || amount <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // Check IP and Card number against blacklist
        boolean isIpBlacklisted = transactionService.isBlacklistedIp(request.getIp());
        boolean isCardNumberBlacklisted = transactionService.isBlacklistedCard(request.getNumber());

        List<String> infoList = new ArrayList<>();


        if (amount <= 200) {
            responseDTO.setResult("ALLOWED");
        } else if (amount <= 1500) {
            responseDTO.setResult("MANUAL_PROCESSING");

            if (!(isIpBlacklisted || isCardNumberBlacklisted)) {
                infoList.add("amount");
            }

        } else {
            responseDTO.setResult("PROHIBITED");
            infoList.add("amount");
        }

        if (isIpBlacklisted && isCardNumberBlacklisted) {
            responseDTO.setResult("PROHIBITED");
            infoList.add("card-number");
            infoList.add("ip");
        } else if (isIpBlacklisted) {
            responseDTO.setResult("PROHIBITED");
            infoList.add("ip");
        } else if (isCardNumberBlacklisted) {
            responseDTO.setResult("PROHIBITED");
            infoList.add("card-number");
        }



        if (infoList.isEmpty()) {
            responseDTO.setInfo("none");
        } else {
            responseDTO.setInfo(String.join(", ", infoList));
        }

        return ResponseEntity.ok(responseDTO);
    }
}
