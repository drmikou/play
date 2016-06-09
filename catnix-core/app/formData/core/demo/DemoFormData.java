package formData.core.demo;

import java.util.Date;
import java.util.List;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;

public class DemoFormData {

	public boolean b1;
	public boolean b2;

	@Formats.DateTime(pattern = "dd-MM-yyyy")
	public Date d;

	@Formats.DateTime(pattern = "HH:mm")
	public Date t;

	@Formats.DateTime(pattern = "dd-MM-yyyy - HH:mm")
	public Date dt;

	public String h;

	public String email;

	public int i1;
	public int i2;

    @Required
	public String f;

	public String p;

	public String t1;
	public String t2;

    public List<String> strList;

}
