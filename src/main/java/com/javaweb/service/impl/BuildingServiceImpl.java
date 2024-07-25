package com.javaweb.service.impl;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.converter.BuildingConverter;
import com.javaweb.converter.BuildingSearchBuilderConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.exception.NotFoundException;
import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.IBuildingService;
import com.javaweb.utils.NumberUtils;
import com.javaweb.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BuildingServiceImpl implements IBuildingService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private BuildingConverter buildingConverter;
    @Autowired
    private BuildingSearchBuilderConverter buildingSearchBuilderConverter;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;



    @Override
    public List<BuildingSearchResponse> findAll(BuildingSearchRequest buildingSearchRequest, Pageable pageable) {
        List<String> typeCode = buildingSearchRequest.getTypeCode();
        BuildingSearchBuilder buildingSearchBuilder = buildingSearchBuilderConverter.toBuildingSearchBuilder(buildingSearchRequest, typeCode);
        List<BuildingEntity> buildingEntities = buildingRepository.findAll(buildingSearchBuilder, pageable);
        List<BuildingSearchResponse> responses = new ArrayList<>();
        for(BuildingEntity item : buildingEntities) {
            BuildingSearchResponse building = buildingConverter.toBuildingSearchResponse(item);
            responses.add(building);
        }

        return responses;
    }

    public List<String> toTypeCodeList(String typeCodes) {
        String[] arr = typeCodes.split(",");
        return Arrays.asList(arr);
    }

    @Override
    public BuildingDTO findById(Long id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        BuildingDTO responses = modelMapper.map(buildingEntity, BuildingDTO.class);

        List<RentAreaEntity> rentAreaEntities = buildingEntity.getRentAreaEntities();
        String rentArea = rentAreaEntities.stream().map(it->it.getValue().toString()).collect(Collectors.joining(","));

        responses.setRentArea(rentArea);
        responses.setTypeCode(toTypeCodeList(buildingEntity.getTypeCode()));

        return responses;
    }

    public static boolean checkAddBuilding(BuildingDTO buildingDTO)
    {
        if(!StringUtils.check(buildingDTO.getName())) return false;
        if(!StringUtils.check(buildingDTO.getDistrict())) return false;
        if(!StringUtils.check(buildingDTO.getWard())) return false;
        if(!StringUtils.check(buildingDTO.getStreet())) return false;
        if(!StringUtils.check(buildingDTO.getRentArea())) return false;
        if(!StringUtils.check(buildingDTO.getRentPriceDescription())) return false;

        if(!NumberUtils.checkNumber(buildingDTO.getNumberOfBasement())) return false;
        if(!NumberUtils.checkNumber(buildingDTO.getFloorArea())) return false;
        if(!NumberUtils.checkNumber(buildingDTO.getRentPrice())) return false;
        return true;
    }

    @Override
    public BuildingDTO addOrUpdateBuilding(BuildingDTO buildingDTO) {
        if(!checkAddBuilding(buildingDTO)) return null;

        BuildingEntity buildingEntity = buildingConverter.toBuildingEntity(buildingDTO);
        Long buildingId = buildingDTO.getId();
        if (buildingId != null) {
            BuildingEntity foundBuilding = buildingRepository.findById(buildingId).orElseThrow(() -> new NotFoundException("Building not found!"));
            buildingEntity.setUserEntities(foundBuilding.getUserEntities());
            buildingEntity.setImage(foundBuilding.getImage());
        }
        buildingRepository.save(buildingEntity);
        return buildingDTO;
    }

    @Override
    public BuildingDTO deleteBuildings(Long[] ids) {
        BuildingEntity buildingEntity = buildingRepository.findById(ids[0]).get();
        buildingRepository.deleteByIdIn(ids);
        return buildingConverter.tobuildingDTO(buildingEntity);
    }

    @Override
    public ResponseDTO listStaffs(Long id) {
        BuildingEntity building = buildingRepository.findById(id).get();
        List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1, "STAFF");
        List<UserEntity> staffAssignment = building.getUserEntities();

        List<StaffResponseDTO> staffResponseDTOS = new ArrayList<>();
        ResponseDTO responseDTO = new ResponseDTO();

        for(UserEntity userEntity : staffs) {
            StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
            staffResponseDTO.setFullName(userEntity.getFullName());
            staffResponseDTO.setStaffId(userEntity.getId());
            if(staffAssignment.contains(userEntity)) staffResponseDTO.setChecked("checked");
            else staffResponseDTO.setChecked("");

            staffResponseDTOS.add(staffResponseDTO);
        }
        responseDTO.setData(staffResponseDTOS);
        responseDTO.setMessage("Success!");
        return responseDTO;
    }

    @Override
    public AssignmentBuildingDTO addAssignmentBuilding(AssignmentBuildingDTO assignmentBuildingDTO) {
        BuildingEntity buildingEntity = buildingRepository.findById(assignmentBuildingDTO.getBuildingId()).get();
        List<UserEntity> userEntities = userRepository.findByIdIn(assignmentBuildingDTO.getStaffs());
        buildingEntity.setUserEntities(userEntities);
        buildingRepository.save(buildingEntity);
        return assignmentBuildingDTO;
    }

    @Override
    public int countTotalItem(List<BuildingSearchResponse> list) {
        int count = 0;
        for(BuildingSearchResponse buildingSearchResponse : list) count += buildingRepository.countTotalItem(buildingSearchResponse);
        return count;
    }
}