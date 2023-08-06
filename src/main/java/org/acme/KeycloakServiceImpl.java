package org.acme;

import io.quarkus.logging.Log;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class KeycloakServiceImpl implements KeycloakService{
    @Inject
    UserRepository userRepository;
    private static final String KEYCLOAK_API_URL = "http://localhost:8080/admin/realms/StagePFE/users";
    private static final String KEYCLOAK_TOKEN_URL = "http://localhost:8080/realms/StagePFE/protocol/openid-connect/token";

    @Override
    public List<KeycloakUser> getUsers(String realm, int max) {

        List users = ClientBuilder.newClient()
                .target(KEYCLOAK_API_URL)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer "+getToken())
                .get(List.class);

        Log.info(KEYCLOAK_API_URL);

        return users;
    }

    @Override
    public KeycloakUser get(String id) {
        // Make the GET request to retrieve the user by ID
        Response response = ClientBuilder.newClient()
                .target(KEYCLOAK_API_URL + "/"+id)
                .resolveTemplate("id", id)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer " + getToken())
                .get();

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            // Successful response, parse the JSON and create a KeycloakUser object
            try (JsonReader jsonReader = Json.createReader(response.readEntity(InputStream.class))) {
                JsonObject userJson = jsonReader.readObject();
                String username = userJson.getString("username");
                String email = userJson.getString("email");
                // Extract any other desired fields from the user JSON

                KeycloakUser user = new KeycloakUser(username, email);
                return user;
            } catch (Exception e) {
                // Handle any exceptions that may occur during JSON parsing
                e.printStackTrace();
                return null;
            }
        } else {
            // Error response
            // Handle the error case accordingly
            return null;
        }
    }

    @Override
    public long count() {
        long count = ClientBuilder.newClient()
                .target(KEYCLOAK_API_URL+"/count")
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer "+getToken())
                .get(long.class);

        return count;
    }



    @Override
    public void deleteUser(String id) {
        Response response = ClientBuilder.newClient()
                .target(KEYCLOAK_API_URL+ "/"+id)
                .request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer "+getToken())
                .delete();

        if (response.getStatus() == 204) {
            // User deletion successful
            System.out.println("User deleted successfully.");
        } else {
            // Handle error cases
            System.err.println("Failed to delete user. Response status: " + response.getStatus());
        }
    }

    public String getToken() {
        String realm = "StagePFE";
        String clientId = "stage";
        String grant_type = "client_credentials";
        String clientSecret = "LxRI8s0agd36ufUHd49dcreWNUjYtvc3";
        Form form = new Form()
                .param("grant_type", grant_type)
                .param("client_id", clientId)
                .param("client_secret", clientSecret)
                .param("realm", realm);

        Response response = ClientBuilder.newClient()
                .target(KEYCLOAK_TOKEN_URL)
                .request(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .post(Entity.form(form));

        // Extract the access token from the response
        Map<String, Object> jsonResponse = response.readEntity(new GenericType<Map<String, Object>>() {});

        // Access the necessary values from the JSON response
        String token = (String) jsonResponse.get("access_token");

        return token;
    }
    @Override
    public Response addUser(KeycloakUser user) {
        JsonObject credentials = Json.createObjectBuilder()
                .add("type", "password")
                .add("value", user.getPassword())
                .add("temporary", true)
                .build();

        JsonArray requiredActions = Json.createArrayBuilder()
                .add("UPDATE_PROFILE")
                .build();

        JsonObject userObject = Json.createObjectBuilder()
                .add("username", user.getUsername())
                .add("email", user.getEmail())
                .add("enabled", true)
                .add("credentials", Json.createArrayBuilder().add(credentials))
                .add("requiredActions", requiredActions)
                .build();
        String userJsonString = userObject.toString();
        Entity<String> entity = Entity.entity(userJsonString, MediaType.APPLICATION_JSON);
        Response response = ClientBuilder.newClient()
                .target(KEYCLOAK_API_URL)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer " + getToken())
                .post(entity);
        Log.info(entity);
        return response;
    }


}
