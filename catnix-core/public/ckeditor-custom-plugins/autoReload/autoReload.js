/**
 * Reload Confirm dialog
 */
CKEDITOR.dialog.add('reloadDocumentDialog', function(editor) {
    return {
        title: 'Recharger le document ?',
        minWidth: 250,
        minHeight: 150,

        contents: [
            {
                id: 'tab-basic',
                label: 'Discussion',
                elements: [
					{
						type : 'html',
						html : 'Il existe déjà une version de ce docuement sur le serveur,<br>voulez vous la recharger ?'		
					}
                ]
            }
        ],
        onOk: function() {
        	reloadEditorFile(editor, editor.config.tmpFilePath)
        }
    };
});




/**
 * AutoReload plugin
 */
CKEDITOR.plugins.add( 'autoReload', {
    afterInit: function( editor ) {
        editor.on('instanceReady',function(){
        	askForReloading(editor);
    	});
    }
});

function askForReloading(editor) {
	var fileName = getDocumentFileName(editor);
	
	coreJsRoutes.controllers.core.FileManager.asyncCheckTmpFileExists(fileName).ajax({
    	type : "POST",
        contentType : 'text/plain; charset=utf-8',
        success : function(data) {
        	var filePath = data;
        	
        	if(filePath !== "false") {
        		editor.config.tmpFilePath = filePath;
                var reloadDialog = new CKEDITOR.dialogCommand('reloadDocumentDialog');
                reloadDialog.exec(editor)
        	} else {
        		editor.config.tmpFilePath = "";
        	}
        },
        error : function () {
        	console.error("error on check exist file");
        }
	});
}

function reloadEditorFile(editor, filePath) {
	coreJsRoutes.controllers.core.FileManager.downloadFile(filePath).ajax({
		type : "GET",
        contentType : 'text/plain; charset=utf-8',
        success : function(data) {
        	editor.setData(data);
        },
        error : function () {
        	console.error("error on reloading file");
        }
	});
}