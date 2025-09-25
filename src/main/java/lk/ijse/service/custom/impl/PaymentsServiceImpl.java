package lk.ijse.service.custom.impl;

import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dto.PaymentsDTO;
import lk.ijse.entity.Payment;
import lk.ijse.enums.DAOTypes;
import lk.ijse.mapper.PaymentMapper;
import lk.ijse.service.custom.PaymentsService;
import lk.ijse.service.exception.DuplicateException;
import lk.ijse.service.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentsServiceImpl implements PaymentsService {

    private final PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOTypes.PAYMENTS);

    @Override
    public List<PaymentsDTO> getAllPayments() throws Exception {
        List<Payment> paymentsList = paymentDAO.getAll();
        List<PaymentsDTO> list = new ArrayList<>();
        for (Payment payments : paymentsList) {
            list.add(PaymentMapper.toDTO(payments));
        }
        return list;
    }

    @Override
    public String getLastPaymentId() throws Exception {
        return paymentDAO.getLastId();
    }

    @Override
    public boolean savePayments(PaymentsDTO t) throws Exception {
        Optional<Payment> payments = paymentDAO.findById(t.getPaymentId());
        if (payments.isPresent()) {
            throw new DuplicateException("Payment already exists");
        }
        if (t.getStudentId() == null) {
            throw new NotFoundException("Student id is null");
        }
        return paymentDAO.save(PaymentMapper.toEntity(t));
    }

    @Override
    public boolean updatePayments(PaymentsDTO t) throws Exception {
        Optional<Payment> payments = paymentDAO.findById(t.getPaymentId());
        if (payments.isEmpty()) {
            throw new DuplicateException("Payment Not Found");
        }
        return paymentDAO.update(PaymentMapper.toEntity(t));
    }

    @Override
    public boolean deletePayments(String id) throws Exception {
        Optional<Payment> payments = paymentDAO.findById(id);
        if (payments.isEmpty()) {
            throw new DuplicateException("Payment Not Found");
        }
        return paymentDAO.delete(id);
    }

    @Override
    public List<String> getAllPaymentIds() throws Exception {
        return paymentDAO.getAllIds();
    }

    @Override
    public Optional<PaymentsDTO> findByPaymentId(String id) throws Exception {
        Optional<Payment> payments = paymentDAO.findById(id);
        return payments.map(PaymentMapper::toDTO);
    }

    @Override
    public String generateNewPaymentId() {
        return paymentDAO.generateNewId();
    }
}
