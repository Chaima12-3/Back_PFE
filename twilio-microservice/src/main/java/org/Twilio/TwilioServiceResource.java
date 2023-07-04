package org.Twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "twilio-api")
@RegisterForReflection(targets = TwilioResponse.class)
@Path("/twilio")
public class TwilioServiceResource {

    @POST
    @Path("/Messages.json")
    Response sendMessage(TwilioRequest request){
        return sendTwilioMessage(request);
    }



    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendTwilioMessage(TwilioRequest request){
        Twilio.init("AC2272d7161f4d33dbe2cb7d253cdc61ce", "bdb1eead59e14e36022cb0a7af87cb6d"); // Replace with your Twilio Account SID and Auth Token

        Message message = Message.creator(
                        new PhoneNumber(request.getTo()),  // Destination phone number
                        new PhoneNumber("+13159187452"),  // Twilio phone number
                        request.getBody())  // Message body
                .create();

        TwilioResponse response = new TwilioResponse();
        response.setSid(message.getSid());

        return Response.ok(response).build();
    }

}
