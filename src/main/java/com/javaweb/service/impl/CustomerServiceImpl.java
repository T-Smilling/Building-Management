package com.javaweb.service.impl;

import com.javaweb.converter.CustomerConverter;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentCustomerDTO;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.CustomerCreateRequest;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.ICustomerService;
import com.javaweb.utils.CustomerUtils;
import com.javaweb.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerConverter customerConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerUtils customerUtils;

    @Override
    public List<CustomerSearchResponse> findAll(CustomerSearchRequest customerSearchRequest, Pageable pageable) {
        List<CustomerEntity> customerEntities = customerRepository.findAll(customerSearchRequest, pageable);

        List<CustomerSearchResponse> responses = new ArrayList<>();
        for(CustomerEntity customer : customerEntities) responses.add(customerConverter.toCustomerSearchResponse(customer));
        return responses;
    }

    @Override
    public int countTotalItem(List<CustomerSearchResponse> responses) {
        int total = 0;
        for(CustomerSearchResponse customerSearchResponse : responses) total += customerRepository.countTotalItem(customerSearchResponse);
        return total;
    }

    @Override
    public CustomerDTO findById(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).get();
        return customerConverter.convertToDTO(customerEntity);
    }

    public boolean validateCreateOrUpdateCustomer(CustomerDTO customerDTO) {
        if(!StringUtils.check(customerDTO.getCustomerPhone())) return false;
        if(!StringUtils.check(customerDTO.getFullName())) return false;
        return true;
    }

    @Override
    public CustomerDTO addOrUpdateCustomer(CustomerDTO customerDTO) {
        if(!validateCreateOrUpdateCustomer(customerDTO)) return null;
        CustomerEntity customerEntity = customerConverter.convertToEntity(customerDTO);
        customerRepository.save(customerEntity);
        return customerDTO;
    }

    @Override
    public ResponseDTO addCustomer(CustomerCreateRequest customerCreateRequest) {
        ResponseDTO responseDTO= new ResponseDTO();
        if (customerCreateRequest.getPhone() == null || customerCreateRequest.getFullName() == null) {
            responseDTO.setMessage("Không để tên hoặc số điện thoại trống!");
            return responseDTO;
        }
        CustomerEntity customerEntity= customerConverter.EntitytoCustomerCreateRequest(customerCreateRequest);
        if(customerCreateRequest.getId() != null){
            CustomerEntity customerTmp= customerRepository.findById(customerCreateRequest.getId()).get();
            customerUtils.setCustomer(customerEntity,customerTmp);
        }
        customerEntity.setIsActive("1");
        try{
            customerRepository.save(customerEntity);
            if(customerCreateRequest.getId() != null){
                responseDTO.setMessage("Cập nhật khách hàng thành công!");
            }
            else responseDTO.setMessage("Thêm khách hàng thành công!");
        }
        catch (Exception e){
            System.out.println(e);
            responseDTO.setMessage("Thêm khách hàng thất bại!");
        }
        return responseDTO;
    }

    @Override
    public void deleteCustomersByIds(Long[] ids) {
        for(Long id : ids) {
            CustomerEntity customerEntity = customerRepository.findById(id).get();
            customerEntity.setIsActive("0");
            customerRepository.save(customerEntity);
        }
    }

    @Override
    public AssignmentCustomerDTO addAssignmentCustomerEntity(AssignmentCustomerDTO assignmentCustomerDTO) {
        CustomerEntity customerEntity = customerRepository.findById(assignmentCustomerDTO.getCustomerId()).get();
        List<UserEntity> userEntities = userRepository.findByIdIn(assignmentCustomerDTO.getStaffs());
        customerEntity.setUserEntities(userEntities);
        customerRepository.save(customerEntity);
        return assignmentCustomerDTO;
    }

    @Override
    public ResponseDTO listStaffs(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).get();
        List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1, "STAFF");
        List<UserEntity> staffAssignment = customerEntity.getUserEntities();
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
        responseDTO.setMessage("success");
        return responseDTO;
    }
}
