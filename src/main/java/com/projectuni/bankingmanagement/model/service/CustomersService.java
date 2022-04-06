package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.config.SpringConfig;
import com.projectuni.bankingmanagement.exception.CannotCreateCustomerException;
import com.projectuni.bankingmanagement.exception.FoundDepositCustomer;
import com.projectuni.bankingmanagement.exception.InvalidCustomerNameException;
import com.projectuni.bankingmanagement.exception.InvalidCustomerTypeException;
import com.projectuni.bankingmanagement.exception.InvalidDateOfBirthException;
import com.projectuni.bankingmanagement.exception.InvalidNationalCodeException;
import com.projectuni.bankingmanagement.exception.NotFoundCustomerException;
import com.projectuni.bankingmanagement.model.dto.DTOCreateCustomer;
import com.projectuni.bankingmanagement.model.dto.DTOSearchCustomer;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToCustomer;
import com.projectuni.bankingmanagement.model.entity.Customers;
import com.projectuni.bankingmanagement.model.entity.Deposit;
import com.projectuni.bankingmanagement.model.enums.DepositStatus;
import com.projectuni.bankingmanagement.model.repository.CustomersRepository;
import com.projectuni.bankingmanagement.model.repository.DepositRepository;
import com.projectuni.bankingmanagement.other.DateParser;
import com.projectuni.bankingmanagement.other.Str;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.InternalServerErrorException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public record CustomersService(CustomersRepository customersRepository , DepositRepository depositRepository)
{

    /**
     * This method is for adding a new customer to the database, in each step if the information isnot incomplete or valid,
     * an error is thrown and if no error is received from where it was used, it is intended to be added.
     *
     * @param dtoCreateCustomer
     * @throws NullPointerException
     * @throws InvalidCustomerNameException
     * @throws InvalidNationalCodeException
     * @throws InvalidDateOfBirthException
     * @throws CannotCreateCustomerException
     * @throws InvalidCustomerTypeException
     * @throws InternalServerErrorException
     */
    public void createCustomer(final DTOCreateCustomer dtoCreateCustomer) throws NullPointerException, InvalidCustomerNameException, InvalidNationalCodeException, InvalidDateOfBirthException, CannotCreateCustomerException, InvalidCustomerTypeException, InternalServerErrorException
    {
        if (dtoCreateCustomer != null)
        {
            if (Str.notEmpty(dtoCreateCustomer.getName()))
            {
                if (dtoCreateCustomer.getNationalCode() > 0)
                {
                    String dateOfBirthStr = dtoCreateCustomer.getDateOfBirthStr();
                    if (Str.notEmpty(dateOfBirthStr))
                    {
                        try
                        {
                            final Date dateOfBirth = DateParser.pars(dateOfBirthStr , "yyyy-MM-dd");
                            dtoCreateCustomer.setDateOfBirth(dateOfBirth);
                        }
                        catch (Exception e)
                        {
                            throw new InvalidDateOfBirthException("yyyy-MM-dd");
                        }
                    }

                    /**
                     * Searches the national code in the database
                     *
                     * @see CustomersRepository#findByNationalCode(int)
                     */
                    final Customers customersByNationalCode = customersRepository.findByNationalCode(dtoCreateCustomer.getNationalCode());

                    if (customersByNationalCode == null)
                    {
                        if (dtoCreateCustomer.getType() != null)
                        {
                            try
                            {
                                Customers customers = ToCustomer.to(dtoCreateCustomer);
                                customers = customersRepository.save(customers);

                                if (customers.getId() <= 0) throw new CannotCreateCustomerException();
                            }
                            catch (Exception e)
                            {
                                throw new InternalServerErrorException("Server error");
                            }
                        }
                        else throw new InvalidCustomerTypeException();
                    }
                    else throw new InvalidNationalCodeException("The national code is duplicate");
                }
                else throw new InvalidNationalCodeException();
            }
            else throw new InvalidCustomerNameException("Name is empty!" , false);
        }
        else throw new NullPointerException("Request is null");
    }

    public List<Customers> getCustomers()
    {
        return customersRepository.findAll();
    }

    public List<Customers> getCustomers(final DTOSearchCustomer dtoSearchCustomer)
    {
        if (dtoSearchCustomer != null)
        {
            if (Str.notEmpty(dtoSearchCustomer.getName()) || dtoSearchCustomer.getNationalCode() > 0 || dtoSearchCustomer.getType() != null)
            {
                final StringBuilder strQuery = new StringBuilder("select customer from Customers customer where ");

                boolean before = false;

                boolean hasName = false, hasType = false, hasNationalCode = false;

                if (Str.notEmpty(dtoSearchCustomer.getName()))
                {
                    hasName = true;

                    strQuery.append("customer.name = :NAME");
                    before = true;
                }
                if (dtoSearchCustomer.getType() != null)
                {
                    hasType = true;

                    if (before) strQuery.append(" or ");

                    strQuery.append("customer.type = :TYPE");
                    before = true;
                }
                if (dtoSearchCustomer.getNationalCode() > 0)
                {
                    hasNationalCode = true;

                    if (before) strQuery.append(" or ");

                    strQuery.append("customer.nationalCode = :NATIONAL_CODE");
                }

                final EntityManager entityManager = SpringConfig.getEntityManager();
                final Query query = entityManager.createQuery(strQuery.toString());

                if (hasName) query.setParameter("NAME" , dtoSearchCustomer.getName());
                if (hasType) query.setParameter("TYPE" , dtoSearchCustomer.getType());
                if (hasNationalCode) query.setParameter("NATIONAL_CODE" , dtoSearchCustomer.getNationalCode());

                return (List<Customers>) query.getResultList();
            }
        }
        throw new NullPointerException("Request is null");
    }

    public void changeCustomerStatus(final long customerId , final boolean newStatus) throws NotFoundCustomerException
    {
        final Optional<Customers> customerFindById = customersRepository.findById(customerId);

        if (customerFindById.isPresent())
        {
            final Customers customer = customerFindById.get();
            customer.setStatus(newStatus);
            customersRepository.save(customer);
        }
        else throw new NotFoundCustomerException();
    }

    public void deleteCustomer(final long customerId) throws NotFoundCustomerException, FoundDepositCustomer
    {
        final Optional<Customers> customerById = customersRepository.findById(customerId);
        if (customerById.isPresent())
        {
            final List<Deposit> depositByCustomerId = depositRepository.findByCustomers_IdAndDepositStatus(customerId , DepositStatus.OPEN);
            if (depositByCustomerId.size() == 0) customersRepository.delete(customerById.get());
            else throw new FoundDepositCustomer();
        }
        else throw new NotFoundCustomerException();
    }
}
