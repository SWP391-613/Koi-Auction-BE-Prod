package com.swp391.koibe.responses.pagination;

import com.swp391.koibe.models.Payment;
import com.swp391.koibe.responses.PaymentResponse;
import com.swp391.koibe.responses.base.BasePaginationResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PaymentPaginationResponse extends BasePaginationResponse {
    private List<PaymentResponse> item;
}
