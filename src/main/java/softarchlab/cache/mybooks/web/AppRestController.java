package softarchlab.cache.mybooks.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import softarchlab.cache.mybooks.domain.Reader;
import softarchlab.cache.mybooks.enums.Genre;
import softarchlab.cache.mybooks.enums.SystemRole;
import softarchlab.cache.mybooks.service.ReaderService;

@RestController
public class AppRestController {

    private static final Logger logger = LoggerFactory.getLogger(AppRestController.class);

    @Autowired
    private MailSender mailTemplate;

    @Autowired
    private ReaderService readerService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(value = "/register_reader", method = RequestMethod.POST)
    public ResponseEntity<String> registerReader(@RequestBody @Valid Reader reader) {
        HttpStatus httpStatus = null;
        String message = null;
        try {
            readerService.registerReader(reader);
            httpStatus = HttpStatus.OK;
            message = "You have successfully registered.";
        } catch (Exception e) {
            httpStatus = HttpStatus.SERVICE_UNAVAILABLE;
            message = e.getMessage();
            logger.error("", e);
        }

        return getMessageResponse(httpStatus, message);

    }

    @RequestMapping(value = "/forgotten_password_send", method = RequestMethod.GET)
    public ResponseEntity<String> forgottenPassword(@RequestParam(value = "email", required = true) String email) {
        HttpStatus httpStatus;
        String message;

        Optional<Reader> readerOpt = readerService.findByEmail(email);
        if (!readerOpt.isPresent()) {
            httpStatus = HttpStatus.NOT_FOUND;
            message = String.format("User with email=%s was not found", email);
        } else {
            try {
                Reader reader = readerOpt.get();
                String generatedPassword = generatePassword();
                readerService.changePassword(generatedPassword, reader);
                sendMessage(reader.getEmail(), "Forgotten password", "Your password is: " + generatedPassword);
                httpStatus = HttpStatus.OK;
                message = "Your password has been successfully sent to your mail.";
            } catch (MailException e) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                message = e.getMessage();
                logger.error("", e);
            }
        }

        return getMessageResponse(httpStatus, message);

    }

    @RequestMapping(value = "/app_data", method = RequestMethod.GET)
    public ResponseEntity<Map<String, List<String>>> appData() {

        Map<String, List<String>> appData = new HashMap<>();
        appData.put("genres", Genre.names());
        appData.put("systemRoles", SystemRole.names());

        return new ResponseEntity<>(appData, HttpStatus.OK);
    }

    private ResponseEntity<String> getMessageResponse(HttpStatus httpStatus, String message) {
        Map<String, Object> messageJson = new HashMap<>();
        messageJson.put("message", message);

        String outputMessage;
        HttpStatus outputHttpStatus = httpStatus;
        ObjectMapper mapper = new ObjectMapper();
        try {
            outputMessage = mapper.writeValueAsString(messageJson);
        } catch (JsonProcessingException e) {
            outputHttpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            outputMessage = "{ \"message\" : \"Json Processing Exception\" }";
            logger.error("", e);
        }
        return new ResponseEntity<>(outputMessage, outputHttpStatus);
    }

    private void sendMessage(String mailTo, String subject, String message) {
        org.springframework.mail.SimpleMailMessage mailMessage = new org.springframework.mail.SimpleMailMessage();
        mailMessage.setSubject(subject);
        mailMessage.setTo(mailTo);
        mailMessage.setText(message);
        mailTemplate.send(mailMessage);
    }

    private String generatePassword() {
        return RandomStringUtils.random(10, true, true);
    }

}
