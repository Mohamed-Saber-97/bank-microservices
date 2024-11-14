package org.example.accounts.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.accounts.constants.AccountConstants;
import org.example.accounts.dto.CustomerAccountDto;
import org.example.accounts.dto.CustomerDto;
import org.example.accounts.entity.Accounts;
import org.example.accounts.entity.Customer;
import org.example.accounts.exception.CustomerAlreadyExistsException;
import org.example.accounts.exception.ResourceNotFoundException;
import org.example.accounts.mapper.AccountsMapper;
import org.example.accounts.mapper.CustomerMapper;
import org.example.accounts.repository.AccountsRepository;
import org.example.accounts.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AccountsMapper accountsMapper;

    @Transactional
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.mobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException(
                    "Customer already registered with given mobileNumber " + customerDto.mobileNumber());
        }
        Customer customer = customerMapper.toEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerID(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS.value());
        newAccount.setBranchAddress(AccountConstants.ADDRESS.value());
        return newAccount;
    }

    public CustomerAccountDto fetchAccountByMobileNumber(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                                              .orElseThrow(() -> new ResourceNotFoundException("Customer",
                                                                                               "mobileNumber",
                                                                                               mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerID(customer.getCustomerId())
                                              .orElseThrow(() -> new ResourceNotFoundException("Account",
                                                                                               "customerId",
                                                                                               customer.getCustomerId()
                                                                                                       .toString()));


        return CustomerAccountDto.fromCustomerAndAccount(customerMapper.toDto(customer),
                                                         accountsMapper.toDto(accounts));
    }

    @Transactional
    public CustomerAccountDto updateAccount(CustomerAccountDto customerAccountDto) {
        Accounts updatedAccount = updateAccountInfo(customerAccountDto);
        Customer updatedCustomer = updateCustomerInfo(updatedAccount.getCustomerID(), customerAccountDto);

        return CustomerAccountDto.fromCustomerAndAccount(customerMapper.toDto(updatedCustomer),
                                                         accountsMapper.toDto(updatedAccount));
    }

    private Customer updateCustomerInfo(Long customerId, CustomerAccountDto customerAccountDto) {
        return customerRepository.findById(customerId)
                                 .map(customer -> {
                                     customer.setName(customerAccountDto.name());
                                     customer.setMobileNumber(customerAccountDto.mobileNumber());
                                     customer.setEmail(customerAccountDto.email());
                                     return customerRepository.save(customer);
                                 })
                                 .orElseThrow(() -> new ResourceNotFoundException("Customer",
                                                                                  "customerId",
                                                                                  customerId.toString()));
    }

    private Accounts updateAccountInfo(CustomerAccountDto customerAccountDto) {
        return accountsRepository.findByAccountNumber(customerAccountDto.accountNumber())
                                 .map(account -> {
                                     account.setAccountType(customerAccountDto.accountType());
                                     account.setBranchAddress(customerAccountDto.branchAddress());
                                     return accountsRepository.save(account);
                                 })
                                 .orElseThrow(() -> new ResourceNotFoundException("Account",
                                                                                  "accountNumber",
                                                                                  customerAccountDto.accountNumber()
                                                                                                    .toString()));
    }

    @Transactional
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                                              .orElseThrow(() -> new ResourceNotFoundException("Customer",
                                                                                               "mobileNumber",
                                                                                               mobileNumber));
        accountsRepository.deleteByCustomerID(customer.getCustomerId());
        customerRepository.delete(customer);
        return true;
    }
}
