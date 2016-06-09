package utils.core;

import org.apache.commons.lang3.StringUtils;

/**
 * It represents a person which is enrolled in a mailing process
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
public class MailPerson {

    private String firstname;
    private String lastname;
    private String mail;

    public MailPerson(String mail) {
        this.mail = mail;
    }

    public MailPerson(String firstname, String lastname, String mail) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.mail = mail;
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(firstname) + StringUtils.capitalize(lastname) +
                "<" + mail + ">";
    }
}
