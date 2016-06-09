
var styleFormat = 'h1,h2,h3,h4,p'.split(',');


function onEnter(editor) {
	console.log('enter');
	console.log(editor.getSelection());
}

/**
 * This method return true if the element is a
 * 	- Paragraph (p)
 * 	- Title (hx)
 * 		- 1 (x = 1)
 * 		- 2 (x = 2)
 * 		- 3 (x = 3)
 * 		- 4	(x = 4)
 *
 * @param nodeName
 * @returns {boolean}
 */
function isPorHxNode(nodeName) {
	return (nodeName == 'p'||nodeName == 'h1'||nodeName == 'h2'||nodeName == 'h3'||nodeName == 'h4');
}

/**
 * This method returns the dom node that correspond to {@link isPorHxNode(nodeName) isPorHxNode} conditions
 * and that it's localized after the node which was pass in argument
 * @see isPorHxNode(nodeName)
 * @param node
 * @returns {*}
 */
function getNextPorHxNode(node) {
	var prevnode = node;
	while((prevnode = node) && ((node = node.nextSibling)||(node = prevnode.parentNode.nextSibling)) && (!isPorHxNode(node.nodeName.toLowerCase())) && node.nodeName.toLowerCase() != "div");
	if(node && node.nodeName.toLowerCase() == "div") node = node.firstChild;
	return (node)?node:prevnode;
}

function onCtrlEnter(editor) {
	var initialElement = editor.getSelection().getStartElement().$;

	if (!isPorHxNode(initialElement.nodeName.toLowerCase())) {
		initialElement = initialElement.parentNode;
	}

	var node = getNextPorHxNode(initialElement);

	var element = new CKEDITOR.dom.element( node, editor.document );

	var range = new CKEDITOR.dom.range(editor.document);
	range.moveToElementEditStart(element);
	editor.getSelection().selectRanges([range]);
	editor.getSelection().scrollIntoView();

}

function onFormatChange(editor, formatTag) {
	editor.activeFormats = getAllowedFormat(formatTag);
}

function getAllowedFormat(formatTag) {
	allowedFormatTag = [];
	limitTag = styleFormat.indexOf(formatTag);

	styleFormat.forEach(function (value, i) {
		if(i > limitTag) {
			allowedFormatTag.push(value);
		}
	});

	return allowedFormatTag;
}


CKEDITOR.plugins.add( 'enterBehaviour', {
    init: function( editor ) {
        editor.on('instanceReady',function(){

            /*editor.addCommand('enterCmd', {
        		exec : function( editor ) {
        			onEnter(editor);
        			return;
        		}
            });*/

            editor.addCommand('ctrlEnterCmd', {
        		exec : function( editor ) {
        			onCtrlEnter(editor);
        			return;
        		}
           	});

            editor.setKeystroke([[CKEDITOR.CTRL + 13, 'ctrlEnterCmd'],
            //                     [CKEDITOR.SHIFT + 13, 'ctrlEnterCmd']
            ] );

            styleFormat.forEach(function (value, i) {
            	var style = new CKEDITOR.style( { element : value } );
                editor.attachStyleStateChange( style, function( state ) {
                	if ( state == CKEDITOR.TRISTATE_ON ) {
                        //Detect a blank p
                        var currentNode = editor.getSelection().getStartElement().$;
                        if (currentNode.nodeName.toLowerCase() == "p" && $(currentNode).text().length == 0) {
                            onFormatChange(editor,currentNode.parentNode.firstChild.nodeName.toLowerCase());
                        } else {
                            onFormatChange(editor, style.element);
                        }
                	}
                });
            });


    	});
    }
});
