package com.javaweb.api.admin;


import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "buildingAPIOfAdmin")
@RequestMapping("/api/building")
@Transactional

public class BuildingAPI{
    @Autowired
    private IBuildingService buildingService;

    @GetMapping
    public List<BuildingSearchResponse> getBuilding (@ModelAttribute BuildingSearchRequest buildingSearchRequest, Pageable pageable) {
        List<BuildingSearchResponse> responses = buildingService.findAll(buildingSearchRequest, pageable);
        return responses;
    }

    @PostMapping
    public ResponseEntity<BuildingDTO> addOrUpdateBuilding(@RequestBody BuildingDTO buildingDTO) {
        return ResponseEntity.ok(buildingService.addOrUpdateBuilding(buildingDTO));
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<BuildingDTO> deleteBuilding(@PathVariable Long[] ids) {
        return ResponseEntity.ok(buildingService.deleteBuildings(ids));
    }

    @GetMapping("/{id}/staffs")
    public ResponseDTO loadStaffs(@PathVariable Long id) {
        ResponseDTO responseDTO = buildingService.listStaffs(id);
        return responseDTO;
    }

    @PostMapping("/assigment")
    public ResponseEntity<AssignmentBuildingDTO> updateAssigmentBuilding(@RequestBody AssignmentBuildingDTO assignmentBuildingDTO) {
        return ResponseEntity.ok(buildingService.addAssignmentBuilding(assignmentBuildingDTO));
    }
}
