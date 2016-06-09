/**
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
/**
 * Plugin that allow toggling editor to full page
 */
CKEDITOR.plugins.add( 'fullscreen', {
    icons: 'fullscreen',
    init: function( editor ) {
        editor.addCommand( 'fullscreen', {
            exec: function( editor ) {
                var $editor = $("#editor");
                var $editorTop = $("#cke_1_top");
                var $content = $("#cke_1_contents");
                var $editorBottom = $("#cke_1_bottom");
                var $aside = $("#documentEditorAsideBarContainer");
                if ($editor.css("position") != "fixed") {

                    $editorTop.css("position","fixed");
                    $editorTop.css("top",50);
                    $editorTop.css("left",0);
                    $editorTop.css("right",0);
                    $editorTop.css("z-index",1000);

                    $editorBottom.css("position","fixed");
                    $editorBottom.css("bottom",0);
                    $editorBottom.css("left",0);
                    $editorBottom.css("right",0);
                    $editorBottom.css("z-index",1000);
                    $editorBottom.css("margin-bottom",0);

                    editor.document.getBody().setStyles({ 'margin-top':+$editorTop.height()+20+'px',
                        'margin-bottom':+$editorBottom.height()+20+'px' });

                    $editor.css("position", "fixed");
                    $editor.css("top", "0");
                    $editor.css("left", "0");
                    $editor.css("right", "0");
                    $editor.css("bottom", $editorBottom.height()+20+"px");
                    $editor.css("z-index", 1020);
                    $editor.css("margin-top", 50+"px");
                    $editor.height(window.innerHeight-50);
                    $content.height(window.innerHeight-50);
                    $(".side_container").height($editor.height());

                    $aside.css("padding-top",$editorTop.height()+10+"px");
                    $aside.css("background","#FAFAFA");
                } else {
                    editor.full = false;
                    $editor.attr("style","");
                    $content.height(200);
                    $(".side_container").height("auto");
                    $editorTop.attr("style","height: auto; -moz-user-select: none;");
                    $editorBottom.attr("style","-moz-user-select: none;");

                    editor.document.getBody().setStyles({ 'margin-top':10+'px','margin-bottom':10+'px' });
                    $aside.css("padding-top","0px");
                    $aside.css("background","transparent");
                }
            }
        });
        editor.ui.addButton( 'fullscreen', {
            label: 'Put it in fullscrren',
            command: 'fullscreen',
            toolbar: 'about'
        });
    }
});
