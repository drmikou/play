
/**
 * AutoSave plugin
 */
CKEDITOR.plugins.add( 'autoSave', {
    init: function( editor ) {
        editor.on('instanceReady',function(){
        	setInterval(function(){
        		saveEditorData(editor);
        	}, 120 * 1000);
    	});
    }
});