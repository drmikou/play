/**
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */

var pdfStyle = "";

/**
 * Toggle aside bar
 */
CKEDITOR.plugins.add( 'asideToggler', {
    icons: 'asideToggler',
    init: function( editor ) {
        editor.addCommand( 'asideToggler', {
            exec: function( editor ) {
                if (aside.$aside.hasClass("hide")) {
                    aside.show();
                } else {
                    aside.hide();
                }
            }
        });
        editor.ui.addButton( 'asideToggler', {
            label: 'Toggle aside bar',
            command: 'asideToggler',
            toolbar: 'about'
        });
    }
});


