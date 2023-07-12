var app = angular.module('chatbox', []);

app.controller('ChatController', function () {
	this.check = true;
	this.chats = [
		 {
			person: false,
			message: "Welcome to Napier CHAT BOT."
		}
	];

	this.insert = function (value) {
		if (this.check) {
			this.chats.push({
				person: this.check,
				message: value
			});
			this.check = false;
		} else {
			this.chats.push({
				person: this.check,
				message: value
			});
			this.check = true;
		}
		inputValue = null;
		alert
	};
	
});