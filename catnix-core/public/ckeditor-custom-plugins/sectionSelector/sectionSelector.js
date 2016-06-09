
( function() {
    var commands = {
        toolbarFocus: {
            editorFocus: false,
            readOnly: 1,
            exec: function( editor ) {
                var idBase = editor._.selectionSelector.idBase;
                var element = CKEDITOR.document.getById( idBase + '0' );

                // Make the first button focus accessible for IE. (#3417)
                // Adobe AIR instead need while of delay.
                element && element.focus( CKEDITOR.env.ie || CKEDITOR.env.air );
            }
        }
    };

    var emptyHtml = '<span class="cke_path_empty">&nbsp;</span>';

    var extra = '';

    // Some browsers don't cancel key events in the keydown but in the
    // keypress.
    // TODO: Check if really needed.
    if ( CKEDITOR.env.gecko && CKEDITOR.env.mac )
        extra += ' onkeypress="return false;"';

    // With Firefox, we need to force the button to redraw, otherwise it
    // will remain in the focus state.
    if ( CKEDITOR.env.gecko )
        extra += ' onblur="this.style.cssText = this.style.cssText;"';

    var pathItemTpl = CKEDITOR.addTemplate( 'pathItem', '<a' +
    ' id="{id}"' +
    ' href="{jsTitle}"' +
    ' tabindex="-1"' +
    ' class="cke_path_item"' +
    ' title="{label}"' +
    extra +
    ' hidefocus="true" ' +
    ' onkeydown="return CKEDITOR.tools.callFunction({keyDownFn},{index}, event );"' +
    ' onclick="CKEDITOR.tools.callFunction({clickFn},{index}); return false;"' +
    ' role="button" aria-label="{label}">' +
    '{text}' +
    '</a>' );

    CKEDITOR.plugins.add( 'sectionSelector', {
        // jscs:disable maximumLineLength
        lang: 'af,ar,bg,bn,bs,ca,cs,cy,da,de,el,en,en-au,en-ca,en-gb,eo,es,et,eu,fa,fi,fo,fr,fr-ca,gl,gu,he,hi,hr,hu,is,it,ja,ka,km,ko,ku,lt,lv,mk,mn,ms,nb,nl,no,pl,pt,pt-br,ro,ru,si,sk,sl,sq,sr,sr-latn,sv,th,tr,tt,ug,uk,vi,zh,zh-cn', // %REMOVE_LINE_CORE%
        // jscs:enable maximumLineLength
        init: function( editor ) {
            editor._.selectionSelector = {
                idBase: 'cke_sectionselector_' + CKEDITOR.tools.getNextNumber() + '_',
                filters: []
            };

            editor.on( 'uiSpace', function( event ) {
                if ( event.data.space == 'bottom' )
                    initselectionSelector( editor, event.data );
            } );
        }
    } );

    function getElementsList(selectionSelector,editor) {
        var elementsList = selectionSelector.list = [],
            namesList = [],
            filters = selectionSelector.filters,
            isContentEditable = true,

        // Use elementPath to consider children of editable only (#11124).
            elementsChain = editor.elementPath().elements,
            name;

        // Starts iteration from body element, skipping html.
        for ( var j = elementsChain.length; j--; ) {
            var element = elementsChain[ j ],
                ignore = 0;

            if ( element.data( 'cke-display-name' ) )
                name = element.data( 'cke-display-name' );
            else if ( element.data( 'cke-real-element-type' ) )
                name = element.data( 'cke-real-element-type' );
            else
                name = element.getName();

            isContentEditable = element.hasAttribute( 'contenteditable' ) ?
            element.getAttribute( 'contenteditable' ) == 'true' : isContentEditable;

            // If elem is non-contenteditable, and it's not specifying contenteditable
            // attribute - then elem should be ignored.
            if ( !isContentEditable && !element.hasAttribute( 'contenteditable' ) )
                ignore = 1;

            for ( var i = 0; i < filters.length; i++ ) {
                var ret = filters[ i ]( element, name );
                if ( ret === false ) {
                    ignore = 1;
                    break;
                }
                name = ret || name;
            }

            if (name == "div") {
                var hx = element.getChildren().$[0].firstChild;
                elementsList.unshift(hx.textContent);
                namesList.unshift(hx.textContent);
            }

            /*if (!ignore && name != "div" && name != "body" && name != "p" && name != "span") {
                elementsList.unshift(element);
                namesList.unshift(element.$.innerHTML);
            }*/
        }
        editor._.testElementsList = elementsList;
        editor._.testNamesList = namesList;
        return [elementsList,namesList];
    }

    function initselectionSelector( editor, bottomSpaceData ) {
        var spaceId = editor.ui.spaceId( 'path' ),
            spaceElement,
            getSpaceElement = function() {
                if ( !spaceElement )
                    spaceElement = CKEDITOR.document.getById( spaceId );
                return spaceElement;
            },
            selectionSelector = editor._.selectionSelector,
            idBase = selectionSelector.idBase;

        bottomSpaceData.html += '<span id="' + spaceId + '_label" class="cke_voice_label">' + editor.lang.elementspath.eleLabel + '</span>' +
            '<span id="' + spaceId + '" class="cke_path" role="group" aria-labelledby="' + spaceId + '_label">' + emptyHtml + '</span>';

        // Register the ui element to the focus manager.
        editor.on( 'uiReady', function() {
            var element = editor.ui.space( 'path' );
            element && editor.focusManager.add( element, 1 );
        } );

        function onClick( elementIndex ) {
            var element = selectionSelector.list[ elementIndex ];
            if ( element.equals( editor.editable() ) || element.getAttribute( 'contenteditable' ) == 'true' ) {
                var range = editor.createRange();
                range.selectNodeContents( element );
                range.select();
            } else {
                editor.getSelection().selectElement( element );
            }

            // It is important to focus() *after* the above selection
            // manipulation, otherwise Firefox will have troubles. #10119
            editor.focus();
        }

        selectionSelector.onClick = onClick;

        var onClickHanlder = CKEDITOR.tools.addFunction( onClick ),
            onKeyDownHandler = CKEDITOR.tools.addFunction( function( elementIndex, ev ) {
                var idBase = selectionSelector.idBase,
                    element;

                ev = new CKEDITOR.dom.event( ev );

                var rtl = editor.lang.dir == 'rtl';
                switch ( ev.getKeystroke() ) {
                    case rtl ? 39 : 37: // LEFT-ARROW
                    case 9: // TAB
                        element = CKEDITOR.document.getById( idBase + ( elementIndex + 1 ) );
                        if ( !element )
                            element = CKEDITOR.document.getById( idBase + '0' );
                        element.focus();
                        return false;

                    case rtl ? 37 : 39: // RIGHT-ARROW
                    case CKEDITOR.SHIFT + 9: // SHIFT + TAB
                        element = CKEDITOR.document.getById( idBase + ( elementIndex - 1 ) );
                        if ( !element )
                            element = CKEDITOR.document.getById( idBase + ( selectionSelector.list.length - 1 ) );
                        element.focus();
                        return false;

                    case 27: // ESC
                        editor.focus();
                        return false;

                    case 13: // ENTER	// Opera
                    case 32: // SPACE
                        onClick( elementIndex );
                        return false;
                }
                return true;
            } );

        editor.on( 'selectionChange', function() {
            var lists = getElementsList(selectionSelector,editor);
            var elementsList = lists[0];
            var namesList = lists[1];
            var name = "",html = [];
            for ( var iterationLimit = elementsList.length, index = 0; index < iterationLimit-1; index++ ) {
                name = namesList[ index ];
                var item = pathItemTpl.output( {
                        id: idBase + index,
                        label: name,
                        text: name,
                        jsTitle: 'javascript:void(\'' + name + '\')', // jshint ignore:line
                        index: index,
                        keyDownFn: onKeyDownHandler,
                        clickFn: onClickHanlder
                    } );

                html.unshift( item );
            }

            var space = getSpaceElement();
            space.setHtml( html.join( '' ) + emptyHtml );
            editor.fire( 'selectionSelectorUpdate', { space: space } );
        } );

        function empty() {
            spaceElement && spaceElement.setHtml( emptyHtml );
            delete selectionSelector.list;
        }

        editor.on( 'readOnly', empty );
        editor.on( 'contentDomUnload', empty );

        editor.addCommand( 'selectionSelectorFocus', commands.toolbarFocus );
        editor.setKeystroke( CKEDITOR.ALT + 122 /*F11*/, 'selectionSelectorFocus' );
    }
} )();