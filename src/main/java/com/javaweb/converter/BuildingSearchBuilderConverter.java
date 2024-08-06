package com.javaweb.converter;

import java.util.List;

import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.utils.BuildingSearchRequestUtils;
import org.springframework.stereotype.Component;

import com.javaweb.builder.BuildingSearchBuilder;

@Component



public class BuildingSearchBuilderConverter
{
    public BuildingSearchBuilder toBuildingSearchBuilder(BuildingSearchRequest buildingSearchRequest, List<String> typeCode)
    {
        BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
                .setName(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getName(), String.class))
                .setFloorArea(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getFloorArea(), Long.class))
                .setWard(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getWard(), String.class))
                .setStreet(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getStreet(), String.class))
                .setDistrict(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getDistrict(), String.class))
                .setNumberOfBasement(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getNumberOfBasement(), Long.class))
                .setTypeCode(typeCode)
                .setManagerName(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getManagerName(), String.class))
                .setManagerPhone(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getManagerPhone(), String.class))
                .setRentPriceTo(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getRentPriceTo(), Long.class))
                .setRentPriceFrom(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getRentPriceFrom(), Long.class))
                .setAreaFrom(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getAreaFrom(), Long.class))
                .setAreaTo(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getAreaTo(), Long.class))
                .setStaffId(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getStaffId(), Long.class))
                .setLevel(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getLevel(), Long.class))
                .setDirection(BuildingSearchRequestUtils.getObject(buildingSearchRequest.getDirection(), String.class))
                .build();


        return buildingSearchBuilder;
    }
}