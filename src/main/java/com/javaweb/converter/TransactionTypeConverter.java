package com.javaweb.converter;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.TransactionTypeEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.TransactionTypeDTO;
import com.javaweb.model.request.TransactionCreateRequest;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.TransactionTypeRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.utils.NumberUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TransactionTypeConverter {
    @Autowired
    public ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public TransactionTypeDTO toTransactionDTO(TransactionTypeEntity transactionTypeEntity){
        TransactionTypeDTO transactionDTO = modelMapper.map(transactionTypeEntity, TransactionTypeDTO.class);
        transactionDTO.setCustomerId(transactionTypeEntity.getCustomer().getId());
        if(transactionTypeEntity.getCreatedDate().equals(transactionDTO.getModifiedDate())) {
            transactionDTO.setModifiedDate(null);
            transactionDTO.setModifiedBy(null);
        }
        return transactionDTO;
    }
    public TransactionTypeEntity toTransactionEntity(TransactionCreateRequest transactionCreateRequest, Long staffId){
        TransactionTypeEntity transactionEntity = modelMapper.map(transactionCreateRequest, TransactionTypeEntity.class);
        UserEntity userEntity = userRepository.findById(staffId).get();
        CustomerEntity customerEntity = customerRepository.findById(transactionCreateRequest.getCustomerId()).get();
        if(transactionCreateRequest.getId() != null){
            TransactionTypeEntity tmp = transactionTypeRepository.findById(transactionCreateRequest.getId()).get();
            transactionEntity.setCreatedDate(tmp.getCreatedDate());
            transactionEntity.setCreatedBy(tmp.getCreatedBy());
            transactionEntity.setModifiedDate(new Date());
            transactionEntity.setModifiedBy(userEntity.getUserName());
        }
        else{
            transactionEntity.setCreatedBy(userEntity.getUserName());
        }
        transactionEntity.setCustomer(customerEntity);
        return transactionEntity;
    }
    public TransactionTypeEntity toTransactionTypeEntity(TransactionTypeDTO transactionTypeDTO)
    {
        TransactionTypeEntity transactionTypeEntity = modelMapper.map(transactionTypeDTO, TransactionTypeEntity.class);
        CustomerEntity customerEntity = customerRepository.findById(transactionTypeDTO.getCustomerId()).get();
        transactionTypeEntity.setCustomer(customerEntity);

        if(NumberUtils.checkNumber(transactionTypeDTO.getId())) //update
        {
            TransactionTypeEntity foundTransactionTypeEntity = transactionTypeRepository.findById(transactionTypeDTO.getId()).get();
            transactionTypeEntity.setCreatedBy(foundTransactionTypeEntity.getCreatedBy());
            transactionTypeEntity.setCreatedDate(foundTransactionTypeEntity.getCreatedDate());
        }
        return transactionTypeEntity;
    }
}
