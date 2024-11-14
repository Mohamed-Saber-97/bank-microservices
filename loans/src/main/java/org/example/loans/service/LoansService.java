package org.example.loans.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.loans.constants.LoansConstants;
import org.example.loans.dto.LoansDto;
import org.example.loans.entity.Loans;
import org.example.loans.exception.LoanAlreadyExistsException;
import org.example.loans.exception.ResourceNotFoundException;
import org.example.loans.mapper.LoansMapper;
import org.example.loans.repository.LoansRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LoansService {
    private final LoansRepository loansRepository;
    private final LoansMapper loansMapper;

    @Transactional
    public void createLoan(String mobileNumber) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(mobileNumber);
        if (optionalLoans.isPresent()) {
            throw new LoanAlreadyExistsException("Loan already registered with given mobileNumber " + mobileNumber);
        }
        loansRepository.save(createNewLoan(mobileNumber));
    }

    private Loans createNewLoan(String mobileNumber) {
        Loans newLoan = new Loans();
        long randomLoanNumber = 100000000000L + new Random().nextInt(900000000);
        newLoan.setLoanNumber(Long.toString(randomLoanNumber));
        newLoan.setMobileNumber(mobileNumber);
        newLoan.setLoanType(LoansConstants.HOME_LOAN.value());
        newLoan.setTotalLoan(Integer.parseInt(LoansConstants.NEW_LOAN_LIMIT.value()));
        newLoan.setAmountPaid(0);
        newLoan.setOutstandingAmount(Integer.parseInt(LoansConstants.NEW_LOAN_LIMIT.value()));
        return newLoan;
    }

    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber)
                                     .orElseThrow(
                                             () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
                                                 );
        return loansMapper.toDto(loans);
    }

    @Transactional
    public boolean updateLoan(LoansDto loansDto) {
        Loans loans = loansRepository.findByLoanNumber(loansDto.loanNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "LoanNumber", loansDto.loanType()));
        loans.setLoanType(loansDto.loanNumber());
        loans.setLoanType(loansDto.loanType());
        loans.setMobileNumber(loansDto.mobileNumber());
        loans.setTotalLoan(loansDto.totalLoan());
        loans.setAmountPaid(loansDto.amountPaid());
        loans.setOutstandingAmount(loansDto.outstandingAmount());
        loansRepository.save(loans);
        return true;
    }

    @Transactional
    public boolean deleteLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Loan", "mobileNumber", mobileNumber)
                                                                                  );
        loansRepository.deleteById(loans.getLoanId());
        return true;
    }
}
