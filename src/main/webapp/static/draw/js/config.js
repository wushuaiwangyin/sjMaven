var t0 = new Date();
var MAX_REQUEST_SIZE = 10485760;

var MAX_WIDTH = 6000;
var MAX_HEIGHT = 6000;

// CUSTOM_PARAMETERS - URLs for save and export
var EXPORT_URL = ctxPath+'/export/flow';//EXPORT XML 
var SHARE_HOST = 'http://localhost:8080/draw';// 已不使用

var SAVE_URL = ctxPath+'/export/flow';//rewrite,no use
var OPEN_URL = ctxPath+'static/draw/open.html';

// Paths and files
var STENCIL_PATH = ctxPath + '/static/draw/stencils';
var SHAPES_PATH = ctxPath + '/static/draw/shapes';
var IMAGE_PATH = ctxPath + '/static/draw/images';
// Path for images inside the diagram
var GRAPH_IMAGE_PATH = ctxPath + '/static/draw/img';
var ICONFINDER_PATH = 'iconfinder';
var STYLE_PATH = ctxPath + '/static/draw/styles';
var CSS_PATH = ctxPath + '/static/draw/styles';
var OPEN_FORM = ctxPath + '/static/draw/open.html';
var TEMPLATE_PATH = ctxPath + '/static/draw/templates';
var BG_PATH = ctxPath + "/static/draw/images/backgroudImages";

// Directory for i18 files and basename for main i18n file
var RESOURCES_PATH = ctxPath + '/static/draw/resources';
var RESOURCE_BASE = RESOURCES_PATH + '/dia';

// Specifies connection mode for touch devices (at least one should be true)
var tapAndHoldStartsConnection = true;
var showConnectorImg = true;

var urlParams = (function(url) {
	var result = new Object();
	var idx = url.lastIndexOf('?');

	if (idx > 0) {
		var params = url.substring(idx + 1).split('&');

		for ( var i = 0; i < params.length; i++) {
			idx = params[i].indexOf('=');

			if (idx > 0) {
				result[params[i].substring(0, idx)] = params[i]
						.substring(idx + 1);
			}
		}
	}

	return result;
})(window.location.href);


/**
 * Synchronously adds scripts to the page.
 */
function mxscript(src)	{
	document.write('<script src="'+ctxPath+"/static/draw/"+src+'"></scr' + 'ipt>');
};


/**
 * Asynchronously adds scripts to the page.
 */
function mxinclude(src)	{
	var g = document.createElement('script'); g.type = 'text/javascript'; g.async = true; g.src = src;
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(g, s);
};

var mxLoadResources = false;
var mxLanguage = urlParams['lang'];
var mxLanguages = [ 'en', 'zh' ];
var geBasePath =ctxPath + '/static/draw/js';
var mxBasePath = ctxPath + '/static/draw/mxgraph';

// Used to request grapheditor/mxgraph sources in dev mode
var mxDevUrl = 'http://devhost.jgraph.com/mxgraph2';

// Customizes sharing host
var share = urlParams['sharehost'];

if (share != null) {
	SHARE_HOST = share;
}

// Customizes export URL
var ex = urlParams['export'];

if (ex != null) {
	EXPORT_URL = ex;
}


// Loads gapi for all browsers but IE8 and below
if (urlParams['gapi'] != '0'
		&& (navigator.userAgent.indexOf('MSIE') == -1 || (document.documentMode != null && document.documentMode >= 9))) {
	mxscript('js/ga.js');
}



// Loads JSON for older browsers
if (typeof (JSON) == 'undefined') {
	mxscript('js/json/json2.min.js');
}

// Adds basic error handling
window.onerror = function() {
	var status = document.getElementById('geStatus');

	if (status != null) {
		status.innerHTML = '页面加载失败！请刷新！';
	}
};