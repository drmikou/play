
CKEDITOR.plugins.add( 'formatProvider', {
    init: function( editor ) {
        editor.activeFormats = ["h2"];
        editor.on("instanceReady",function() {
            console.log(editor);
            var comboFormat = editor.ui.instances.Format;
            comboFormat.onOpen = function(){
                comboFormat.showAll();
                for(var style in comboFormat.allowedContent) {
                    var elementFormat = comboFormat.allowedContent[style].element;
                    if (editor.activeFormats.indexOf(elementFormat) == -1) {
                        comboFormat.hideItem(elementFormat);
                    }
                }
            };

            comboFormat.onClick = function( value ) {
                editor.focus();
                editor.fire( 'saveSnapshot' );

                var style = editor.activeFormats[ value ],
                    elementPath = editor.elementPath();

                /*editor[ style.checkActive( elementPath, editor ) ? 'removeStyle' : 'applyStyle' ]( style );*/

                editor.insertHtml(
                  '<div class="documentSection">' +
                  '<'+value+'></'+value+'>' +
                  '<p></p>' +
                  '</div>' +
                  '<p></p>'
                );

                // Save the undo snapshot after all changes are affected.
                setTimeout( function() {
                    editor.fire( 'saveSnapshot' );
                }, 0 );
            };

        });
    }
});