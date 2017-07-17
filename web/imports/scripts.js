/**
 * Created by m1sho on 17.07.2017.
 */

/**
 *
 * @param quiz_id
 * @param friend_id sends quiz challenge to friend_id
 */
function sendChallenge(quiz_id, friend_id) {
	xhr2 = new XMLHttpRequest();

	var url = "/SendChallenge?quiz_id=" + quiz_id + "&friend_id=" + friend_id;

	xhr2.onreadystatechange = sendChallengHandler;
	xhr2.open("POST", url, true);
	xhr2.send(null);
}

function sendChallengHandler() {
	if (xhr2.readyState === 4) {
		if (xhr2.status === 200) {
			console.log("successful");
			alert("Challenge  sent")
		} else {
			alert("ERROR");
		}
	}
}

/**
 * @param sends friend request
 */
function sendRequest(id) {
	try {
		xhr = new XMLHttpRequest();
	} catch (e) {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (xhr === null) {
		alert("Ajax not supported by your browser!");
		return;
	}
	var url = "/FriendRequestResponse?status=2&friend_id=" + id;

	xhr.onreadystatechange = sendRequestHandler;
	xhr.open("POST", url, true);
	xhr.send(null);
}


function sendRequestHandler() {
	if (xhr.readyState === 4) {
		if (xhr.status === 200) {
			console.log("successful");
			alert("Friend Request sent")
		} else {
			alert("ERROR");
		}
	}
}
