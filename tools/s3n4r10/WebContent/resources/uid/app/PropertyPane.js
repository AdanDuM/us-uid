example.PropertyPane = Class.extend({

	init : function(elementId, view) {
		this.html = $("#" + elementId);
		this.view = view;

		this.templateView = null;

		view.on("select", $.proxy(this.onSelectionChanged, this));
	},

	/**
	 * @method Called if the selection in the canvas has been changed. You must
	 *         register this on the canvas to receive this event.
	 * 
	 * @param {draw2d.Figure}
	 *            figure
	 */
	onSelectionChanged : function(emitter, figure) {
		if (this.templateView != null) {
			this.templateView.close();
			this.templateView = null;
		}

		this.html.html("");
		this.shows(figure);

	},

	shows : function(figure) {
		if (figure instanceof draw2d.shape.icon.Fork) {
			this.templateView = new FunctionalDataAnnotations({
				model : figure
			});
		} else if (figure instanceof br.ufsc.leb.uid.scenario.Interacao
				|| figure instanceof br.ufsc.leb.uid.scenario.Transaction) {
			this.templateView = new FunctionalDataInteractionView({
				model : figure
			});
		} else if (figure instanceof draw2d.shape.node.Node) {
			this.templateView = new FunctionalDataInputOutputView({
				model : figure
			});
		}
		this.html.append(this.templateView.render().$el);

	}
});