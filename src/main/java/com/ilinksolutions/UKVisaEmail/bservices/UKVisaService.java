package com.ilinksolutions.UKVisaEmail.bservices;

import java.util.List;

import com.ilinksolutions.UKVisaEmail.data.UKVisaDAO;
import com.ilinksolutions.UKVisaEmail.data.impl.UKVisaDAOImpl;
import com.ilinksolutions.UKVisaEmail.domains.UKVisaMessage;
import com.ilinksolutions.UKVisaEmail.utils.AES256Manager;
import com.ilinksolutions.UKVisaEmail.utils.EmailManager;

/**
 *
 */
public class UKVisaService
{
	private UKVisaDAO dao = new UKVisaDAOImpl();

	public UKVisaMessage getEntry(int id)
	{
		return dao.getEntry(id);
	}
	
	public List<UKVisaMessage> getAllEntries()
	{
		return dao.list();
	}
	
	public UKVisaMessage addEntry(UKVisaMessage message)
	{
		String text = "Dear " + message.getFirstName() + " " + message.getLastName() + 
				", \n\n Your application has been submitted based on your a request filed on your behalf.";
		String subject = "Re: UK VISA Application: Submission Added.";

		UKVisaMessage returnValue = dao.save(message);
		String messageString = "{\"id\": " + message.getId() + "," +
								"\"firstName\": \"" + message.getFirstName() + "\"," +
								"\"lastName\": \"" + message.getLastName() + "\"," +
								"\"contactNo\": \"" + message.getContactNo() + "\"," +
								"\"email\": \"" + message.getEmail() + "\"}";
		String encryptedString = AES256Manager.encryptMessage(messageString);
		EmailManager eMail = new EmailManager(subject, text);
		eMail.send(encryptedString);
		return returnValue;
	}

	public UKVisaMessage updateEntry(int id, UKVisaMessage message)
	{
		String text = "Dear " + message.getFirstName() + " " + message.getLastName() + 
				", \n\n Your application has been updated based on your a request filed on your behalf.";
		String subject = "Re: UK VISA Application: Submission Updated.";
		
		String messageString = "{\"id\": " + message.getId() + "," +
				"\"firstName\": \"" + message.getFirstName() + "\"," +
				"\"lastName\": \"" + message.getLastName() + "\"," +
				"\"contactNo\": \"" + message.getContactNo() + "\"," +
				"\"email\": \"" + message.getEmail() + "\"}";
		String encryptedString = AES256Manager.encryptMessage(messageString);
		EmailManager eMail = new EmailManager(subject, text);
		eMail.send(encryptedString);
		return dao.updateEntry(id, message);
	}
}
