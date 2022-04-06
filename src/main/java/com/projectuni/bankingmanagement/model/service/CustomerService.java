package com.projectuni.bankingmanagement.model.service;

import com.projectuni.bankingmanagement.exception.CannotCreateCustomerException;
import com.projectuni.bankingmanagement.exception.InvalidCustomerNameException;
import com.projectuni.bankingmanagement.exception.InvalidCustomerTypeException;
import com.projectuni.bankingmanagement.exception.InvalidDateOfBirthException;
import com.projectuni.bankingmanagement.exception.InvalidNationalCodeException;
import com.projectuni.bankingmanagement.model.dto.DTOCreateCustomer;
import com.projectuni.bankingmanagement.model.dto.Mapper.ToCustomer;
import com.projectuni.bankingmanagement.model.entity.Customer;
import com.projectuni.bankingmanagement.model.repository.CustomerRepository;
import com.projectuni.bankingmanagement.other.DateParser;
import com.projectuni.bankingmanagement.other.Str;
import org.springframework.stereotype.Service;

import javax.ws.rs.InternalServerErrorException;
import java.util.Date;
import java.util.List;

@Service
public record CustomerService(CustomerRepository customerRepository)
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
                     * @see CustomerRepository#findByNationalCode(int)
                     */
                    final Customer customerByNationalCode = customerRepository.findByNationalCode(dtoCreateCustomer.getNationalCode());

                    if (customerByNationalCode == null)
                    {
                        if (dtoCreateCustomer.getType() != null)
                        {
                            try
                            {
                                Customer customer = ToCustomer.to(dtoCreateCustomer);
                                customer = customerRepository.save(customer);

                                if (customer.getId() <= 0) throw new CannotCreateCustomerException();
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


    public List<Customer> getCustomers()
    {
        return customerRepository.findAll();
    }
}
