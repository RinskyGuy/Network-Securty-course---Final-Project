var myInput = document.getElementById("psw");
var myInput2 = document.getElementById("psw-repeat");
var letter = document.getElementById("letter");
var capital = document.getElementById("capital");
var number = document.getElementById("number");
var length = document.getElementById("length");
var rpt_ps = document.getElementById("rpt_ps");
var tst = document.getElementById("tst");

function display_constraints(psw_type) {
    if (psw_type == 1) {
        document.getElementById("psw_constraints").style.display = "block";
    } else {
        document.getElementById("rpt_psw_constraints").style.display = "block";
    }
}

function hide_constraints(psw_type) {
    if (psw_type == 1) {
        document.getElementById("psw_constraints").style.display = "none";
    } else {
        document.getElementById("rpt_psw_constraints").style.display = "none";
    }
}

function validate_psw(psw_type) {
    tst.value = myInput2 + myInput;
    if (psw_type == 1) {
        var lowerCaseLetters = /[a-z]/g;
        if (myInput.value.match(lowerCaseLetters)) {
            letter.classList.remove("invalid");
            letter.classList.add("valid");
        } else {
            letter.classList.remove("valid");
            letter.classList.add("invalid");
        }

        // Validate capital letters
        var upperCaseLetters = /[A-Z]/g;
        if (myInput.value.match(upperCaseLetters)) {
            capital.classList.remove("invalid");
            capital.classList.add("valid");
        } else {
            capital.classList.remove("valid");
            capital.classList.add("invalid");
        }

        // Validate numbers
        var numbers = /[0-9]/g;
        if (myInput.value.match(numbers)) {
            number.classList.remove("invalid");
            number.classList.add("valid");
        } else {
            number.classList.remove("valid");
            number.classList.add("invalid");
        }

        // Validate length
        if (myInput.value.length >= 8) {
            length.classList.remove("invalid");
            length.classList.add("valid");
        } else {
            length.classList.remove("valid");
            length.classList.add("invalid");
        }
    } else {
        if (myInput.value == myInput2.value) {
            rpt_ps.classList.remove("invalid");
            rpt_ps.classList.add("valid");
        } else {
            rpt_ps.classList.remove("valid");
            rpt_ps.classList.add("invalid");
        }
    }


}