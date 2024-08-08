package com.hps.merchantservicingservice.controllers;



import com.hps.merchantservicingservice.dto.MerchantDTO;
import com.hps.merchantservicingservice.entities.Merchant;
import com.hps.merchantservicingservice.services.MerchantServicingService;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/merchants/servicing")
public class MerchanServicingController {

    @Autowired
    private MerchantServicingService merchantServicingService;

    @PutMapping("/merchantUpdated/{id}")
    public Merchant updateMerchant(@PathVariable long id, @RequestBody MerchantDTO merchantDTO) {
        return merchantServicingService.updateMerchant(id, merchantDTO);
    }
    @Autowired
    private KafkaAdmin kafkaAdmin;
    @Autowired
    private Map<String, Object> adminConfigs;


    @PostConstruct
    public void createTopic() {
        String topicName = "merchant-servcing-service";
        int numPartitions = 1;
        short replicationFactor = 1;

        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        kafkaAdmin.createOrModifyTopics(newTopic);
    }
}


