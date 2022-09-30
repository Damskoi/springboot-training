package com.example.training.mapper;

import com.example.training.dto.Reservation;

public class ReservationMapper {

    public static Reservation mapToDto(com.example.training.entity.Reservation reservation){

        Reservation dto = new Reservation();
        dto.setId(reservation.getId());
        dto.setDatetime(reservation.getDatetime());
        dto.setEmploye(EmployeMapper.mapToDto(reservation.getEmploye()));
        dto.setSalle(SalleMapper.mapToDto(reservation.getSalle()));

        return dto;
    }

    public static com.example.training.entity.Reservation mapToBo(Reservation reservation){
        com.example.training.entity.Reservation bo = new com.example.training.entity.Reservation();
        bo.setId(reservation.getId());
        bo.setDatetime(reservation.getDatetime());
        bo.setEmploye(EmployeMapper.mapToBo(reservation.getEmploye()));
        bo.setSalle(SalleMapper.mapToBo(reservation.getSalle()));

        return bo;
    }
}
