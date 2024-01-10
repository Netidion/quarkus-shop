package com.vnet.lab.entity;

import com.vnet.lab.utils.enums.PaymentStatus;
import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "payments")
public class Payment extends AbstractEntity{

    @Column(name = "e_payment_id")
    private String ePaymentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @NotNull
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    public Payment(String ePaymentId, @NotNull PaymentStatus paymentStatus, @NotNull BigDecimal amount) {
        this.ePaymentId = ePaymentId;
        this.paymentStatus = paymentStatus;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(ePaymentId, payment.ePaymentId) && paymentStatus == payment.paymentStatus
                && Objects.equals(amount, payment.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ePaymentId, paymentStatus, amount);
    }
}
