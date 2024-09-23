package com.hps.merchantservicingservice.controllers;



import com.hps.merchantservicingservice.dto.MerchantDTO;
import com.hps.merchantservicingservice.services.MerchantServicingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/merchants/servicing")
public class MerchanServicingController {
    @Autowired
    private MerchantServicingService merchantServicingService;
//    @PutMapping("/merchantUpdatedMMS/{id}")
//    public ResponseEntity<String> updateMerchant(@PathVariable Long id, @RequestBody MerchantDTO updateRequest) {
//        merchantServicingService.updateMerchant(id, updateRequest);
//        return ResponseEntity.ok("Merchant updated successfully");
//    }
        @PutMapping("/merchantUpdatedMMS/{id}")
        public ResponseEntity<String> updateMerchant(@PathVariable Long id, @RequestBody MerchantDTO updateRequest) {
            merchantServicingService.updateMerchant(id, updateRequest);
            return ResponseEntity.ok("Merchant updated successfully");
        }

}
//    @Autowired
//    private KafkaAdmin kafkaAdmin;
//    @Autowired
//    private Map<String, Object> adminConfigs;
//
//
//    @PostConstruct
//    public void createTopic() {
//        String topicName = "merchant-servcing-service";
//        int numPartitions = 1;
//        short replicationFactor = 1;
//
//        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
//        kafkaAdmin.createOrModifyTopics(newTopic);
//    }



