package com.scaler.paymentservice.controllers;

import com.scaler.paymentservice.dtos.CreatePaymentLinkRequestDto;
import com.scaler.paymentservice.dtos.CreatePaymentLinkResponseDto;
import com.scaler.paymentservice.models.PaymentStatus;
import com.scaler.paymentservice.services.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments/")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping()
    public CreatePaymentLinkResponseDto createPaymentLink(@RequestBody CreatePaymentLinkRequestDto request){
        String redirectUrl = this.paymentService.createPaymentLink(request.getOrderId());

        CreatePaymentLinkResponseDto response = new CreatePaymentLinkResponseDto();
        response.setUrl(redirectUrl);

        return response;
    }

    @GetMapping("/{id}")
    public PaymentStatus checkPaymentStatus(@PathVariable("id") String paymentGatewayPaymentId){
        return this.paymentService.getPaymentStatus(paymentGatewayPaymentId);
    }


}

/*
    User - createOrder -> OrderService()
    User - createPaymentLink() -> PaymentService
    User (Order details page) -> checkPaymentStatus() -> PaymentService
    PaymentGateway (Webhook) -> PaymentService
 */
