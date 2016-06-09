package utils.core;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import play.libs.mailer.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a helper that handle e-mail sending
 *
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
public class Mailer {

    private static final Config CONFIG = ConfigFactory.load("application.conf");
    private MailPerson from = new MailPerson(CONFIG.getString("fromMail.firstname"), CONFIG.getString("fromMail.lastname"), CONFIG.getString("fromMail.mail"));
    private List<MailPerson> to;
    private String subject;
    private String body;
    private List<Attachment> attachments = new ArrayList<>();

    public Mailer(List<MailPerson> to, String subject, String body) {
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public Mailer(List<MailPerson> to, String subject, String body, Map<String, String> replacements) {
        this.to = to;
        this.subject = subject;
        this.body = interpretTemperatingLanguage(body, replacements);
    }

    public Mailer(MailPerson to, String subject, String body) {
        this.to = new ArrayList<>();
        this.to.add(to);
        this.subject = subject;
        this.body = body;
    }

    public Mailer(MailPerson to, String subject, String body, Map<String, String> replacements) {
        this.to = new ArrayList<>();
        this.to.add(to);
        this.subject = subject;
        this.body = interpretTemperatingLanguage(body, replacements);
    }

    /**
     * This decode params like [this] in a string and replace it by the corresponding String find
     * in replacements map
     * @param text Text to be analyse and replace
     * @param replacements Map of attributes eg: {"this": "those"} will replace [this] by those
     * @return String with replace parameters
     */
    private String interpretTemperatingLanguage(String text,
                                                Map<String, String> replacements) {
        Pattern pattern = Pattern.compile("\\[(.+?)\\]");
        Matcher matcher = pattern.matcher(text);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String replacement = replacements.get(matcher.group(1));
            if (replacement != null) {
                matcher.appendReplacement(buffer, "");
                buffer.append(replacement);
            }
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public void addAttachment(Attachment attachment) {
        attachments.add(attachment);
    }

    public void send() {
        Email email = new Email();
        email.setFrom(from.toString());
        email.setTo(Lists.transform(to, new Function<MailPerson, String>() {
            @Nullable
            @Override
            public String apply(MailPerson mailPerson) {
                if (mailPerson != null)
                    return mailPerson.toString();
                else
                    return "";
            }
        }));
        email.setSubject(subject);
        email.setBodyHtml(body);
        email.setAttachments(attachments);

        MailerInstance.getInstance().getMailerClient().send(email);
    }

}
