package com.scaler.paymentservice.services;

import com.scaler.paymentservice.models.Payment;
import com.scaler.paymentservice.models.PaymentGateway;
import com.scaler.paymentservice.models.PaymentStatus;
import com.scaler.paymentservice.paymentgateways.PaymentGatewayFactory;
import com.scaler.paymentservice.paymentgateways.PaymentGatewayInterface;
import com.scaler.paymentservice.paymentgateways.RazorpayPaymentGateway;
import com.scaler.paymentservice.repositories.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private RazorpayPaymentGateway razorpayPaymentGateway;
    private PaymentGatewayFactory paymentGatewayFactory;

    private PaymentRepository paymentRepository;

    public PaymentService(PaymentGatewayFactory paymentGatewayFactory, PaymentRepository paymentRepository, RazorpayPaymentGateway razorpayPaymentGateway) {
        this.paymentGatewayFactory = paymentGatewayFactory;
        this.paymentRepository = paymentRepository;
        this.razorpayPaymentGateway = razorpayPaymentGateway;
    }

    public String createPaymentLink(long orderId) {

        /*
            I need to get the details of the order
                -amount

            Actual implementation -> get order details from OderService

            Order order = restTemplate.getForObject(url:"",Order.class);
            Long amount = order.getAmount();
            String userName = order.getUser().getName();
            String userMobile = order.getUser().getPhoneNumber();
            String userEmail = order.getUser().getEmail();
         */

        Long amount = 1000L;
        String userName = "Akhila Kamadi";
        String userMobile = "+919856237410";
        String userEmail = "abc@example.com";

        PaymentGatewayInterface paymentGateway = paymentGatewayFactory.getBestPaymentGateway();

        String paymentLink = paymentGateway.createPaymentLink(amount, userName, userEmail, userMobile, orderId);

        Payment payment = new Payment();
        payment.setPaymentLink(paymentLink);
        payment.setOrderId(orderId);
//        payment.setPaymentGateway(PaymentGateway.RAZORPAY);
        payment.setPaymentGateway(PaymentGateway.STRIPE);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setAmount(amount);

        paymentRepository.save(payment);

        return paymentLink;
    }

    public PaymentStatus getPaymentStatus(String paymentGatewayPaymentId) {
        Payment payment = paymentRepository.findByPaymentGatewayReferenceId(paymentGatewayPaymentId);
        PaymentGatewayInterface paymentGateway = null;
        if (PaymentGateway.RAZORPAY.equals(payment.getPaymentGateway())){
            paymentGateway = razorpayPaymentGateway;
        } else if (PaymentGateway.STRIPE.equals(payment.getPaymentGateway())) {
            
        }

        PaymentStatus paymentStatus = paymentGateway.getPaymentStatus(paymentGatewayPaymentId);
        payment.setPaymentStatus(paymentStatus);

        paymentRepository.save(payment);

        return paymentStatus;
    }
}
