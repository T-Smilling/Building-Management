package com.javaweb.converter;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.model.dto.BuildingDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentAreaConverter{
    public static List<RentAreaEntity> rentAreaEntityList(BuildingDTO buildingDTO, BuildingEntity buildingEntity){
        List<RentAreaEntity> rentAreaEntities = new ArrayList<>();
        if(buildingDTO.getRentArea() != null && !buildingDTO.getRentArea().equals("")){
            String rentArea[] = buildingDTO.getRentArea().split(",");
            for(String rent :rentArea){
                RentAreaEntity rentAreaEntity = new RentAreaEntity();
                try {
                    rentAreaEntity.setValue(Long.parseLong(rent.trim()));
                    rentAreaEntity.setBuildingId(buildingEntity);
                    rentAreaEntities.add(rentAreaEntity);
                } catch (Exception e){
                    System.out.println("You haven't entered the number!");
                }
            }
        }
        return rentAreaEntities;
    }
}