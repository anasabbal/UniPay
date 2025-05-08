package com.unipay.service.address;

import com.unipay.command.CreateAddressCommand;
import com.unipay.models.Address;

public interface AddressService {
    Address create(final CreateAddressCommand command);
}
