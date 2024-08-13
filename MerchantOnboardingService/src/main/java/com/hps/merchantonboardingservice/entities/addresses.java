package com.hps.merchantonboardingservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Table(name = "addresses")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class addresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adresseId;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String email;
    private String phoneNumber;
    private String faxNumber;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    @JsonIgnore
    private Merchant merchant;

}
