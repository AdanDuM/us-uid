var br = br ? br : {};
br.ufsc = br.ufsc ? br.ufsc : {};
br.ufsc.leb = br.ufsc.leb ? br.ufsc.leb : {};
br.ufsc.leb.uid = br.ufsc.leb.uid ? br.ufsc.leb.uid : {};
br.ufsc.leb.uid.scenario = br.ufsc.leb.uid.scenario ? br.ufsc.leb.uid.scenario
		: {};

br.ufsc.leb.uid.scenario.app = br.ufsc.leb.uid.scenario.app ? br.ufsc.leb.uid.scenario.app
		: {};
// إحداثيات: 21°25′21.02″N 39°49′34.58″E

br.ufsc.leb.uid.scenario.Util = function Util() {
}
br.ufsc.leb.uid.scenario.Util.filter = function(figures, type) {
	var extract = [];
	for (var int = 0; int < figures.length; int++) {
		var figure = figures[int];
		if (figure instanceof type)
			extract[extract.length] = figure;
	}
	return extract;
}
br.ufsc.leb.uid.scenario.FunctionalData = function FunctionalData(args) {
	this.model = "";
}
br.ufsc.leb.uid.scenario.app.Canvas = draw2d.Canvas.extend({
	init : function(id) {
		this._super(id, 10000, 10000);
		this.setScrollArea("#" + id);
		this.installEditPolicy(new br.ufsc.leb.uid.scenario.CopyInterceptorPolicy());
	},
	getInteractions : function() {
		var figures = this.getFigures().asArray();
		var type = br.ufsc.leb.uid.scenario.Interacao;
		return br.ufsc.leb.uid.scenario.Util.filter(figures, type);
	}
});

br.ufsc.leb.uid.scenario.SystemOutput = draw2d.shape.basic.Text.extend({
	NAME : 'br.ufsc.leb.uid.scenario.SystemOutput',
	init : function(attr) {
		this._super(attr);
		this.installEditor(new draw2d.ui.LabelInplaceEditor());
		this.setUserData(new br.ufsc.leb.uid.scenario.FunctionalData());
		this.setColor(new draw2d.util.Color("#ffffff"));
		this.setFontColor(new draw2d.util.Color("#000000"));
	}
});

br.ufsc.leb.uid.scenario.UserInput = draw2d.shape.basic.Label.extend({
	NAME : 'br.ufsc.leb.uid.scenario.UserInput',
	init : function() {
		this._super();
		this.installEditor(new draw2d.ui.LabelInplaceEditor());
		this.setUserData(new br.ufsc.leb.uid.scenario.FunctionalData());
		this.setColor(new draw2d.util.Color("#000000"));
	}
});


br.ufsc.leb.uid.scenario.UserInputTransaction = draw2d.shape.basic.Text.extend({
	NAME : 'br.ufsc.leb.uid.scenario.UserInputTransaction',
	init : function(transaction) {
		this._super();
		this.transaction=transaction;
		this.installEditor(new draw2d.ui.LabelInplaceEditor());
		this.setColor(new draw2d.util.Color("#ffffff"));
		this.setFontColor(new draw2d.util.Color("#000000"));
	},
	setText :function (value){
		this.transaction.getUserData().text=value;
		return this._super(value);
	},
	getText:function (){
		return this.transaction.getUserData().text;
	}
});


br.ufsc.leb.uid.scenario.Interacao = draw2d.shape.composite.Jailhouse.extend({
	NAME : "br.ufsc.leb.uid.scenario.Interacao",
	init : function() {
		this._super(170, 130);
		this.setRadius(500);
		this.setUserData(new br.ufsc.leb.uid.scenario.FunctionalData());
		this.setColor(new draw2d.util.Color("#000000"));
		this.setBackgroundColor(new draw2d.util.Color("#ffffff"));
	},
	getSystemOutputs : function() {
		var type = br.ufsc.leb.uid.scenario.SystemOutput;
		var figures = this.getAssignedFigures().asArray();
		return br.ufsc.leb.uid.scenario.Util.filter(figures, type);
	},
	getUserInputs : function() {
		var type = br.ufsc.leb.uid.scenario.UserInput;
		var figures = this.getAssignedFigures().asArray();
		return br.ufsc.leb.uid.scenario.Util.filter(figures, type);
	},
	assignFigure : function(component) {
		if (!(component instanceof br.ufsc.leb.uid.scenario.Interacao))
			this._super(component);
	},
	addPrint : function(inputOrOutput) {
		inputOrOutput.setText('Text');
		inputOrOutput.setDimension(43, 10);
		this.getCanvas().addFigure(inputOrOutput, this.getX() + 35,
				this.getY() + 35);
		this.assignFigure(inputOrOutput);
	},
	newSystemOutput : function() {
		this.addPrint(new br.ufsc.leb.uid.scenario.SystemOutput());
	},
	newUserInput : function() {
		this.addPrint(new br.ufsc.leb.uid.scenario.UserInput());
	},
	getTransaction : function() {
		if (this.getInputPorts().isEmpty())
			return null;
		return this.getInputPort(0).getConnections().get(0);
	},
	newInteractionState : function() {
		var canvas = this.getCanvas();
		var source = new br.ufsc.leb.uid.scenario.Interacao();
		canvas.addFigure(source, this.getX()-this.getWidth()-40, this.getY());
		var ligacao = new br.ufsc.leb.uid.scenario.Transaction();
		ligacao.installConnection(source, this);
		canvas.addFigure(ligacao);
	},

	newInteractionStateProgressive : function() {
		var canvas = this.getCanvas();
		var source = new br.ufsc.leb.uid.scenario.Interacao();
		canvas.addFigure(source, this.getX()+this.getWidth()+40, this.getY());
		var ligacao = new br.ufsc.leb.uid.scenario.Transaction();
		ligacao.installConnection(this, source);
		canvas.addFigure(ligacao);
	}
});

br.ufsc.leb.uid.scenario.Transaction = draw2d.Connection
		.extend({
			NAME : 'br.ufsc.leb.uid.scenario.Transaction',
			init : function() {
				this._super();
				this.setRouter(new draw2d.layout.connection.DirectRouter());
				this
						.setTargetDecorator(new draw2d.decoration.connection.ArrowDecorator());
				this.setColor(new draw2d.util.Color("#000000"));
				this.setUserData({text:''});
				this.valueAction = new br.ufsc.leb.uid.scenario.UserInputTransaction(this);
				this.add(this.valueAction, new draw2d.layout.locator.ParallelMidpointLocator());
				this.valueAction.setText("Action");
			},
			setPersistentAttributes : function(memento){
				 	this._super(memento);
				 	this.valueAction.setText(memento.userData.text);
				 	
			 },
			installConnection : function(source, target) {
				target.createPort("input",
						new br.ufsc.leb.uid.scenario.TargetLocator());
				if(source.getOutputPort(0)==null){
					source.createPort("output",
							new br.ufsc.leb.uid.scenario.ResourceLocator());				
				}
				this.setSource(source.getOutputPort(0));
				this.setTarget(target.getInputPort(0));
				
			},
			getFrom : function() {
				return this.getSource().getParent();
			}
		});

br.ufsc.leb.uid.scenario.TargetLocator = draw2d.layout.locator.InputPortLocator
		.extend({
			NAME : 'br.ufsc.leb.uid.scenario.TargetLocator',
			init : function() {
				this._super();
			},
			relocate : function(index, figure) {
				var parent = figure.getParent();
				this.applyConsiderRotation(figure, parent.getWidth() / 45, parent.getHeight()/2);
			}
		});

br.ufsc.leb.uid.scenario.ResourceLocator = draw2d.layout.locator.OutputPortLocator
		.extend({
			NAME : 'br.ufsc.leb.uid.scenario.ResourceLocator',
			init : function() {
				this._super();
			},
			relocate : function(index, figure) {
				var parent = figure.getParent();
				this.applyConsiderRotation(figure, parent.getWidth(), parent.getHeight()/2);
			}
		});

br.ufsc.leb.uid.scenario.CopyInterceptorPolicy = draw2d.policy.canvas.ExtendedKeyboardPolicy
.extend({
	NAME : "br.ufsc.leb.uid.scenario.CopyInterceptorPolicy",

	init : function() {
		this._super();

	},

	onKeyDown : function(canvas, keyCode, shiftKey, ctrlKey) {
		if(canvas.getSelection()!==null){
			var distance = 10;
			switch (keyCode) {
				case 37: {// <-
					canvas.getSelection().each(function(i, figure) {
						var position=figure.getPosition();
						var x =(position.x-distance);
						x=x-(x%distance);
						figure.setPosition(x, position.y);
					});
					break;
				}
				case 38: {// ^
					canvas.getSelection().each(function(i, figure) {
						var position=figure.getPosition();
						var y =(position.y-distance);
						y=y-(y%distance);
						figure.setPosition(position.x, y);
					});
					break;
				}
				case 39: {// ->
					canvas.getSelection().each(function(i, figure) {
						var position=figure.getPosition();
						var x =(position.x+distance);
						x=x-(x%distance);
						figure.setPosition(x, position.y);
					});					
					break;
				}
				case 40: {// v
					canvas.getSelection().each(function(i, figure) {
						var position=figure.getPosition();
						var y =(position.y+distance);
						y=y-(y%distance);
						figure.setPosition(position.x, y);
					});
					break;
				}
			}
		}
		
		
		
		if (canvas.getCurrentSelection() !== null && ctrlKey === true) {
			switch (keyCode) {
			case 67: {// C
				copy = new draw2d.util.ArrayList();
				canvas.getSelection().each(function(i, figure) {
					copy.add(figure);
				});
				break;
			}
			case 86: {// V
				if (canvas.getCurrentSelection() instanceof br.ufsc.leb.uid.scenario.Interacao) {
					copy
							.each(function(i, figure) {
								if (figure instanceof br.ufsc.leb.uid.scenario.UserInput
										|| figure instanceof br.ufsc.leb.uid.scenario.SystemOutput) {
									this.interaction = canvas
											.getCurrentSelection();
									this.destination = this.interaction
											.getPosition();
									this.originalInteraction = figure
											.getComposite()
											.getPosition();
									this.original = figure
											.getPosition();
									this.x = (this.original.x - this.originalInteraction.x)
											+ this.destination.x;
									this.y = (this.original.y - this.originalInteraction.y)
											+ this.destination.y;
									this.clone = figure.clone();
									canvas.addFigure(this.clone,
											this.x, this.y);
									this.interaction
											.assignFigure(this.clone);
								}
							});
				}
				break;
			}
			}
		}
		this._super(canvas, keyCode, shiftKey, ctrlKey);
	}
});

