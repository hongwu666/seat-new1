requirejs.config({
	debug: true,
	waitSeconds: 15,
	baseUrl: '/App',
	urlArgs: "t=" + (typeof MAIZUO_UPDATE_TIME === 'undefined' ? (new Date()).getTime() : MAIZUO_UPDATE_TIME),
	shim: {
		underscore: {
			deps: ['jquery']
		},
		backbone: {
			deps: ['underscore'],
			exports: 'Backbone'
		},
		pep: {
			deps: ['jquery']
		},
		cookie: {
			deps: ['jquery']
		},
		md5: {
			deps: ['jquery']
		}
	},
	paths: {

		/* core */
		jquery: './lib/jquery/jquery',
		underscore: './lib/underscore/underscore',
		backbone: './lib/backbone/backbone',
		domReady: './lib/require/domReady',

		/* requirejs plugins */
		text: './lib/require/text',

		/* underscore plugins */
		string: './lib/underscore/string',
		underscoreString: './lib/underscore/underscore.string',

		/* asset */
		JSON: './lib/plugin/asset/JSON2/JSON2',
		jstween: './lib/plugin/asset/jstween-1.1/jstween-1.1',
		PubSub: './lib/plugin/jquery/PubSub/PubSub',
		iscroll: './lib/plugin/asset/iscroll/iscroll',

		/* jquery plugins */
		pep: './lib/plugin/jquery/pep/jquery.pep',
		cookie: './lib/plugin/jquery/cookie/jquery.cookie',
		md5: './lib/plugin/jquery/md5/jquery.md5',
		base64: './lib/plugin/jquery/base64/jquery.base64',
		mockjax: './lib/plugin/jquery/mockjax/jquery.mockjax',
		countdown: './lib/plugin/jquery/countdown/jquery.countdown',
		lazyload: './lib/plugin/jquery/lazyload/jquery.lazyload',
		'switch': './lib/plugin/jquery/switch/switch'
	}
});

requirejs.onError = function (err) {
	if (err.requireType === 'timeout') {
		console.log('modules: ', err.requireModules);
		alert('网速不给力,加载页面失败,请刷新页面重试!');
	}
	/*
	 logger.log({
	 action: ''
	 data  : err.requireModules
	 status: 0
	 msg   : '网速不给力,加载页面失败,请刷新页面重试!'
	 })
	 */
	throw err;
};

_.templateSettings = {
	evaluate: /<@([\s\S]+?)@>/g,
	interpolate: /<@=([\s\S]+?)@>/g,
	escape: /<@-([\s\S]+?)@>/g
};

eval($("script[src$='config.js']").html());