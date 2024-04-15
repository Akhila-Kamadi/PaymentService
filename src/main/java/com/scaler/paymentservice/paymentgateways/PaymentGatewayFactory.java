package com.scaler.paymentservice.paymentgateways;

import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayFactory {

    private RazorpayPaymentGateway razorpayPaymentGateway;

    private StripePaymentGateway stripePaymentGateway;

    public PaymentGatewayFactory(RazorpayPaymentGateway razorpayPaymentGateway,
                                 StripePaymentGateway stripePaymentGateway) {
        this.razorpayPaymentGateway = razorpayPaymentGateway;
        this.stripePaymentGateway = stripePaymentGateway;
    }

    public PaymentGatewayInterface getBestPaymentGateway() {

//        int random = new Random().nextInt();
//
//        if (random%2 == 0){
//            return new RazorpayPaymentGateway();
//        }
//        else{
//        return new StripePaymentGateway();
//        }

        //For now let's assume the best payment gateway to be Razorpay
//        return razorpayPaymentGateway;
        return stripePaymentGateway;
    }

}

/*
Which Payment Gateway to choose that is decided by server at runtime -> so factory is needed.
Without you changing any code, if once any payment gateway goes down or has some failures,
automatically within sometime server will start getting the link of next payment gateway
and there would be no changes to be made at front-end


In reality this factory, might be doing even more work.
For example,
    1. This might go to the database and check, what is the success rate of each of the payment gateways.
    2. It might be sorting those payment gateway by time.
    3. It might be finding the best payment gateway and return it.

 */
