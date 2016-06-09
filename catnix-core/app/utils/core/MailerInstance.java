package utils.core;

import play.libs.mailer.MailerClient;

/**
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
public class MailerInstance {
    private static MailerInstance ourInstance = new MailerInstance();
    private MailerClient mailerClient = null;

    public static MailerInstance getInstance() {
        return ourInstance;
    }

    private MailerInstance() {
    }

    public MailerClient getMailerClient() {
        return mailerClient;
    }

    public void setMailerClient(MailerClient mailerClient) {
        if (this.mailerClient == null) {
            this.mailerClient = mailerClient;
        }
    }
}
