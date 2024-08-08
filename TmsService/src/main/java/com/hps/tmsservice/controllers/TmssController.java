package com.hps.tmsservice.controllers;


import com.hps.tmsservice.dto.DeviceDTO;
import com.hps.tmsservice.dto.MerchantDTO;
import com.hps.tmsservice.entities.Device;
import com.hps.tmsservice.entities.Merchant;
import com.hps.tmsservice.services.TmsService;
import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/tms")
public class TmssController {
    @Autowired
    private TmsService tmss;
    @Autowired
    private KafkaAdmin kafkaAdmin;
    @Autowired
    private Map<String, Object> adminConfigs;


    @PostConstruct
    public void createTopic() {
        String topicName = "tmsservice";
        int numPartitions = 1;
        short replicationFactor = 1;

        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);
        kafkaAdmin.createOrModifyTopics(newTopic);
    }

    @PutMapping("/deviceUpdated/{id}")
    public Device updateContract(@PathVariable long id, @RequestBody DeviceDTO deviceDTO) {
        return tmss.updateContract(id, deviceDTO);
    }





    @DeleteMapping("/devices/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        tmss.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }


}
