package com.SideQuest_app.controller.config;

import com.SideQuest_app.controller.dto.UserModel;
import com.SideQuest_app.domain.model.core.AppUser;
import com.SideQuest_app.domain.model.core.Stop;
import com.SideQuest_app.domain.model.core.Trip;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        TypeMap<AppUser, UserModel.UserView> userToUserViewMap = modelMapper.createTypeMap(AppUser.class, UserModel.UserView.class);
        userToUserViewMap.addMappings(mapper -> {
            mapper.map(AppUser::getFirstName, UserModel.UserView::setFirstName);
            mapper.map(AppUser::getLastName, UserModel.UserView::setLastName);
            mapper.map(AppUser::getEmail, UserModel.UserView::setEmail);
            mapper.map(AppUser::getTrips, UserModel.UserView::setTrips);
            mapper.map(AppUser::getInterests, UserModel.UserView::setInterests);
        });

        TypeMap<Trip, UserModel.TripView> tripToTripViewMap = modelMapper.createTypeMap(Trip.class, UserModel.TripView.class);
        tripToTripViewMap.addMappings(mapper -> {
            mapper.map(Trip::getId, UserModel.TripView::setId);
            mapper.map(Trip::getStartLocationName, UserModel.TripView::setStartLocationName);
            mapper.map(Trip::getEndLocationName, UserModel.TripView::setEndLocationName);
            mapper.map(Trip::isCompleted, UserModel.TripView::setCompleted);
            mapper.map(Trip::getStops, UserModel.TripView::setStops);
        });

        TypeMap<Stop, UserModel.StopView> stopToStopViewMap = modelMapper.createTypeMap(Stop.class, UserModel.StopView.class);
        stopToStopViewMap.addMappings(mapper -> {
            mapper.map(stop -> stop.getPlace().getId(), UserModel.StopView::setPlaceId);
        });

        return modelMapper;


    }
}
