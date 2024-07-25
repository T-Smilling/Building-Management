package com.javaweb.service;

import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IBuildingService{
    List<BuildingSearchResponse> findAll(BuildingSearchRequest buildingSearchRequest, Pageable pageable);
    BuildingDTO findById(Long id);
    BuildingDTO addOrUpdateBuilding(BuildingDTO buildingDTO);
    BuildingDTO deleteBuildings(Long[] ids);
    ResponseDTO listStaffs(Long id);
    AssignmentBuildingDTO addAssignmentBuilding(AssignmentBuildingDTO assignmentBuildingDTO);
    int countTotalItem(List<BuildingSearchResponse> list);
}