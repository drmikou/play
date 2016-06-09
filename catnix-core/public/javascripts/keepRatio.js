/**
 * @author Jean-Baptiste WATENBERG <jbwatenberg@juniorisep.com>
 */

/**
 * Our keepRatio plugin
 * This jquery plugin allow to keep the format ratio while you are resizing your document.
 *
 * Usage :
 * $(selector).keepRatio(); //keep the selected element to the A4 ratio
 * $(selector).keepRatio({
 *      format: 'yourFormat', // Must be defined in the ratios object
 *      ratios: {'yourFormat':valueOfTheFormat}
 * });
 */
(function( $ ) {

    $.fn.keepRatio = function(options) {
        var opts = $.extend( {}, $.fn.keepRatio.defaults, options );
        var $this = this;


        if (typeof opts.ratios[opts.format] !== "undefined") {
            function applyRatio() {
                $this.height($this.width() * opts.ratios[opts.format]);
            }
            applyRatio();
            var oldResize = window.onresize;
            function resize(e) {
                applyRatio();

                if (typeof oldResize === 'function') {
                    oldResize();
                }
            }
            window.onresize = resize;
        } else {
            console.error("[KeepRatio] The selected format must be defined in the ratios object");
        }
    };
    //plugin default options
    $.fn.keepRatio.defaults = {
        format: 'A4',
        ratios: {'A4':1.4142}
    };

})(jQuery);