package com.scaler.paymentservice.configs;

import com.stripe.StripeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Value("${stripe.secret_key}")
    private String stripeSecretKey;

    @Bean
    public StripeClient setStripeSecretKey() {
//        Stripe.apiKey = stripeSecretKey;
        return new StripeClient(stripeSecretKey);
    }
}
