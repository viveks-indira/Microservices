package com.User.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symbol;
    private String name;
    private Double price;
    private Integer quantity;
    private String exchange;

    @ManyToOne
    @JoinColumn(name = "user_client_id", referencedColumnName = "clientId", nullable = false)
    @JsonIgnore
    private User user;
}
