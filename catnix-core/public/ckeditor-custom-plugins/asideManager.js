/**
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */
var aside = {
    $aside: $(".aside"),
    $documentEditorDocumentContainer: $("#cke_1_contents"),
    show:  function() {
        if (aside.$aside.hasClass("hide")) {
            aside.$aside.removeClass("hide");
            $("#cke_1_contents").height(Math.max(aside.$documentEditorDocumentContainer.height(),aside.$aside.height())+40);
        }
    },
    hide: function () {
        if (!aside.$aside.hasClass("hide")) {
            aside.$aside.addClass("hide");
        }
    },
    toggle: function () {
        if(aside.$aside.hasClass("hide")) {
            aside.show();
        } else {
            aside.hide();
        }
    }
};