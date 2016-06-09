package controllers.core;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import javax.inject.Inject;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.mailer.MailerClient;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import utils.core.MailerInstance;
import utils.core.MailPerson;
import utils.core.Mailer;
import utils.core.graph.GraphSerie;
import views.html.core.page.demo.*;
import views.html.core.graphTool.graphHisto;
import views.html.core.modelDocument.demo.*;
import formData.core.demo.DemoFormData;
import views.html.core.page.demo.pageDemo;


/**
 * This controller aim to show examples of implementations
 * It show the correct way for doing typical actions
 *
 * @author pelo
 *
 */
/*
 * Form management
 * ---------------
 * To fill a form, you will need a formData corresponding to the fields (to parametrize your form).
 * This formData will contain the logic and modification to the BDD.
 *
 * A controller should not have any direct interaction with the BDD.
 *
 * If you deal with an Object to pass to client side in a form, pass its PK as post hidden field,
 * and retrieve if using formFetcher.
 *
 * Beside this, Parametrized form allow the client side to have automated control on fields.
 *
 *
 * Rq: Dynamic form
 * There is another way to use form, Dynamic form. It should not be used because it is not compatible with
 * Twitter bootstrap integration (formTool).
 *
 *
 *
 * Flash messages
 * --------------
 * You can post flash messages, they are store in the flash() session and auto retrieved by mainLayout.
 *
 */
@SubjectPresent
public class Demo extends Controller {
	@Inject DocumentProcessing documentProcessing;
	@Inject MailerClient mailerClient;

	/*
     * Async Widget
     */


    /*
     * Fragment
     */
	public Result renderDemoSearchBar() {
        return ok(views.html.core.widget.searchBar.render());
    }

    public Result renderDemoGraph(long startTimeStamp, long endTimeStamp) {
    	Date startDate = new Date(startTimeStamp);
    	Date endDate = new Date(endTimeStamp);

    	Calendar start = Calendar.getInstance();
    	start.setTime(startDate);
    	Calendar end = Calendar.getInstance();
    	end.setTime(endDate);

    	Map<GraphSerie, Map<Double, Double>> graph = new HashMap<GraphSerie, Map<Double, Double>>();
    	Map<Double, Double> graphData = new HashMap<Double, Double>();

    	for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
    		graphData.put(new Double(date.getTime()), Math.random());
    	}
    	graph.put(new GraphSerie("toto"), graphData);

        return ok(graphHisto.render("ajaxFunction", "title", graph, new Html(""), false, true, 12));
    }

	/*
	 * Simple page
	 */
    public Result index() {
    	System.out.print(Play.application().path());
        return ok(pageDemo.render());
    }

    /*
     * Form Submission
     */
    public Result demoPost() {
    	DemoFormData formData = new DemoFormData();

    	formData.t1 = "préremplis dans le controller";
    	formData.b1 = false;
    	formData.h = "hidden";
    	formData.b2 = true;

    	Form<DemoFormData> form = Form.form(DemoFormData.class).fill(formData);
        return ok(postDemo.render(form));
    }

    public Result demoPostPost() {
    	Form<DemoFormData> form = Form.form(DemoFormData.class).bindFromRequest();

        if(form.hasErrors()) {
            return badRequest(postDemo.render(form));
        } else {
			return ok(form.toString());
    	}
    }

    public Result demoDynPost() {
        DynamicForm requestData = Form.form().bindFromRequest();

        return ok(new Html(requestData.get("demoName") + requestData.get("demoFilledName")));
    }

    /*
     *  Editor
     */
    public Result demoEditor() {
    	Html content = demoDoc.render();

        return ok(editorDemo.render(content, Auth.getCurrentUser()));
    }

    public Result demoEditorPost() {
		return ok(DocumentProcessing.getSubmittedDraft());
    }

    /*
     * Print Editor
     */
    public Result demoPrintEditor() {
    	Html content = demoDoc.render();
        return ok(editorPrintDemo.render(content, Auth.getCurrentUser()));
    }

    public Result demoPrintEditorPost() {
    	Html document = DocumentProcessing.getSubmittedDraft();
		return ok(DocumentProcessing.asPdfBytes(document));
    }

    /*
     * Flash messages
     */
    public Result demoFlash() {

    	flash("alert", "ALERT");
    	flash("success", "SUCCESS");
    	flash("error", "ERREUR");
    	flash("warning", "WARNING");
    	flash("information", "INFO");

        return ok(pageDemo.render());
    }

    /*
     * Cast Html page to Pdf
     */
    public Result demoPdf() {
        return documentProcessing.asPdf(pageDemo.render());
    }

    /*
     * Dynamic table
     */
    public Result demoTable() {
        return ok(tableDemo.render());
    }

	public Result demoMail() {
		MailerInstance.getInstance().setMailerClient(mailerClient);
		Mailer mailer = new Mailer(new MailPerson("Big", "Boss", "jbwatenberg@juniorisep.com"), "[CATNIX][DEV] POC mail", "<h1>Mail...ix</h1>");
		mailer.send();
		return ok("POC");
	}


    /*
	 * Graph presentation
	 */
    public Result demoGraph() {
    	//Pie
    	Map<GraphSerie, Double> pieMap = new HashMap<GraphSerie, Double>();
    	pieMap.put(new GraphSerie("toto", "red"), 15.0);
    	pieMap.put(new GraphSerie("toti"), 75.0);

    	//Function
    	Map<GraphSerie, Map<Double, Double>> functionMap = new HashMap<GraphSerie, Map<Double, Double>>();
    	Map<Double, Double> f1Data = new HashMap<Double, Double>();
    	Map<Double, Double> f2Data = new HashMap<Double, Double>();

    	for(double i = 0.0; i < 10; i += 0.5) {
    		f1Data.put(i, Math.cos(i));
    		f2Data.put(i, Math.sin(i));
    	}
    	functionMap.put(new GraphSerie("toto", "red"), f1Data);
    	functionMap.put(new GraphSerie("toti"), f2Data);

    	//Histo
    	Map<GraphSerie, Map<Double, Double>> histoMap = new HashMap<GraphSerie, Map<Double, Double>>();
    	Map<Double, Double> h1Data = new HashMap<Double, Double>();
    	Map<Double, Double> h2Data = new HashMap<Double, Double>();

    	for(double i = 0.0; i < 10; i += 0.5) {
    		h1Data.put(i, Math.random());
    		h2Data.put(i, Math.random());
    	}
    	histoMap.put(new GraphSerie("toto", "red"), h1Data);
    	histoMap.put(new GraphSerie("toti"), h2Data);

    	//Time Section
    	Calendar c = Calendar.getInstance();
    	
    	//Function Time
    	Map<GraphSerie, Map<Double, Double>> functionTimeMap = new HashMap<GraphSerie, Map<Double, Double>>();
    	Map<Double, Double> fTimeData = new HashMap<Double, Double>();
    	
    	c = Calendar.getInstance();
    	for(int i=0; i < 12; i++) {
            c.add(Calendar.MONTH, 1);
    		long val = c.getTime().getTime();
    		fTimeData.put(new Double(val), Math.cos(val));
    	}
    	functionTimeMap.put(new GraphSerie("toto"), fTimeData);
    	
    	//Histo Time
    	Map<GraphSerie, Map<Double, Double>> histoTimeMap = new HashMap<GraphSerie, Map<Double, Double>>();
    	Map<Double, Double> hTime = new HashMap<Double, Double>();

    	c = Calendar.getInstance();
    	for(int i=0; i < 12; i++) {
            c.add(Calendar.MONTH, 1);
    		hTime.put(new Double(c.getTime().getTime()), Math.random());
    	}
    	histoTimeMap.put(new GraphSerie("toto"), hTime);

        return ok(graphDemo.render(pieMap, functionMap, histoMap, functionTimeMap, histoTimeMap));
    }
    

    /*
     * Pdf in iFrame in page
     */
    public Result demoPreviewPdf() {
        return ok(previewPdfDemo.render());
    }

}
