define(['text!../tpl/tpl.tpl', '../data/Data', 'lazyload'], function (tpl, data) {
	return Backbone.View.extend({
		events: {

		},
		initialize: function () {
			this.$el.html(tpl);
		}
	});
});
