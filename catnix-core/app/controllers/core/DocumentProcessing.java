package controllers.core;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import it.innove.play.pdf.PdfGenerator;
import play.data.Form;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Html;
import views.html.core.documentLayout.discussion;

import com.fasterxml.jackson.databind.JsonNode;
import common.DocumentDiscussion;


/**
 * This controller converts Html into PDF, add Headers and footers
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
@SubjectPresent
public class DocumentProcessing extends Controller {
	
	/*
	 * Retrive current Host, needed by Play to Pdf
	 */
	private static String getPlayInternalAdresse() {
		return "http://" + request().host();
	}
	/*
	 * Html Document 
	 */
	/**
	 * Retrive Html Document
	 */
	public static Html getSubmittedDraft() {
        return new Html(Form.form().bindFromRequest().get("editorContent"));
    }
	
	
    /*
     * Pdf Generation
     */
    /*
     * The Template must be XHTML compatible.
     * DocumentLayout will help you to have an uniform pdf template
     */
    /**
     * Render a scala template as .pdf
     */
    public Result asPdf(Html page) {
        return PdfGenerator.ok(page, getPlayInternalAdresse());
    }

    /**
     * Render a scala template as .pdf (in bytes)
     */
    public static byte[] asPdfBytes(Html page) {
        return PdfGenerator.toBytes(page, getPlayInternalAdresse());
    }

    /**
     * Render a scala template as .pdf (in stream)
     */
    public Result asPdfStream(final Html page) {
    	Chunks<byte[]> chunk = new ByteChunks() {
            @Override
            public void onReady(Chunks.Out<byte[]> out) {
                out.write(DocumentProcessing.asPdfBytes(page));
                out.close();
            }
        };

        return ok(chunk);
    }


    /*
     * Comment Handle
     */
    /**
     * AJAX action that render a JSON comment into a scala template
     */
    @BodyParser.Of(BodyParser.Json.class)
    public Result renderDiscussion() {
        JsonNode jsonNode = request().body().asJson();
        DocumentDiscussion discussionContent = Json.fromJson(jsonNode, DocumentDiscussion.class);

        return ok(discussion.render(discussionContent, Auth.getCurrentUser()));
    }
}
