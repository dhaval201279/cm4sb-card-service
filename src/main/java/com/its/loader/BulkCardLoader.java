package com.its.loader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.entity.CardEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class BulkCardLoader {

    private final ReactiveRedisConnectionFactory factory;
    private final ReactiveRedisOperations<String, CardEntity> cardOps;
    private ObjectMapper objectMapper;

    public BulkCardLoader(ReactiveRedisConnectionFactory factory, ReactiveRedisOperations<String, CardEntity> cardOps, ObjectMapper objectMapper) {
        this.factory = factory;
        this.cardOps = cardOps;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void loadData() {
        log.info("Importing creditCard list");

        // Flush anything that was previously in Redis
        factory.getReactiveConnection().serverCommands().flushAll().subscribe();
        log.info("Existing data flushed successfully");

        ClassLoader classLoader = getClass().getClassLoader();
        try (Reader in = new InputStreamReader(
                Objects.requireNonNull(classLoader.getResource("new-bulk-card-data.json")).openStream(), "UTF-8")) {
                List<CardMetaData> records = objectMapper.readValue(in, new TypeReference<List<CardMetaData>>() {
            });

            log.info("Record count read from file {} ", records.size());
            records
                .stream()
                .map(CardMetaData::getCreditCard)
                .forEach(this::accept);
            log.info("Bulk creditCard data imported successfully");
        } catch (Exception e) {
            log.error("Exception occurred whilst importing bulk creditCard data - message {} and stacktrace {}", e.getMessage(), e.getStackTrace());
        }
    }

    private void accept(CreditCard aCreditCard) {
        cardOps
            .opsForValue()
            .set(aCreditCard.getCardNumber(), CardEntity.builder()
                    .address(aCreditCard.getAddress())
                    .cardNumber(aCreditCard.getCardNumber())
                    .country(aCreditCard.getCountry())
                    .cvv(aCreditCard.getCvv())
                    .expiryDate(aCreditCard.getExpiryDate())
                    .issuingNetwork(aCreditCard.getIssuingNetwork())
                    .name(aCreditCard.getName())
                    .build()
            )
            .subscribe();
    }
}
