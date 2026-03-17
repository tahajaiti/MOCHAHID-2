package com.mochahid.videoservice.controller;

import com.mochahid.common.dto.ApiResponse;
import com.mochahid.videoservice.entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> getAllCategories() {
        List<Map<String, String>> categories = Arrays.stream(Category.values())
                .map(c -> Map.of(
                        "name", c.name(),
                        "label", formatLabel(c.name()),
                        "slug", c.name().toLowerCase().replace("_", "-")
                ))
                .toList();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

    private String formatLabel(String name) {
        String[] parts = name.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!sb.isEmpty()) sb.append(" ");
            sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return sb.toString();
    }
}
