package com.SideQuest_app.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public interface UserModel {

    @Data
    @Builder
    class UserView {
        public String firstName;
        public String lastName;
        public String email;
        private List<TripView> trips;
        private List<InterestView> interests;
    }

    @Data
    @Builder
    class InterestView {
        private String name;
    }

    @Data
    @Builder
    class TripView {
        public String id;
        public String startLocationName;
        private String endLocationName;
        private boolean isCompleted;
        List<StopView> stops;
    }

    @Data
    @Builder
    class StopView {
        private String placeId;
    }

}
