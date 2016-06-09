package models.core;

import com.avaje.ebean.Model;


import javax.persistence.GenerationType;
import javax.persistence.*;

@Entity
@Table
public class Guest extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int guestId;
	private String mail;
	private String firstname;
	private String lastname;
	private String password;

	public int getGuestId() {
		return guestId;
	}

	public String getMail() {
		return mail;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setGuestId(int guestId) {
		this.guestId = guestId;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}