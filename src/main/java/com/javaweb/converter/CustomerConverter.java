package com.javaweb.converter;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.enums.Status;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.request.CustomerCreateRequest;
import com.javaweb.model.response.CustomerSearchResponse;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.utils.NumberUtils;
import com.javaweb.utils.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomerConverter{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDTO convertToDTO(CustomerEntity customerEntity){
        CustomerDTO customerDTO = modelMapper.map(customerEntity, CustomerDTO.class);
        return customerDTO;
    }
    public CustomerEntity convertToEntity(CustomerDTO customerDTO){
        CustomerEntity customerEntity = modelMapper.map(customerDTO, CustomerEntity.class);
        customerEntity.setIsActive("1");
        Long customerId = customerDTO.getId();
        if(NumberUtils.checkNumber(customerId)) {
            CustomerEntity foundCustomer = customerRepository.findById(customerId).get();
            customerEntity.setTransactionTypes(foundCustomer.getTransactionTypes());
            customerEntity.setUserEntities(foundCustomer.getUserEntities());
            customerEntity.setCreatedDate(foundCustomer.getCreatedDate());
            customerEntity.setCreatedBy(foundCustomer.getCreatedBy());
        }
        else customerEntity.setStatus("CHUA_XU_LY");
        return customerEntity;
    }
    public CustomerEntity EntitytoCustomerCreateRequest(CustomerCreateRequest customerCreateRequest){
        CustomerEntity customerEntity = modelMapper.map(customerCreateRequest, CustomerEntity.class);
        return customerEntity;
    }
    public CustomerSearchResponse toCustomerSearchResponse(CustomerEntity customerEntity) {
        Map<String, String> status = Status.type();
        CustomerSearchResponse response = modelMapper.map(customerEntity, CustomerSearchResponse.class);
        if(StringUtils.check(customerEntity.getStatus())) response.setStatus(status.get(customerEntity.getStatus()));
        return response;
    }
}