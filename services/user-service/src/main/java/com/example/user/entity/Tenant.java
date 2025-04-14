package com.example.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("tenants")
public class Tenant {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name; // Unique tenant name (e.g., company or org)

    private String adminEmail; // Email of the tenant's admin

    private String contactNumber;

    private String address;

    private String status; // e.g., ACTIVE, SUSPENDED, PENDING

    private String planType; // e.g., FREE, PRO, ENTERPRISE

    private Instant subscriptionExpiryDate;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
