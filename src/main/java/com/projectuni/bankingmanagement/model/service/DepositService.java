package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.exception.DepositIsClosedException;
import com.projectuni.bankingmanagement.exception.InvalidAccountInventory;
import com.projectuni.bankingmanagement.exception.InvalidCreditExpirationDate;
import com.projectuni.bankingmanagement.exception.InvalidIncreaseDepositException;
import com.projectuni.bankingmanagement.exception.InvalidWithdrawalDepositException;
import com.projectuni.bankingmanagement.exception.InventoryIsNotEnoughException;
import com.projectuni.bankingmanagement.exception.NotFoundCustomerException;
import com.projectuni.bankingmanagement.exception.NotFoundDepositException;
import com.projectuni.bankingmanagement.model.dto.DTOOpeningDeposit;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToDeposit;
import com.projectuni.bankingmanagement.model.entity.Customers;
import com.projectuni.bankingmanagement.model.entity.Deposit;
import com.projectuni.bankingmanagement.model.enums.DepositStatus;
import com.projectuni.bankingmanagement.model.repository.CustomersRepository;
import com.projectuni.bankingmanagement.model.repository.DepositRepository;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public record DepositService(DepositRepository depositRepository , CustomersRepository customersRepository)
{

    public List<Deposit> getDeposits(final long customerId) throws NotFoundCustomerException, NotFoundDepositException
    {
        final Optional<Customers> customerById = customersRepository.findById(customerId);
        if (customerById.isPresent())
        {
            final List<Deposit> depositByCustomerId = depositRepository.findByCustomers_Id(customerId);
            if (depositByCustomerId.size() > 0) return depositByCustomerId;
            else throw new NotFoundDepositException();
        }
        else throw new NotFoundCustomerException();
    }

    public List<Customers> getCustomerDeposits(final long depositId) throws NotFoundDepositException
    {
        return getDepositById(depositId).getCustomers();
    }

    public List<Deposit> getDeposits() throws NotFoundDepositException
    {
        final List<Deposit> depositByCustomerId = depositRepository.findAll();
        if (depositByCustomerId.size() > 0) return depositByCustomerId;
        else throw new NotFoundDepositException();
    }

    /**
     * @param dtoOpeningDeposit
     * @throws NullPointerException
     * @throws NotFoundCustomerException
     * @throws InvalidAccountInventory
     * @throws InvalidCreditExpirationDate
     */
    public void openingDeposit(final DTOOpeningDeposit dtoOpeningDeposit) throws NullPointerException, NotFoundCustomerException, InvalidAccountInventory, InvalidCreditExpirationDate
    {
        if (dtoOpeningDeposit != null)
        {
            final List<Integer> customerIds = dtoOpeningDeposit.getCustomerIds();
            if (customerIds != null && customerIds.size() > 0)
            {
                final List<Customers> customers = new ArrayList<>();
                for (final Integer customerId : customerIds)
                {
                    final Optional<Customers> customerById = customersRepository.findById(Long.valueOf(customerId));
                    if (customerById.isPresent()) customers.add(customerById.get());
                    else throw new NotFoundCustomerException(customerId);
                }
                if (dtoOpeningDeposit.getDepositType() != null)
                {
                    if (dtoOpeningDeposit.getDepositCurrency() != null)
                    {
                        if (dtoOpeningDeposit.getDepositStatus() == null)
                            dtoOpeningDeposit.setDepositStatus(DepositStatus.OPEN);

                        if (dtoOpeningDeposit.getAccountInventory() > 0)
                        {
                            if (dtoOpeningDeposit.getCreditExpirationDate() > 0)
                            {
                                Deposit deposit = ToDeposit.to(dtoOpeningDeposit);
                                deposit.setCustomers(customers);

                                deposit = depositRepository.save(deposit);

                                if (deposit.getId() <= 0)
                                    throw new InternalServerErrorException("Cannot opening deposit");
                            }
                            else throw new InvalidCreditExpirationDate();
                        }
                        else throw new InvalidAccountInventory();
                    }
                    else throw new NullPointerException("deposit_currency is null");
                }
                else throw new NullPointerException("deposit_type is null");
            }
            else throw new NotFoundCustomerException();
        }
        else throw new NullPointerException("Request is null");
    }

    public void changeStatus(final long depositId , final DepositStatus status) throws NotFoundDepositException, DepositIsClosedException
    {
        final Deposit deposit = getDepositById(depositId);
        if (!deposit.getDepositStatus().equals(DepositStatus.CLOSED))
        {
            deposit.setDepositStatus(status);
            depositRepository.save(deposit);
        }
        else throw new DepositIsClosedException();
    }

    /**
     * Increase deposit account
     *
     * @param depositId
     * @param amount
     * @throws NotFoundDepositException
     * @throws InvalidAccountInventory
     * @throws InvalidIncreaseDepositException
     */
    public void increase(final long depositId , final long amount) throws NotFoundDepositException, InvalidAccountInventory, InvalidIncreaseDepositException
    {
        final Deposit depositById = getDepositById(depositId);
        if (amount > 0)
        {
            final DepositStatus depositStatus = depositById.getDepositStatus();
            if (depositStatus.equals(DepositStatus.OPEN) || depositStatus.equals(DepositStatus.BLOCKED_WITHDRAWAL))
            {
                depositById.setAccountInventory(Math.abs(depositById.getAccountInventory() + amount));
                depositRepository.save(depositById);
            }
            else throw new InvalidIncreaseDepositException();
        }
        else throw new InvalidAccountInventory();
    }

    /**
     * Increase deposit account
     *
     * @param depositId
     * @param amount
     * @throws NotFoundDepositException
     * @throws InvalidAccountInventory
     * @throws InvalidWithdrawalDepositException
     */
    public void withdrawal(final long depositId , final long amount) throws NotFoundDepositException, InvalidAccountInventory, InvalidWithdrawalDepositException, InventoryIsNotEnoughException
    {
        final Deposit depositById = getDepositById(depositId);
        if (amount > 0)
        {
            final DepositStatus depositStatus = depositById.getDepositStatus();
            if (depositStatus.equals(DepositStatus.OPEN) || depositStatus.equals(DepositStatus.BLOCKED_DEPOSIT))
            {
                final long accountInventory = depositById.getAccountInventory();
                if (accountInventory >= amount)
                {
                    depositById.setAccountInventory(Math.abs(accountInventory - amount));
                    depositRepository.save(depositById);
                }
                else throw new InventoryIsNotEnoughException();
            }
            else throw new InvalidWithdrawalDepositException();
        }
        else throw new InvalidAccountInventory();
    }

    private Deposit getDepositById(final long id) throws NotFoundDepositException
    {
        final Optional<Deposit> depositById = depositRepository.findById(id);
        if (depositById.isPresent()) return depositById.get();
        else throw new NotFoundDepositException();
    }
}
