package com.vnet.lab.entity;

import com.vnet.lab.utils.enums.CartStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "carts")
public class Cart extends AbstractEntity{

    @ManyToOne
    private final Customer customer;


    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private final CartStatus cartStatus;

    public Cart(Customer customer, @NonNull CartStatus cartStatus) {
        this.customer = customer;
        this.cartStatus = cartStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return Objects.equals(customer, cart.customer) && cartStatus == cart.cartStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, cartStatus);
    }
}
