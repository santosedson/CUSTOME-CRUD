package com.edson.customer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.edson.customer.commons.ApplicationMessages;
import com.edson.customer.model.Customer;
//import com.edson.customer.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerApplicationTests {

	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

   /* @Autowired
    private CustomerService service;*/
    
    private static final String POST_ENDPOINT= "/api/v1/customer/register";
    private static final String PUT_ENDPOINT= "/api/v1/customer/update";
    private static final String DELETE_ENDPOINT= "/api/v1/customer/delete";
    private static final String FIND_BY_ENDPOINT= "/api/v1/customer/findBy";
    
    private static final String NEW_CPF_TO_REGISTER="17021298021";
    private static final String CPF_ALREADY_REGISTERED_1="60165062061";
    private static final String CPF_ALREADY_REGISTERED_2="11386682063";//to be used in deletion test
    private static final String CPF_NOT_REGISTERED="07622917028";
    private static final String INVALID_CPF_FORMATTED="356.424.260-01";
    private static final String INVALID_CPF_UNREAL="11111111111";
    private static final String INVALID_CPF_ALPHABET="ABCDEFGHIJKLMN";
    private static final String INVALID_CPF_NULL=null;
    
    private static final String INVALID_NAME_SIZE_1="e";
    private static final String INVALID_NAME_NULL=null;
    private static final String INVALID_NAME_IVALID_CHARACTER="edson f.";
    private static final String INVALID_NAME_WITH_NUMBER="edson f1l80";
    private static final String VALID_NAME_1="EDSON FILHO";
    private static final String VALID_NAME_2="EDSON B S FILHO";
    private static final String VALID_NAME_3="michel n";
    
    /*============================REGISTER TEST [BEGIN] ===========================================================*/
    
	@Test //Customer Válido (esse teste só vai dar sucesso para primeiro insert desse cpf)
	void case01RegisterCustomer1() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase1( new Customer(VALID_NAME_3,NEW_CPF_TO_REGISTER,null));
	    Assertions.assertEquals(ApplicationMessages.INSERT_SUCCESS.getCode(),returnMsg );// JUnit5
	}
	
	@Test //CPF já cadastrado (esse teste só vai dar sucesso para tentativa de segundo insert do cpf  já cadastado antes)
	void case02RegisterCustomer() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase1(new Customer(VALID_NAME_1,CPF_ALREADY_REGISTERED_1,null));
	    Assertions.assertEquals(ApplicationMessages.CPF_ALREADY_EXISTS.getCode(),returnMsg);// JUnit5
	}
	
	@Test //CPF inválido - com pontuação
	void case03RegisterCustomer() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase1(  new Customer(VALID_NAME_1,INVALID_CPF_FORMATTED,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
	}
	
	@Test //CPF inválido - não é um cpf real
	void case04RegisterCustomer() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase1( new Customer(VALID_NAME_1,INVALID_CPF_UNREAL,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
	}
	
	@Test //CPF inválido - não é um cpf 
	void case05RegisterCustomer() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase1( new Customer(VALID_NAME_1, INVALID_CPF_ALPHABET,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
	}
	
	@Test //CPF inválido - cpf nulo
	void case06RegisterCustomer() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase1( new Customer(VALID_NAME_1,INVALID_CPF_NULL,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
	}
	
	@Test //Nome inválido - nome nulo
	void case07RegisterCustomer() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase1( new Customer(INVALID_NAME_NULL,CPF_NOT_REGISTERED,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_NAME.getCode(),returnMsg);// JUnit5
	}
	
	@Test //Nome inválido - nome contém números
	void case08RegisterCustomer() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase1( new Customer(INVALID_NAME_WITH_NUMBER,CPF_NOT_REGISTERED,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_NAME.getCode(),returnMsg);// JUnit5
	}
	
	@Test //Nome inválido - nome contém caractere especial '.'
	void case09RegisterCustomer() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase1( new Customer(INVALID_NAME_IVALID_CHARACTER,CPF_NOT_REGISTERED,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_NAME.getCode(),returnMsg);// JUnit5
	}
	
	@Test //Nome inválido - contém somente uma letra
	void case10RegisterCustomer() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase1( new Customer(INVALID_NAME_SIZE_1,CPF_NOT_REGISTERED,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_NAME.getCode(),returnMsg);// JUnit5
	}

	@Test //Customer inválido - objeto nullo
	void case11RegisterCustomer() throws JsonProcessingException, Exception {
		String returnMsg = registerApiCase2(null);
	    Assertions.assertEquals(ApplicationMessages.BAD_REQUEST.getCode(),returnMsg);// JUnit5
	}
	
	//accepted
	public String registerApiCase1(Customer customer) throws JsonProcessingException, Exception {
		ResultActions result=mockMvc.perform(post(POST_ENDPOINT)
							        .contentType("application/json")
							        .content(objectMapper.writeValueAsString(customer)))
							        .andExpect(status().isAccepted());
		//service.checkAndInsertCustomer(customer);
		return result.andReturn().getResponse().getContentAsString();
	}
	//bad request
	public String registerApiCase2(Customer customer) throws JsonProcessingException, Exception {
		ResultActions result=	mockMvc.perform(post(POST_ENDPOINT)
								        .contentType("application/json")
								        .content(objectMapper.writeValueAsString(customer)))
								        .andExpect(status().isBadRequest());

		return result.andReturn().getResponse().getContentAsString();
	}

	/*============================REGISTER TEST [END] ===========================================================*/
	
	
    /*============================UPDATE TEST [BEGIN] ===========================================================*/
    
	
	@Test //Atualizado com sucesso (esse teste só vai dar sucesso se o cpf informado está no banco)
	void case12UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase1(new Customer(VALID_NAME_2,CPF_ALREADY_REGISTERED_1,null));
	    Assertions.assertEquals(ApplicationMessages.UPDATE_SUCCESS.getCode(),returnMsg);// JUnit5
	}
	
	@Test //Falha ao atulizar - CPF não encontrado 
	void case13UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase1(new Customer(VALID_NAME_2,CPF_NOT_REGISTERED,null));
	    Assertions.assertEquals(ApplicationMessages.CPF_NOT_FOUND.getCode(),returnMsg);// JUnit5
	}
	
	@Test //CPF inválido - com pontuação
	void case14UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase1(  new Customer(VALID_NAME_2,INVALID_CPF_FORMATTED,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
	}
	
	@Test //CPF inválido - não é um cpf real
	void case15UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase1( new Customer(VALID_NAME_2,INVALID_CPF_UNREAL,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
	}
	
	@Test //CPF inválido - não é um cpf 
	void case16UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase1( new Customer(VALID_NAME_2, INVALID_CPF_UNREAL,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
	}
	
	@Test //CPF inválido - cpf nulo
	void case17UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase1( new Customer(VALID_NAME_2,INVALID_CPF_NULL,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
	}
	
	@Test //Nome inválido - nome nulo
	void case18UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase1( new Customer(INVALID_NAME_NULL,CPF_ALREADY_REGISTERED_1,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_NAME.getCode(),returnMsg);// JUnit5
	}
	
	@Test //Nome inválido - nome contém números
	void case19UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase1( new Customer(INVALID_NAME_WITH_NUMBER,CPF_ALREADY_REGISTERED_1,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_NAME.getCode(),returnMsg);// JUnit5
	}
	
	@Test //Nome inválido - nome contém caractere especial '.'
	void case20UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase1( new Customer(INVALID_NAME_IVALID_CHARACTER,CPF_ALREADY_REGISTERED_1,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_NAME.getCode(),returnMsg);// JUnit5
	}
	
	@Test //Nome inválido - contém somente uma letra
	void case21UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase1( new Customer(INVALID_NAME_SIZE_1,CPF_ALREADY_REGISTERED_1,null));
	    Assertions.assertEquals(ApplicationMessages.INVALID_NAME.getCode(),returnMsg);// JUnit5
	}
	
	@Test //Customer inválido - objeto nullo
	void case22UpdateCustomer() throws JsonProcessingException, Exception {
		String returnMsg = updateApiCase2(null);
	    Assertions.assertEquals(ApplicationMessages.BAD_REQUEST.getCode(),returnMsg);// JUnit5
	}
	
	//accepted
	public String updateApiCase1(Customer customer) throws JsonProcessingException, Exception {
		ResultActions result=mockMvc.perform(put(PUT_ENDPOINT)
							        .contentType("application/json")
							        .content(objectMapper.writeValueAsString(customer)))
							        .andExpect(status().isAccepted());
		
		return result.andReturn().getResponse().getContentAsString();
	}
	//bad request
	public String updateApiCase2(Customer customer) throws JsonProcessingException, Exception {
		ResultActions result=	mockMvc.perform(put(PUT_ENDPOINT)
								        .contentType("application/json")
								        .content(objectMapper.writeValueAsString(customer)))
								        .andExpect(status().isBadRequest());

		return result.andReturn().getResponse().getContentAsString();
	}

	/*============================UPDATE TEST [END] ===========================================================*/
	
	
	/*============================FIND BY TEST [BEGIN] ===========================================================*/
    
		@Test //consulta realizada com sucesso (esse teste só vai dar sucesso se o cpf informado está no banco)
		void case23FindCustomerByCpf() throws JsonProcessingException, Exception {
			Object returnObject = findByApiCase2(CPF_ALREADY_REGISTERED_1);
		    Assertions.assertEquals(Customer.class,returnObject);// JUnit5
		}
		
		@Test //CPF inválido - com pontuação
		void case24FindCustomerByCpf() throws JsonProcessingException, Exception {
			String returnMsg = findByApiCase1(INVALID_CPF_FORMATTED);
		    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
		}
		
		@Test //CPF inválido - não é um cpf real
		void case25FindCustomerByCpf() throws JsonProcessingException, Exception {
			String returnMsg = findByApiCase1(INVALID_CPF_UNREAL);
		    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
		}
		
		@Test //CPF inválido - não é um cpf 
		void case26FindCustomerByCpf() throws JsonProcessingException, Exception {
			String returnMsg = findByApiCase1(INVALID_CPF_ALPHABET);
		    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
		}
		
		@Test //CPF inválido - cpf nulo
		void case27FindCustomerByCpf() throws JsonProcessingException, Exception {
			String returnMsg = findByApiCase1(INVALID_CPF_NULL);
		    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
		}
		
		@Test //CPF não encontrado -
		void case28FindCustomerByCpf() throws JsonProcessingException, Exception {
			String returnMsg = findByApiCase1(CPF_NOT_REGISTERED);
		    Assertions.assertEquals(ApplicationMessages.CPF_NOT_FOUND.getCode(),returnMsg);// JUnit5
		}

		
		public String findByApiCase1(String cpf) throws JsonProcessingException, Exception {
			ResultActions result=mockMvc.perform(get(FIND_BY_ENDPOINT)
								        .contentType("application/json")
								        .param("cpf", cpf))
								        .andExpect(status().isAccepted());
			
			return result.andReturn().getResponse().getContentAsString();
		}
		
		public Class<?> findByApiCase2(String cpf) throws JsonProcessingException, Exception {
			ResultActions result=	mockMvc.perform(get(FIND_BY_ENDPOINT)
									        .contentType("application/json")
									        .param("cpf", cpf))
									        .andExpect(status().isAccepted());

			String json = result.andReturn().getResponse().getContentAsString();
			try {
				Customer customer = new ObjectMapper().readValue(json, Customer.class);
				return customer.getClass();
			}catch (Exception e) {
				return e.getClass();
			}
			
			
		}

		/*============================FIND BY TEST [END] ===========================================================*/
		
	    /*============================DELETE TEST [BEGIN] ===========================================================*/
	    
		
		@Test //Deletado com sucesso (esse teste só vai dar sucesso se o cpf informado está no banco/ainda não foi deletado do banco)
		void case29DeleteCustomer() throws JsonProcessingException, Exception {
			String returnMsg = deleteApiCase1(CPF_ALREADY_REGISTERED_2);
		    Assertions.assertEquals(ApplicationMessages.DELETE_SUCCESS.getCode(),returnMsg);// JUnit5
		}
		
		@Test //Falha ao deletar - CPF não encontrado 
		void case30DeleteCustomer() throws JsonProcessingException, Exception {
			String returnMsg = deleteApiCase1(CPF_NOT_REGISTERED);
		    Assertions.assertEquals(ApplicationMessages.CPF_NOT_FOUND.getCode(),returnMsg);// JUnit5
		}
		
		@Test //CPF inválido - com pontuação
		void case31DeleteCustomer() throws JsonProcessingException, Exception {
			String returnMsg = deleteApiCase1(INVALID_CPF_FORMATTED);
		    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
		}
		
		@Test //CPF inválido - não é um cpf real
		void case32DeleteCustomer() throws JsonProcessingException, Exception {
			String returnMsg = deleteApiCase1(INVALID_CPF_UNREAL);
		    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
		}
		
		@Test //CPF inválido - não é um cpf 
		void case33DeleteCustomer() throws JsonProcessingException, Exception {
			String returnMsg = deleteApiCase1(INVALID_CPF_ALPHABET);
		    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
		}
		
		@Test //CPF inválido - cpf nulo
		void case34DeleteCustomer() throws JsonProcessingException, Exception {
			String returnMsg = deleteApiCase1(INVALID_CPF_NULL);
		    Assertions.assertEquals(ApplicationMessages.INVALID_CPF.getCode(),returnMsg);// JUnit5
		}
		

		
		//accepted
		public String deleteApiCase1(String cpf) throws JsonProcessingException, Exception {
			ResultActions result=mockMvc.perform(delete(DELETE_ENDPOINT)
								        .contentType("application/json")
								        .param("cpf", cpf))
								        .andExpect(status().isAccepted());
			
			return result.andReturn().getResponse().getContentAsString();
		}

		/*============================DELETE TEST [END] ===========================================================*/
}
