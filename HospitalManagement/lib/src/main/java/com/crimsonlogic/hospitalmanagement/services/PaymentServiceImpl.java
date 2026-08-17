package com.crimsonlogic.hospitalmanagement.services;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import com.crimsonlogic.hospitalmanagement.enums.PaymentStatus;
import com.crimsonlogic.hospitalmanagement.exceptions.BillNotFoundException;
import com.crimsonlogic.hospitalmanagement.exceptions.ValidationException;
import com.crimsonlogic.hospitalmanagement.mapper.IBillMapper;
import com.crimsonlogic.hospitalmanagement.mapper.IPaymentMapper;
import com.crimsonlogic.hospitalmanagement.model.Bill;
import com.crimsonlogic.hospitalmanagement.model.Payment;
import com.crimsonlogic.hospitalmanagement.util.IdGenerator;
import com.crimsonlogic.hospitalmanagement.util.MyBatisUtil;

@Service
public class PaymentServiceImpl
        implements IPaymentMapper {

    // =========================================================
    // ADD PAYMENT
    // =========================================================

    public void addPayment(
            Payment payment,
            String cardNumber,
            String expiry,
            String cvv,
            String upiId,
            String upiPin)
            throws ValidationException,
            BillNotFoundException {

        validatePayment(payment);

        String paymentType =
                payment.getPaymentType()
                        .trim()
                        .toUpperCase();

        BillServiceImpl billService =
                new BillServiceImpl();

        Bill bill =
                billService.getBillById(
                        payment.getBill()
                                .getBillId());

        if (bill == null) {

            throw new BillNotFoundException(
                    "Bill not found");
        }

        if ("PAID".equalsIgnoreCase(
                bill.getStatus())) {

            throw new ValidationException(
                    "This bill is already paid");
        }

        if ("CANCELLED".equalsIgnoreCase(
                bill.getStatus())) {

            throw new ValidationException(
                    "Cancelled bill cannot be paid");
        }

        switch (paymentType) {

            case "CASH":

                validateCashPayment(
                        payment.getAmount(),
                        bill.getTotalAmount());

                break;

            case "CARD":

                validateCardPayment(
                        cardNumber,
                        expiry,
                        cvv);

                break;

            case "UPI":

                validateUpiPayment(
                        upiId,
                        upiPin);

                break;

            default:

                throw new ValidationException(
                        "Payment type must be "
                                + "Cash, Card or UPI");
        }

        payment.setAmount(
                bill.getTotalAmount());

        payment.setPaymentDate(
                LocalDate.now());

        payment.setPaymentId(
                IdGenerator.generateRandomId(
                        "PAY"));

        payment.setStatus(
                PaymentStatus.SUCCESS.name());

        payment.setActive(true);

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPaymentMapper paymentMapper =
                    session.getMapper(
                            IPaymentMapper.class);

            IBillMapper billMapper =
                    session.getMapper(
                            IBillMapper.class);

            try {

                paymentMapper.addPayment(
                        payment);

                billMapper.updateBillStatus(
                        bill.getBillId(),
                        "PAID");

                session.commit();

            } catch (RuntimeException e) {

                session.rollback();

                throw e;
            }
        }
    }


    // =========================================================
    // ADD PAYMENT - MAPPER METHOD
    // =========================================================

    @Override
    public void addPayment(
            Payment payment) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPaymentMapper mapper =
                    session.getMapper(
                            IPaymentMapper.class);

            mapper.addPayment(payment);

            session.commit();
        }
    }


    // =========================================================
    // GET PAYMENT BY ID
    // =========================================================

    @Override
    public Payment getPaymentById(
            String paymentId) {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPaymentMapper mapper =
                    session.getMapper(
                            IPaymentMapper.class);

            return mapper.getPaymentById(
                    paymentId);
        }
    }


    // =========================================================
    // GET ALL PAYMENTS
    // =========================================================

    @Override
    public List<Payment> getAllPayments() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPaymentMapper mapper =
                    session.getMapper(
                            IPaymentMapper.class);

            return mapper.getAllPayments();
        }
    }


    // =========================================================
    // GET MAX PAYMENT NUMBER
    // =========================================================

    @Override
    public Integer getMaxPaymentNumber() {

        try (SqlSession session =
                     MyBatisUtil.getSqlSession()) {

            IPaymentMapper mapper =
                    session.getMapper(
                            IPaymentMapper.class);

            return mapper.getMaxPaymentNumber();
        }
    }


    // =========================================================
    // CASH VALIDATION
    // =========================================================

    private void validateCashPayment(
            double amount,
            double billAmount)
            throws ValidationException {

        if (amount <= 0) {

            throw new ValidationException(
                    "Cash amount must be greater than zero");
        }

        if (amount < billAmount) {

            throw new ValidationException(
                    "Cash amount is less than bill amount");
        }
    }


    // =========================================================
    // CARD VALIDATION
    // =========================================================

    private void validateCardPayment(
            String cardNumber,
            String expiry,
            String cvv)
            throws ValidationException {

        if (cardNumber == null
                || !cardNumber.matches(
                "\\d{16}")) {

            throw new ValidationException(
                    "Card number must contain "
                            + "exactly 16 digits");
        }

        if (expiry == null
                || !expiry.matches(
                "(0[1-9]|1[0-2])/\\d{2}")) {

            throw new ValidationException(
                    "Expiry must be in MM/YY format");
        }

        if (cvv == null
                || !cvv.matches(
                "\\d{3}")) {

            throw new ValidationException(
                    "CVV must contain exactly 3 digits");
        }
    }


    // =========================================================
    // UPI VALIDATION
    // =========================================================

    private void validateUpiPayment(
            String upiId,
            String upiPin)
            throws ValidationException {

        if (upiId == null
                || !upiId.matches(
                "^[A-Za-z0-9._-]+@[A-Za-z0-9.-]+$")) {

            throw new ValidationException(
                    "Invalid UPI ID");
        }

        if (upiPin == null
                || !upiPin.matches(
                "\\d{4}|\\d{6}")) {

            throw new ValidationException(
                    "UPI PIN must contain "
                            + "4 or 6 digits");
        }
    }


    // =========================================================
    // VALIDATE PAYMENT
    // =========================================================

    private void validatePayment(
            Payment payment)
            throws ValidationException {

        if (payment == null) {

            throw new ValidationException(
                    "Payment cannot be null");
        }

        if (payment.getBill() == null) {

            throw new ValidationException(
                    "Bill cannot be null");
        }

        if (payment.getBill()
                .getBillId() == null
                || payment.getBill()
                .getBillId()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Bill ID cannot be empty");
        }

        if (payment.getPaymentType() == null
                || payment.getPaymentType()
                .trim()
                .isEmpty()) {

            throw new ValidationException(
                    "Payment type cannot be empty");
        }
    }
}