package com.br.delogic.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "preference_sports")
    private String preferenceSports;

    @Column(name = "preference_theatre")
    private String preferenceTheatre;

    @Column(name = "preference_concerts")
    private String preferenceConcerts;

    @Column(name = "preference_jazz")
    private String preferenceJazz;

    @Column(name = "preference_classical")
    private String preferenceClassical;

    @Column(name = "preference_opera")
    private String preferenceOpera;

    @Column(name = "preference_rock")
    private String preferenceRock;

    @Column(name = "preference_vegas")
    private String preferenceVegas;

    @Column(name = "preference_broadway")
    private String preferenceBroadway;

    @Column(name = "preference_musicals")
    private String preferenceMusicals;

}
