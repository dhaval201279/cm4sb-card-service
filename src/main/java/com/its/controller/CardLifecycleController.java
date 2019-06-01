package com.its.controller;

import com.its.entity.CardEntity;
import com.its.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class CardLifecycleController {
    private final CardService cardService;

    public CardLifecycleController(CardService cardService) {
        this.cardService = cardService;
    }

    /*@GetMapping("/cards/country/{country}")
    public Flux<CardEntity> byCountry(@PathVariable String country) {
        log.info("Entering CardLifecycleController : byCountry");
        return cardService.getCardsByCountry(country);
    }*/

    @GetMapping("/card/{cardId}")
    public Mono<CardEntity> byCardId(@PathVariable String cardId) {
        log.info("Entering CardLifecycleController : byCardId");
        return cardService.getCardById(cardId);
    }

    @GetMapping("/cards")
    public Flux<CardEntity> all() {
        log.info("Entering CardLifecycleController : all");
        return cardService.getCards();
    }
}
