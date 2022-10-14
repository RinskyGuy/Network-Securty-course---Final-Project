window.onload = function generateReport() {
	let userBody = JSON.stringify({
        "role": "PLAYER",
        "username": sessionUsername,
        "avatar": sessionAvatar
    });
		url = `https://localhost:8443/aaa`;
    http.open("GET", url, true);
    http.setRequestHeader('Accept', 'application/json');
    http.setRequestHeader('Content-Type', 'application/json');
    http.send(userBody);
    http.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let response = this.response;
			let data = JSON.parse(response);
			console.log(data);
        };
	}
}