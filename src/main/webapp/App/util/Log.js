/**
 * _  _ ____ _ ___  _  _ ____  ___ ____ ___  _    ____
 * |\/| |__| |   /  |  | |  |   |  |__| |__] |    |___
 * |  | |  | |  /__ |__| |__| . |  |  | |__] |___ |___
 * Date: 2014/5/6 19:12 Created with IntelliJ IDEA.
 *
 */
var funs = ['assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log', 'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd', 'timeStamp', 'trace', 'warn'];
var log = {};
$.each(funs || [], function (i, v) {
	log[v] = function () {
	};
});
if (typeof MAIZUO_DEBUG === 'undefined') {
	MAIZUO_DEBUG = true;
}
var debug = _.str.toQueryParams(location.href).debug;
debug = MAIZUO_DEBUG || debug;
if (debug) {
	if (typeof window.console == 'undefined') {
		window.console = log;
		window.log = log;
	} else {
		window.log = window.console;
	}
} else {
	window.console = log;
	window.log = log;
}
