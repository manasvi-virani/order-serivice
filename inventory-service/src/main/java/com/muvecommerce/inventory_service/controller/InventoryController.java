package com.muvecommerce.inventory_service.controller;

import com.muvecommerce.inventory_service.repository.InventoryRepository;
import com.muvecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku_code}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String sku_code) {
        return inventoryService.isInStock(sku_code);


    }
}
