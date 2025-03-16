package com.SideQuest_app.domain.model.core;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Place {

    @Id
    private String id;

    @NotNull
    private String name;

    @NotNull
    private Float rating;

    @NotNull
    private Long totVisits;

}
