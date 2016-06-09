package ldapix;

import com.fasterxml.jackson.databind.JsonNode;
import com.typesafe.config.ConfigFactory;
import play.Logger;
import play.libs.Json;
import play.libs.ws.*;
import play.libs.F.Function;
import play.libs.F.Promise;

/**
 * This class is a web service client for LDAPIX API
 * @see http://git.dev.juniorisep.com/jbwatenberg/ldapix/blob/master/README.md
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
public class LdapixAccess {

    public Promise<JsonNode> connect(String username, String password) {
        String ldapixUrl = ConfigFactory.load("application.conf").getString("ldapix.url");
        MCrypt mCrypt = new MCrypt();
        try {
            username = MCrypt.bytesToHex(mCrypt.encrypt(username));
            password = MCrypt.bytesToHex(mCrypt.encrypt(password));
            Promise<JsonNode> jsonPromise = WS.url(ldapixUrl)
                    .setContentType("application/x-www-form-urlencoded")
                    .post("username=" + username + "&password=" + password).map(
                            new Function<WSResponse, JsonNode>() {
                        public JsonNode apply(WSResponse response) {
                            JsonNode json;
                            if (response.getBody().equals("Error in search query")) {
                                json = Json.parse("{\"result\":false}");
                            } else {
                                json = response.asJson();
                            }
                            return json;
                        }
                    }
            );
            return jsonPromise;
        } catch (Exception e) {
            Logger.error("An error occurred while trying to connect to LDAPIX", e);
        }
        return null;
    }

}
