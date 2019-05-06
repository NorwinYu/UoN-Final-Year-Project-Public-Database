package com.mybus.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.mybus.dao.OperatorAccountDAO;
import com.mybus.model.Booking;
import com.mybus.model.OperatorAccount;
import com.mybus.service.SessionManager;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Optional;

@Service
public class PDFCreator {
    private VelocityContext context;
    private XMLWorkerHelper xmlWorkerHelper;

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private OperatorAccountDAO operatorAccountDAO;


    @PostConstruct
    public void init(){
        Velocity.init();
        context = new VelocityContext();
        xmlWorkerHelper = XMLWorkerHelper.getInstance();
    }

    @Autowired
    private EmailSender emailSender;

    public void createTaxInvoicePDF(Booking booking, String fileName) {
        try {
            Optional<OperatorAccount> optional = operatorAccountDAO.findById(sessionManager.getOperatorId());
            if(!optional.isPresent()){
              return;
            }
            OperatorAccount operatorAccount = optional.get();
            String folderPath = "emailTemplates/"+operatorAccount.getEmailTemplates();
            InputStream invoiceStream = getClass().getClassLoader().getResourceAsStream(folderPath+"/" +fileName);
            InputStream messageBody = getClass().getClassLoader().getResourceAsStream(folderPath+"/thankYouForTraveling.html");
            String htmlContent = IOUtils.toString(invoiceStream, "UTF-8");
            String emailBody = IOUtils.toString(messageBody, "UTF-8");

            context.put("ticketNo",booking.getTicketNo());
            context.put("bookedBy",booking.getBookedBy());
            context.put("source",booking.getSource());
            context.put("seats",booking.getSeats());
            context.put("boardingTime",booking.getBoardingTime());
            context.put("landmark",booking.getLandmark());
//            context.put("gst",booking.getGrossCollection());
            context.put("boardingPoint",booking.getBoardingPoint());
//            context.put("passengerAddress");
            context.put("journeyDate",booking.getJourneyDate());
            context.put("serviceNumber",booking.getServiceNumber());
            context.put("destination",booking.getDestination());
            context.put("NoOfPassengers",booking.getSeatsCount());
            context.put("Bus Type",booking.getServiceName());
            context.put("netAmt",booking.getNetAmt());
            context.put("TicketAmt",booking.getOriginalCost());
            context.put("TypeOfBooking",booking.getPaymentType());
            context.put("passengerName", booking.getName());
            context.put("tax", booking.getServiceTax());

            StringWriter str = new StringWriter();
            Velocity.evaluate(context, str, fileName, htmlContent);
//            emailBody = String.format(emailBody, booking.getName());


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            InputStream is = new ByteArrayInputStream(str.toString().getBytes());
            xmlWorkerHelper.parseXHtml(writer, document, is);
            document.close();
            byte[] contents = byteArrayOutputStream.toByteArray();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contents);
            emailSender.sendEmailWithAttachment("harish1400quad@gmail.com", emailBody, "Srikrishna Travels - Your tax invoice","Tax Invoice"
                    , byteArrayInputStream);
            byteArrayOutputStream.close();
            byteArrayInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Booking booking = new Booking();
        booking.setName("Mark");
        booking.setTicketNo("SK43453");
        booking.setServiceTax(3000);
        new PDFCreator().createTaxInvoicePDF(booking, "welcome.html");
    }
}


