package com.its.service;

import com.its.entity.CardEntity;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class CardService {
    private final ReactiveRedisOperations<String, CardEntity> cardOps;

    public CardService(ReactiveRedisOperations<String, CardEntity> cardOps) {
        this.cardOps = cardOps;
    }


    /*public Flux<CardEntity> getCardsByCountry(String country) {
        return cardOps
                    .keys(c)
    }*/

    public Mono<CardEntity> getCardById(String cardId) {
        log.info("Entering CardService : getCardById with parameter cardId - {}" , cardId);
        return cardOps
                .keys(cardId)
                .flatMap(this::apply)
                .take(1)
                .next();
    }

    private Publisher< CardEntity> apply(String s) {
        return cardOps.opsForValue().get(s);
    }

    public Flux<CardEntity> getCards() {
        log.info("Entering CardService : getCards");
        return cardOps
                .keys("*")
                .flatMap(this::apply);
    }


}
