package com.javaweb.service;

import com.javaweb.model.dto.AssignmentCustomerDTO;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.dto.TransactionTypeDTO;
import com.javaweb.model.request.CustomerSearchRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    List<CustomerSearchResponse> findAll(CustomerSearchRequest customerSearchRequest, Pageable pageable);
    int countTotalItem( List<CustomerSearchResponse> responses );
    CustomerDTO findById(Long id);
    CustomerDTO addOrUpdateCustomer(CustomerDTO customerDTO);
    void deleteCustomersByIds(Long[] ids);
    AssignmentCustomerDTO addAssignmentCustomerEntity(AssignmentCustomerDTO assignmentCustomerDTO);
    ResponseDTO listStaffs(Long id);

}
