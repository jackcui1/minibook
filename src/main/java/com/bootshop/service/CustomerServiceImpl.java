package com.bootshop.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootshop.model.Cart;
import com.bootshop.model.Customer;
import com.bootshop.model.Role;
import com.bootshop.repository.CustomerRepository;
import com.bootshop.service.CartService;
import com.bootshop.service.CustomerService;
import com.bootshop.service.ShippingAddressService;
import com.bootshop.service.UserService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShippingAddressService shippingAddressService;
	
	@Autowired
	private CartService cartService;
	
	@Override
	public Customer addCustomer(Customer customer ,Cart cart) {
		Role role=new Role();
		role.setRole("ROLE_USER");
		customer.getUser().setRole(role);
		userService.save(customer.getUser());
		shippingAddressService.addAddress(customer.getShippingAddress());
		customer.setCart(cart);
		cartService.addCart(customer.getCart());
		return customerRepository.save(customer);
	}
	
	@Override
	public void addCustomer(Customer customer) {
		
		customer.getUser().getRole().setRole("ROLE_USER");
		userService.save(customer.getUser());
		shippingAddressService.addAddress(customer.getShippingAddress());
		cartService.addCart(customer.getCart());
		customerRepository.save(customer);
		
	}
	
	@Override
	public Customer getCustomerById(int id) {
		return  customerRepository.getOne(id);
	}

	@Override
	public List<Customer> getCustomerAll() {
		
		return customerRepository.findAll();
	}

	@Override
	public void deleteCustomer(Customer customer) {
		customerRepository.delete(customer);
		
	}

	@Override
	public void editCustomer(Customer customer) {
		customerRepository.save(customer);
		
	}

	@Override
	public Customer getCustomerBycustomername(String customername) {
		return customerRepository.getCustomerBycustomername(customername);
	}


	@Override
	public Customer findCustomerByCartid(String cartid) {
		return customerRepository.findOneByCartid(cartid);
	}

	@Override
	public boolean isCustomerExist(int id) {

		return customerRepository.exists(id);
	}


}
