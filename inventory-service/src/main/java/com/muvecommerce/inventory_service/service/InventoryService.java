package com.muvecommerce.inventory_service.service;

import com.muvecommerce.inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional()
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku_code){
      return  inventoryRepository.findBySkuCode(sku_code).isPresent();

    }
}
