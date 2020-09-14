package com.edson.customer.service;
/**
* @author  Edson Filho
* @version 1.0
* @since   2020-09-14 
*/
import java.util.InputMismatchException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edson.customer.commons.ApplicationMessages;
import com.edson.customer.entity.CustomerEntity;
import com.edson.customer.model.Customer;
import com.edson.customer.model.repository.CustomerRepo;

@Service
public class CustomerValidations {
	
	@Autowired
	CustomerRepo repository;
	/**
     * This function validates if informed customer is valid for insert. Combined with functions: cpfAlreadyExists and  isAValidCustomer()
     * @param Customer -> object
     * @return String invalidateMsg -> null as valid customer or msg as invalid customer
     */
	public String isAvalidInsert(Customer customer) {
		String invalidateMsg=isAValidCustomer(customer);
		if(invalidateMsg==null) {
			if(cpfAlreadyExists(customer.getCpf())) {
				invalidateMsg=ApplicationMessages.CPF_ALREADY_EXISTS.getCode();
			}
		}
		return invalidateMsg;
	}
	/**
     * This function validates if informed customer is valid for update. Combined with functions: cpfAlreadyExists and  isAValidCustomer()
     * @param Customer -> object
     * @return String invalidateMsg -> null as valid customer or msg as invalid customer
     */
	public String isAvalidUpdate(Customer customer) {
		String invalidateMsg=isAValidCustomer(customer);
		if(invalidateMsg==null) {
			if(!cpfAlreadyExists(customer.getCpf())) {
				invalidateMsg=ApplicationMessages.CPF_NOT_FOUND.getCode();
			}
		}
		return invalidateMsg;
	}
	/**
     * This function validates if it is a valid deletion. Combined with functions: isAValidCpf() and  cpfAlreadyExists()
     * @param String -> cpf
     * @return String invalidateMsg -> null as valid deletion or msg as invalid deletion
     */
	public String isAvalidDelete(String cpf) {
		String invalidateMsg=null;
		if(!isAValidCpf(cpf)){
			invalidateMsg=ApplicationMessages.INVALID_CPF.getCode();
		}else if(!cpfAlreadyExists(cpf)) {
				invalidateMsg=ApplicationMessages.CPF_NOT_FOUND.getCode();
		}
		return invalidateMsg;
	}
	/**
     * This function validates if it is a valid search by cpf. Combined with functions: isAValidCpf()
     * @param String -> cpf
     * @return String invalidateMsg -> null as valid search or msg as invalid search
     */
	public String isAValidSearchByCpf(String cpf) {
		String invalidateMsg=null;
		if(!isAValidCpf(cpf)){
			invalidateMsg=ApplicationMessages.INVALID_CPF.getCode();
		}
		return invalidateMsg; 
	}
	
	/**
     * This function validates if informed customer is valid. Combined with functions: isAValidCpf() and  isAValidName()
     * @param Customer -> object
     * @return String invalidateMsg -> null as valid customer or msg as invalid customer
     */
	private String isAValidCustomer(Customer customer) {
		String invalidateMsg=null;
		if(!isAValidCpf(customer.getCpf())){
			invalidateMsg=ApplicationMessages.INVALID_CPF.getCode();
		}else if(!isAValidName(customer.getName())){
			invalidateMsg=ApplicationMessages.INVALID_NAME.getCode();
		}
		return invalidateMsg;
	}
	
	/**
     * This function validates if cpf  already exists on data base.
     * @param String -> cpf  of customer
     * @return true (already exists) or false (not found)
     */
	private boolean cpfAlreadyExists(String cpf) {
		CustomerEntity customerEntity=repository.findByCpf(cpf);
		if(customerEntity==null) {
			return false;
		}else {
			return true;
		}
	}
	
	/**
     * This function validates if informed name is a valid name.
     * @param String -> name  of customer
     * @return true (valid name) or false (invalid name)
     */
	private boolean isAValidName(String name){
		if(name==null) {
			return false;
		}else {
			//^               // start of line
			//[a-zA-Z]{2,}    // will except a name with at least two characters
			//$				  // end of line
			return name.matches("^[a-zA-z ]{2,}$");
		}
		
	}
	
	/**
     * This function validates if informed CPF is valid. Need to be a real cpf.
     * Use "https://www.4devs.com.br/gerador_de_cpf" to test
     * @param String -> cpf number of customer
     * @return true (valid cpf) or false (invalid cpf)
     */
	private  boolean isAValidCpf(String Cpf) {
		if(Cpf==null)
			return false;
		 // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (Cpf.equals("00000000000") ||
        	Cpf.equals("11111111111") ||
        	Cpf.equals("22222222222") || Cpf.equals("33333333333") ||
        	Cpf.equals("44444444444") || Cpf.equals("55555555555") ||
        	Cpf.equals("66666666666") || Cpf.equals("77777777777") ||
        	Cpf.equals("88888888888") || Cpf.equals("99999999999") ||
            (Cpf.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
        // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
        // converte o i-esimo caractere do CPF em um numero:
        // por exemplo, transforma o caractere '0' no inteiro 0
        // (48 eh a posicao de '0' na tabela ASCII)
            num = (int)(Cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

        // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(Cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == Cpf.charAt(9)) && (dig11 == Cpf.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }
     }
}



