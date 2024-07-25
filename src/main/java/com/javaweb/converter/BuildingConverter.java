package com.javaweb.converter;

import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.enums.District;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.response.BuildingSearchResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BuildingConverter{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RentAreaConverter rentAreaConverter;
    public BuildingSearchResponse toBuildingSearchResponse(BuildingEntity buildingEntity)
    {
        BuildingSearchResponse buildingSearchResponse = modelMapper.map(buildingEntity, BuildingSearchResponse.class);
        List<RentAreaEntity> rentAreaEntities = buildingEntity.getRentAreaEntities();

        String rentArea = rentAreaEntities.stream().map(it -> it.getValue().toString()).collect(Collectors.joining(", "));
        buildingSearchResponse.setRentArea(rentArea);

        Map<String, String> districts = District.type();

        String districtName = "";
        if(buildingEntity.getDistrict() != null && buildingEntity.getDistrict() != "")
        {
            districtName = districts.get(buildingEntity.getDistrict());
        }


        if(districtName != null && districtName != "")
        {
            buildingSearchResponse.setAddress(buildingEntity.getStreet() + ", " + buildingEntity.getWard() + ", " + districtName);
        }
        return buildingSearchResponse;
    }

    public BuildingDTO tobuildingDTO(BuildingEntity buildingEntity)
    {
        return modelMapper.map(buildingEntity, BuildingDTO.class);
    }

    public BuildingEntity toBuildingEntity(BuildingDTO buildingDTO)
    {
        BuildingEntity buildingEntity = modelMapper.map(buildingDTO, BuildingEntity.class);
        buildingEntity.setTypeCode(removeAccent(buildingDTO.getTypeCode()));
        buildingEntity.setRentAreaEntities(rentAreaConverter.rentAreaEntityList(buildingDTO, buildingEntity));
        return buildingEntity;
    }

    public static String removeAccent(List<String> typeCodes)
    {
        return String.join(",", typeCodes);
    }
}