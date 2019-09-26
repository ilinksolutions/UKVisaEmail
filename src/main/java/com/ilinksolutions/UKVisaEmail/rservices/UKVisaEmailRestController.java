package com.ilinksolutions.UKVisaEmail.rservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ilinksolutions.UKVisaEmail.bservices.UKVisaService;
import com.ilinksolutions.UKVisaEmail.domains.UKVisaMessage;
import com.ilinksolutions.UKVisaEmail.utils.AES256Manager;
import com.ilinksolutions.UKVisaEmail.utils.EmailManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value="UK Visa Email Rest Test API", tags= { "UK Visa Email Endpoints" })
public class UKVisaEmailRestController {
	Logger logger = LoggerFactory.getLogger(UKVisaEmailRestController.class);
	
	/*	
	@RequestMapping(value = "/fileMessage", method = RequestMethod.POST, headers = { "content-type=multipart/mixed",
			"content-type=multipart/form-data" })
	public ResponseEntity<UKVisaMessage> fileMessage(@RequestHeader HttpHeaders headers, @PathVariable String userId,
			@RequestPart(value = "image", required = false) MultipartFile image,
			@RequestPart(value = "", required = true) UKVisaMessage json) {
		logger.info("UKVisaEmailRestController: fileMessage: Begin!");
		logger.info("UKVisaEmailRestController: fileMessage: JSON: " + json.toString());
		logger.info("UKVisaEmailRestController: fileMessage: End!");
		UKVisaService service = new UKVisaService();
		UKVisaMessage returnValue = service.getEntry(new Integer(1).intValue());
		if (returnValue == null) {
			logger.info("UKVisaEmailRestController: fileMessage: returnValue: NULL");
			return ResponseEntity.notFound().build();
		} else {
			logger.info("UKVisaEmailRestController: fileMessage: returnValue: " + returnValue.toString());
			return ResponseEntity.ok(returnValue);
		}
	}

@GetMapping("/getmsg/{id}")
	public ResponseEntity<UKVisaMessage> readEntry(@PathVariable String id) {
		logger.info("UKVisaEmailRestController: readEntry: Begin!");
		logger.info("UKVisaEmailRestController: readEntry: Path Variable: " + id);
		UKVisaService service = new UKVisaService();
		UKVisaMessage returnValue = service.getEntry(new Integer(id).intValue());
		if (returnValue == null) {
			logger.info("UKVisaEmailRestController: readEntry: returnValue: NULL");
			return ResponseEntity.notFound().build();
		} else {
			logger.info("UKVisaEmailRestController: readEntry: returnValue: " + returnValue.toString());
			return ResponseEntity.ok(returnValue);
		}
	}

	@PostMapping("/savemsg")
	public ResponseEntity<UKVisaMessage> registerMessage(@RequestBody UKVisaMessage message) {
		logger.info("UKVisaEmailRestController: registerMessage: Begin.");
		logger.info("UKVisaEmailRestController: registerMessage: Transform: " + message.toString());
		UKVisaService service = new UKVisaService();
		UKVisaMessage returnValue = service.addEntry(message);
		if (returnValue == null) {
			logger.info("UKVisaEmailRestController: registerMessage: id: NULL.");
			return ResponseEntity.notFound().build();
		} else {
			logger.info("UKVisaEmailRestController: registerMessage: id: End.");
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(returnValue.getId())
					.toUri();
			return ResponseEntity.created(uri).body(returnValue);
		}
	}

	@PutMapping("/updatemsg/{id}")
	public ResponseEntity<UKVisaMessage> update(@RequestBody UKVisaMessage message, @PathVariable int id) {
		logger.info("UKVisaEmailRestController: update: Begin.");
		UKVisaService service = new UKVisaService();
		UKVisaMessage returnValue = service.updateEntry(id, message);
		if (returnValue == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(returnValue);
		}
	}
*/
	@ApiOperation(value="Sending Email to the client.")
	@PostMapping("/sendEmail")
	public ResponseEntity<Integer> sendEmail(@RequestBody UKVisaMessage message)
	{
		logger.info("UKVisaEmailRestController: getService: Begin!");
		logger.info("UKVisaEmailRestController: getService: Path Variable: " + message.toString());
		Integer returnValue = new Integer(0);
		String text = "Dear " + message.getFirstName() + " " + message.getLastName()
				+ ", \n\n Your application has been submitted based on your a request filed on your behalf.";
		String subject = "Re: UK VISA Application: Submission Added.";
		try {
			String messageString = "{\"id\": " + message.getId() + "," + "\"firstName\": \"" + message.getFirstName()
					+ "\"," + "\"lastName\": \"" + message.getLastName() + "\"," + "\"contactNo\": \""
					+ message.getContactNo() + "\"," + "\"email\": \"" + message.getEmail() + "\"}";
			String encryptedString = AES256Manager.encryptMessage(messageString);
			EmailManager eMail = new EmailManager(subject, text);
			eMail.send(encryptedString);
			returnValue = new Integer(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(returnValue);
	}
}