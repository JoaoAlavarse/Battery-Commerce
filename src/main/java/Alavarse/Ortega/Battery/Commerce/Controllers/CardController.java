package Alavarse.Ortega.Battery.Commerce.Controllers;

import Alavarse.Ortega.Battery.Commerce.DTOs.CardDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.CardResponseDTO;
import Alavarse.Ortega.Battery.Commerce.DTOs.UpdateCardDTO;
import Alavarse.Ortega.Battery.Commerce.Entities.CardEntity;
import Alavarse.Ortega.Battery.Commerce.Services.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    private CardService service;

    @PostMapping
    public ResponseEntity<CardResponseDTO> create(@RequestBody @Valid CardDTO data){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.create(data));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CardResponseDTO>> getAllByUser(@PathVariable String userId){
        return ResponseEntity.ok().body(this.service.getAllByUser(userId));
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<HttpStatus> update(@PathVariable String cardId, @RequestBody @Valid UpdateCardDTO data){
        this.service.updateCard(data, cardId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String cardId) {
        this.service.delete(cardId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/main/{id}")
    public ResponseEntity<HttpStatus> setMainTrue(@PathVariable String cardId){
        this.service.setCardMainTrue(cardId);
        return ResponseEntity.noContent().build();
    }
}