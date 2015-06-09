/**
 * _  _ ____ _ ___  _  _ ____  ___ ____ ___  _    ____
 * |\/| |__| |   /  |  | |  |   |  |__| |__] |    |___
 * |  | |  | |  /__ |__| |__| . |  |  | |__] |___ |___
 * Date: 13-12-6 上午11:08 Created with IntelliJ IDEA.
 *
 */
Tracer = {

	initialize: function () {

		var hyx_ref = document.referrer;

		var hyx_visiturl = document.URL;

		var hyx_userid = this.gc_hyx('userId');

		if (-1 == hyx_userid) {

			hyx_userid = 0;

		}

		var hyx_sessionid = this.gc_hyx('JSESSIONID');

		var hyx_data = '&userid=' + hyx_userid + '&sessionid=' + encodeURI(hyx_sessionid) + '&resolution=' + escape(screen.width + '*' + screen.height) + '&lasturl=' + escape(hyx_ref.substr(0, 512)) + '&visiturl=' + escape(hyx_visiturl.substring(0, 512));

		var _baseUrl = "http://tj.maizuo.com/puttj.htm?domainid=27" + hyx_data + '&jsoncallback=?';

		$('body').append('<img src="' + _baseUrl + '" style="display: none"  />');

	},
	gv_hyx    : function (of) {

		var es = document.cookie.indexOf(";", of);

		if (es == -1) {

			es = document.cookie.length;

		}

		return unescape(document.cookie.substring(of, es));

	},
	gc_hyx    : function (n) {

		var arg = n + "=";

		var alen = arg.length;

		var clen = document.cookie.length;

		var i = 0;

		while (i < clen) {

			var j = i + alen;

			if (document.cookie.substring(i, j) == arg) {

				return this.gv_hyx(j);

			}

			i = document.cookie.indexOf(" ", i) + 1;

			if (i == 0) {

				break;

			}

		}

		return -1;
	}

}

Tracer.initialize();