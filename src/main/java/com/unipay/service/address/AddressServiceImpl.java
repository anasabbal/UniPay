package com.unipay.service.address;


import com.unipay.command.CreateAddressCommand;
import com.unipay.models.Address;
import com.unipay.repository.AddressRepository;
import com.unipay.utils.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{

    private final AddressRepository addressRepository;


    @Override
    public Address create(CreateAddressCommand command) {
        command.validate();
        log.info("Begin creating address with payload {}", JSONUtil.toJSON(command));
        final Address address = Address.create(command);
        log.info("Address with id {} created successfully !", address.getId());
        return addressRepository.save(address);
    }
}
