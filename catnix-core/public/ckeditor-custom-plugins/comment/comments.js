/**
 * New Discuss dialog
 */
CKEDITOR.dialog.add('newDiscussDialog', function( editor ) {
    return {
        title: 'Ajouter une discussion',
        minWidth: 400,
        minHeight: 200,

        contents: [
            {
                id: 'tab-basic',
                label: 'Discussion',
                elements: [
                    {
                        type: 'textarea',
                        id: 'discussionSubject',
                        label: 'Discussion',
                        validate: CKEDITOR.dialog.validate.notEmpty("Le sujet de la discussion ne peut pas être vide.")

                    }
                ]
            }
        ],
        onOk: function() {
        	createNewDiscussion(this,editor);
        },
        onShow: function() {

            //@todo refactor this variable (commentDialog should be a best name)
            var ckDialog = this;

            //@todo refactor this with ckDialog.getContentElement which return an element of the dialog

            /**
             * To avoid scope errors and because closures doesn't works in this case we define a global variable
             * that correspond to the ok button of the "new discuss dialog"
             * @type {{}}
             */
            editor.commentDialog = {};
            editor.commentDialog.ckOk = ckDialog._.buttons['ok'];

            var textareaId = ckDialog._.contents["tab-basic"]["discussionSubject"].domId;

            var $textarea = $(ckDialog._.element.$).find("#" + textareaId).find("textarea");

            $textarea.unbind("keyup").on("keyup", function (e) {
                if (e.keyCode == 13 && e.ctrlKey) {
                    e.stopPropagation();
                    e.preventDefault();
                    var content = $(this).val();
                    if (content != "") {
                        editor.commentDialog.ckOk.click();
                    } else {
                        alert("Vous ne pouvez pas créer un commentaire vide");
                    }
                    return false;
                }
            });
        }
    };
});

/*
 * Discussion Object
 */
function buildNewDiscussionData(subjectString) {
    var discussionData = {
        timeStamp: Date.now(),
        subject: subjectString,
        discussionCreator: CKEDITOR.currentUserLogin,
        status: "open",
        commentList: []
    };
    return discussionData;
}

/*
 * Comment Object
 */
function buildNewCommentData(commentContent) {
	var commentData = {
		speakerLogin: CKEDITOR.currentUserLogin,
		postDateTimeStamp: Date.now(),
		content: commentContent
    };
    return commentData;
}


/*
 * Discussion Surrounder
 */
function buildNewDiscussionSurrounder(editor, subject) {
    var selectedText = editor.getSelection().getSelectedText();

    var discussionData = buildNewDiscussionData(subject);

    var contents = editor.getSelectedHtml();
    var fragment = contents.$.childNodes;

    if (fragment.length>0) {
        for (var i = fragment.length - 1; i >= 0; i--) {
            var $part = $(fragment[i]);

            if ($part.hasClass("commented")) {
                alert("Cette zone est deja commentée.");
                return [false, $part[0]];
            }

            /*
            Detect multiple tags in comments
             */
            if (typeof $part.prop("tagName") !== "undefined" && $part.prop("tagName").toLowerCase() != "span") {
                if (isPorHxNode($part.prop("tagName").toLowerCase())) {
                    alert("Vous ne pouvez pas commenter plusieurs paragraphes ou titres en même temps.");
                    return [false, $part[0]];
                }
            }
        }
    }
    var surrounder = new CKEDITOR.dom.element("span");
    surrounder.setText(selectedText);
    surrounder.id = "commentedOn"+discussionData.timeStamp.toString();

    updateDiscussionSurrounder(surrounder, discussionData);

    return [true,surrounder];
}

function updateDiscussionSurrounder(surrounder, discussionData) {
    surrounder.data("discussion", JSON.stringify(discussionData));

    surrounder.removeClass("open");
    surrounder.removeClass("progress");
    surrounder.removeClass("closed");

    surrounder.addClass("commented").addClass(discussionData.status);

    surrounder.$.click();
}

function extractDiscussionJsonData(discussionSurrounder) {
    return discussionSurrounder.$.dataset.discussion;
}

function extractDiscussionData(discussionSurrounder) {
    return JSON.parse(discussionSurrounder.$.dataset.discussion);
}


/*
 * New Discussion
 */
function createNewDiscussion(dialog,editor){

    var subject = dialog.getValueOf( 'tab-basic', 'discussionSubject' );

    var discussionSurrounder = buildNewDiscussionSurrounder(editor, subject);

    if (!discussionSurrounder[0]) {
        //We can't create the discuss because there is multiples tags or this section is already commented
    } else {
        loadDiscussionAsidePanel(discussionSurrounder[1]);
        editor.insertElement(discussionSurrounder[1]);
        discussionSurrounder[1].$.click();
    }

}


/*
 * Load or ReLoad asideBar
 */
function loadDiscussionAsidePanel(discussionSurrounder){
	discussionSurrounder.on('click', function(evt) {
		coreJsRoutes.controllers.core.DocumentProcessing.renderDiscussion().ajax({
			type : "POST",
            contentType : 'application/json;',
            data : extractDiscussionJsonData(discussionSurrounder),
            success : function(data) {
                $(".aside").html(data);

                //Can do better - pass data and link only this part
                linkDiscussionMouseOver(discussionSurrounder);
                linkDiscussionStatusChange(discussionSurrounder);
                linkDiscussionCommentAdd(discussionSurrounder);

                aside.show();
            },
            error : function () {
                console.error("error on ajax load of discussionAsidePanel");
            }
		});
    });
}

function reloadExistingDiscussion(editor){
	var existingDiscussionList = editor.document.find('.commented');

	for ( var i = 0; i < existingDiscussionList.count(); i++ ) {
	    var existingDiscussion = existingDiscussionList.getItem(i);
	    loadDiscussionAsidePanel(existingDiscussion, existingDiscussion.$.dataset.discussion)
	}
}


/*
 * Mouse Over
 */
function linkDiscussionMouseOver(discussionSurrounder) {
	$(".showComment").mouseenter(function() {
		discussionSurrounder.addClass("fired");
    }).mouseleave(function() {
		discussionSurrounder.removeClass("fired");
    });
}

/*
 * Discussion Status
 */
function linkDiscussionStatusChange(discussionSurrounder) {

	$("#setOpen").click(function() {
        updateDiscussionStatus(discussionSurrounder, "open");
    });

	$("#setProgress").click(function() {
        updateDiscussionStatus(discussionSurrounder, "progress");
    });

	$("#setClosed").click(function() {
        updateDiscussionStatus(discussionSurrounder, "closed");
    });
}

function updateDiscussionStatus(discussionSurrounder, newStatus) {
    var discussionData = extractDiscussionData(discussionSurrounder);
    discussionData.status = newStatus;

    updateDiscussionSurrounder(discussionSurrounder, discussionData);
    loadDiscussionAsidePanel(discussionSurrounder);
}

/*
 * Discussion Comments
 */
function linkDiscussionCommentAdd(discussionSurrounder) {

	$("#commentEditor").keydown(function(e) {
        if (e.keyCode == 13 && e.ctrlKey) {
            e.preventDefault();

            var commentData = buildNewCommentData($("#commentEditor").val());
            addDiscussionComment(discussionSurrounder, commentData);
        }
    });
}

function addDiscussionComment(discussionSurrounder, commentData) {
    var discussionData = extractDiscussionData(discussionSurrounder);
    discussionData.commentList.push(commentData);

    updateDiscussionSurrounder(discussionSurrounder, discussionData);
    loadDiscussionAsidePanel(discussionSurrounder);
}


/**
 * Comment plugin
 */
CKEDITOR.plugins.add( 'comment', {
    icons: 'comment',

    beforeInit: function( editor ) {
        console.log("befor plugin init");
    },
    onLoad: function() {
        console.log("onload");
        //CKEDITOR.config.contentCss.push( CKEDITOR.config.customPluginPath + 'comment/comment.css' );
    },
    init: function( editor ) {
        editor.addCommand( 'comment', new CKEDITOR.dialogCommand( 'newDiscussDialog' ));
        editor.ui.addButton( 'comment', {
            label: 'Commenter',
            command: 'comment',
            toolbar: 'about'
        });
        editor.on('dataReady',function(){
        	reloadExistingDiscussion(editor);
            console.log("data ready !");
    	});
        console.log("plugin added");
    }
});


