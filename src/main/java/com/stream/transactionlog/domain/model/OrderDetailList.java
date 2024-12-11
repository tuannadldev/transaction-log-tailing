package com.stream.transactionlog.domain.model;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailList {
    private List<OrderDetail> details;

    public OrderDetailList() {
        this.details = new ArrayList<>();
    }

    // Getter và Setter
    public List<OrderDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
    }

    public void addOrderDetail(OrderDetail detail) {
        this.details.add(detail);
    }

    public List<OrderDetail> ToList() {
        return details;
    }

    @Override
    public String toString() {
        return "OrderDetailList{" + details + '}';
    }
}
