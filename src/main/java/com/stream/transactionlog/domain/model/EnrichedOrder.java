package com.stream.transactionlog.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class EnrichedOrder {
    private String orderId;
    private String customerId;
    private long orderDate; // Giữ nguyên kiểu long
    private String totalAmount;
    private String orderStatus;
    private List<OrderDetail> details;

    // Constructor mặc định
    public EnrichedOrder() {
        this.details = new ArrayList<>();
    }

    // Constructor với tham số
    @JsonCreator
    public EnrichedOrder(@JsonProperty("orderId") String orderId,
                         @JsonProperty("customerId") String customerId,
                         @JsonProperty("orderDate") long orderDate,
                         @JsonProperty("totalAmount") String totalAmount,
                         @JsonProperty("orderStatus") String orderStatus,
                         @JsonProperty("details") List<OrderDetail> details) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.details = details != null ? details : new ArrayList<>();
    }

    // Constructor từ Order
    public EnrichedOrder(Order order) {
        this.orderId = order.getOrderId();
        this.customerId = order.getCustomerId();
        this.orderDate = order.getOrderDate();
        this.totalAmount = order.getTotalAmount();
        this.orderStatus = order.getOrderStatus();
        this.details = new ArrayList<>();
    }

    // Getters và Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        if (details != null) {
            this.details = details;
        } else {
            System.out.println("Error: details cannot be null");
            this.details = new ArrayList<>();
        }
    }
}