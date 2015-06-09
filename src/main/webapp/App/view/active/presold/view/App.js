define(['text!../tpl/tpl.tpl', '../data/Data', 'lazyload'], function (tpl, data) {
	return Backbone.View.extend({
		events    : {
			"click .icon"         : "open",
			"click .button.delete": "destroy"
		},
		initialize: function () {
			this.$el.html(tpl);
		}
	});
});
