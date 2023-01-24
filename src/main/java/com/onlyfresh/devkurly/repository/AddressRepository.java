package com.onlyfresh.devkurly.repository;

import com.onlyfresh.devkurly.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
