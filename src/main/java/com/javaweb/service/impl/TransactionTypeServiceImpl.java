package com.javaweb.service.impl;

import com.javaweb.converter.TransactionTypeConverter;
import com.javaweb.entity.TransactionTypeEntity;
import com.javaweb.model.dto.TransactionTypeDTO;
import com.javaweb.repository.TransactionTypeRepository;
import com.javaweb.service.ITransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionTypeServiceImpl implements ITransactionTypeService {
    @Autowired
    private TransactionTypeRepository transactionTypeRepository;
    @Autowired
    private TransactionTypeConverter transactionTypeConverter;
    @Override
    public List<TransactionTypeDTO> findByCodeAndCustomerId(String code, Long customerId) {
        List<TransactionTypeDTO> response = new ArrayList<>();
        List<TransactionTypeEntity> listTransactionType = transactionTypeRepository.findByCodeAndCustomerId(code, customerId);
        for(TransactionTypeEntity transactionTypeEntity : listTransactionType) response.add(transactionTypeConverter.toTransactionDTO(transactionTypeEntity));
        return response;
    }

    @Override
    public TransactionTypeDTO addOrUpdateTransactionType(TransactionTypeDTO transactionTypeDTO) {
        TransactionTypeEntity transactionTypeEntity = transactionTypeConverter.toTransactionTypeEntity(transactionTypeDTO);
        transactionTypeRepository.save(transactionTypeEntity);
        return transactionTypeDTO;
    }

    @Override
    public TransactionTypeDTO findById(Long id) {
        return transactionTypeConverter.toTransactionDTO(transactionTypeRepository.findById(id).get());
    }
}