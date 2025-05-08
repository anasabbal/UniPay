package com.unipay.service.business;


import com.unipay.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService{

    private final BusinessRepository businessRepository;
}
