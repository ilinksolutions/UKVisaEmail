package com.ilinksolutions.UKVisaEmail.bservices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.ilinksolutions.UKVisaEmail.data.UKVisaDAO;
import com.ilinksolutions.UKVisaEmail.data.impl.UKVisaDAOImpl;
import com.ilinksolutions.UKVisaEmail.domains.UKVisaMessage;
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
		String encryptedString = encryptMessage(messageString); //AES256Manager.encryptMessage(messageString);
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
		String encryptedString = encryptMessage(messageString); //AES256Manager.encryptMessage(messageString);
		EmailManager eMail = new EmailManager(subject, text);
		eMail.send(encryptedString);
		return dao.updateEntry(id, message);
	}
	
	
	public String encryptMessage(String message)
    {
        String output = "";
		try
        {
            URL url = new URL("http://ilinkp4-ilinkp4.b9ad.pro-us-east-1.openshiftapps.com/UKVisaED/encryptmsg");
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            OutputStream oStream = httpConnection.getOutputStream();
            oStream.write(message.getBytes());
            
            if (httpConnection.getResponseCode() != 200)
            {
                throw new  RuntimeException("Encryption/Dcryption Services Error: Error Code ");
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((httpConnection.getInputStream())));
    
            while ((output = br.readLine()) != null)
            {
            }
            oStream.flush();
            httpConnection.disconnect();
        } catch (Exception e) {
        	  throw new  RuntimeException("Encryption/Dcryption Services Error: Error Code ", e);
        }
		return output;
    }
}
