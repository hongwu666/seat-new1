/**
 * _  _ ____ _ ___  _  _ ____  ___ ____ ___  _    ____
 * |\/| |__| |   /  |  | |  |   |  |__| |__] |    |___
 * |  | |  | |  /__ |__| |__| . |  |  | |__] |___ |___
 * Date: 14-1-3 下午3:48 Created with IntelliJ IDEA.
 *
 */
var Util = {
	toQueryParams   : function (str) {
		var ret = {};
		if (typeof str == 'string') {
			var search = str.replace(/^\s+/, '').replace(/\s+$/, '').match(/([^?#]*)(#.*)?$/);//
			if (!search) {
				return {};
			}
			var searchStr = search[1];
			var searchHash = searchStr.split('&');
			for (var i = 0, len = searchHash.length; i < len; i++) {
				var pair = searchHash[i];
				if ((pair = pair.split('='))[0]) {
					var key = decodeURIComponent(pair.shift());
					var value = pair.length > 1 ? pair.join('=') : pair[0];
					if (value != undefined) {
						value = decodeURIComponent(value);
					}
					if (key in ret) {
						if (ret[key].constructor != Array) {
							ret[key] = [ret[key]];
						}
						ret[key].push(value);
					} else {
						ret[key] = value;
					}
				}
			}
		}
		return ret;
	},
	toQueryString   : function (obj) {
		function toQueryPair(key, value) {
			if (typeof value == 'undefined') {
				return key;
			}
			return key + '=' + encodeURIComponent(value === null ? '' : String(value));
		}

		var ret = [];
		for (var key in obj) {
			key = encodeURIComponent(key);
			var values = obj[key];
			if (values && values.constructor == Array) {
				var queryValues = [];
				for (var i = 0, len = values.length, value; i < len; i++) {
					value = values[i];
					queryValues.push(toQueryPair(key, value));
				}
				ret = ret.concat(queryValues);
			} else {
				ret.push(toQueryPair(key, values));
			}
		}
		return ret.join('&');
	},
	isAndroid       : function () {
		var result = navigator.userAgent.match(/Android/i);
		return result == 'Android';
	},
	isIOS           : function () {
		var result = navigator.userAgent.match(/iPhone|iPad|iPod/i);
		return result == 'iPhone' || result == 'iPad' || result == 'iPod';
	},
	isEmail         : function (v) {
		var regex = /^(\w+)([\-+.][\w]+)*@(\w[\-\w]*\.){1,5}([A-Za-z]){2,6}$/;
		return regex.test(v);
	},
	isAlpha         : function (v) {
		var regex = /^[a-zA-Z_]+$/;
		return regex.test(v);
	},
	isAlphaNumber   : function (v) {
		var regex = /^[a-zA-Z0-9_]+$/;
		return regex.test(v);
	},
	isNumber        : function (v) {
		var regex = /^[0-9]+$/;
		return regex.test(v);
	},
	isUrl           : function (v) {
		var regex = /(((^https?)|(^ftp)):\/\/([\-\w]+\.)+\w{2,3}(\/[%\-\w]+(\.\w{2,})?)*(([\w\-\.\?\\\/+@&#;`~=%!]*)(\.\w{2,})?)*\/?)/i;
		return regex.test(v);
	},
	isNickName      : function (v) {
		//  /^[0-9a-zA-Z\u4e00-\u9fa5_·-]*$/;
		var regex = /^[A-Za-z0-9_\-\u4e00-\u9fa5]+$/;
		return regex.test(v);
	},
	isID            : function (v) {
		var regex = /^(\d{18,18}|\d{15,15}|\d{17,17}[x|X])$/;
		return regex.test(v);
	},
	isMobile        : function (v) {
		//var regex = /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/;
		var regex = /^0?(11[0-9]|12[0-9]|13[0-9]|14[0-9]|15[0-9]|16[0-9]|17[0-9]|18[0-9]|19[0-9])[0-9]{8}$/;
		return regex.test(v);
	},
	isPassWord      : function (v) {
		var regex = /^\S{6,20}$/;
		return regex.test(v);
	},
	passWordLevel   : function (pwd) {
		var _level = function (PW) {
			if (PW.length <= 5 || PW === null || pwd === '') {
				return 1;
			}
			var Modes = 0;
			for (var i = 0; i < PW.length; i++) {
				Modes |= function (iN) {
					if (iN >= 48 && iN <= 57) {
						return 1;// 数字
					} else if (iN >= 65 && iN <= 90) {
						return 2;// 大写字母
					} else if (iN >= 97 && iN <= 122) {
						return 4; // 小写
					} else {
						return 8; // 特殊字符
					}
				}(PW.charCodeAt(i));
			}
			return function (num) {
				var modes = 0;
				for (i = 0; i < 4; i++) {
					if (num & 1) {
						modes++;
					}
					num >>>= 1;
				}
				return modes;
			}(Modes);
		}(pwd);
		return _level;
	},
	isChinese       : function (v) {
		var regex = /[u00-uFF]/;
		return regex.test(v);
	},
	chineseLength   : function (v) {
		var size = 0;
		for (var i = 0, len = v.length; i < len; i++) {
			if (this.isChinese(v.charAt(i))) {
				size = size + 2;
			} else {
				size = size + 1;
			}
		}
		return size;
	}
};