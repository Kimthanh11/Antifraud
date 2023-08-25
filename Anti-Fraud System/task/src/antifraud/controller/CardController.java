package antifraud.controller;

import antifraud.dto.request.CardRequest;
import antifraud.dto.response.CardResponse;
import antifraud.dto.response.DeleteCardResponse;
import antifraud.entity.Card;
import antifraud.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/antifraud")
public class CardController {
    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/stolencard")
    public ResponseEntity<?> saveStolenCard(@Validated @RequestBody CardRequest cardRequest){

        try {
            CardResponse cardResponse =  cardService.saveCard(cardRequest);

            return ResponseEntity.status(HttpStatus.OK).body(cardResponse);

        } catch (UnsupportedOperationException e) {
            if (("Card number already exists").equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // Ip already assigned
            } else if (("Wrong format").equals(e.getMessage())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Default error response
        }
    }

    @DeleteMapping("/stolencard/{number}")
    public ResponseEntity<?> deleteCard(@PathVariable String number){
        try {

            if (!CardService.isValidCardNumber(number)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
            DeleteCardResponse deleteCardResponse = cardService.deleteCard(number);
            return ResponseEntity.status(HttpStatus.OK).body(deleteCardResponse);
        } catch (UnsupportedOperationException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/stolencard")
    public List<Card> listCards() {
        return cardService.getAllCard();
    }
}
