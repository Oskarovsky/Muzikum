function showAvatarForm() {
  document.getElementById('avatarForm').style.display = "block";
}

function passwordCompare() {
  let password = document.getElementById("password")
    , confirm_password = document.getElementById("confirm_password");

  function validatePassword(){
    if (password.value !== confirm_password.value) {
      confirm_password.setCustomValidity("Passwords Don't Match");
    } else {
      confirm_password.setCustomValidity('');
    }
  }

  password.onchange = validatePassword;
  confirm_password.onkeyup = validatePassword;
}

let enableRegisterButton = function () {
  if (document.getElementById('password').value === document.getElementById('confirm_password').value) {
    document.getElementById('message').style.display = "none"
    document.getElementById('submit_register').disabled = false;
  } else {
    document.getElementById('message').style.display = "inline"
    document.getElementById('message').style.color = 'red';
    document.getElementById('message').innerHTML = 'Niepoprawne hasło';
    document.getElementById('submit_register').disabled = true;
  }
};

let comparePassword = function () {
  if (document.getElementById('password').value === document.getElementById('confirm_password').value
  && document.getElementById('password').value !== '') {
    document.getElementById('message').style.display = "none"
    document.getElementById('submit_register').disabled = false;
  } else {
    document.getElementById('message').style.display = "inline"
    document.getElementById('message').style.color = 'red';
    document.getElementById('message').innerHTML = 'Niepoprawne hasło';
    document.getElementById('submit_register').disabled = true;
  }
}

let enableLoginButton = function () {
  if (document.getElementById("password_form").value !== ''
    && document.getElementById("username_form").value !== '') {
    document.getElementById('submit_login').disabled = false;
  } else {
    document.getElementById('submit_login').disabled = true;
  }
}

$(document).ready(function test() {
  var els = document.getElementsByClassName('votecount');
  for (var i = 0; i < els.length; i++) {
    var cell = els[i];
    if (cell.textContent < 0) {
      cell.classList.remove('green')
    } else if (cell.textContent > 0) {
      cell.classList.add('green');
    } else {
      cell.classList.add('default');
    }
  }
});

// $(document).ready(function(){
//   $(".votecount").filter(function() {
//     var div = $('div:contains(On Call)', this);
//     if (div.length === 0) return false;
//
//     var points = div.text();
//
//     return points <= 0;
//
//   }).css("color","red");
// });


$(document).ready(function () {
  $imgSrc = $('#imgProfile').attr('src');
  function readURL(input) {

    if (input.files && input.files[0]) {
      var reader = new FileReader();

      reader.onload = function (e) {
        $('#imgProfile').attr('src', e.target.result);
      };

      reader.readAsDataURL(input.files[0]);
    }
  }
  $('#btnChangePicture').on('click', function () {
    // document.getElementById('profilePicture').click();
    if (!$('#btnChangePicture').hasClass('changing')) {
      $('#profilePicture').click();
    }
    else {
      // change
    }
  });
  $('#profilePicture').on('change', function () {
    readURL(this);
    $('#btnChangePicture').addClass('changing');
    $('#btnChangePicture').attr('value', 'Confirm');
    $('#btnDiscard').removeClass('d-none');
    // $('#imgProfile').attr('src', '');
  });
  $('#btnDiscard').on('click', function () {
    // if ($('#btnDiscard').hasClass('d-none')) {
    $('#btnChangePicture').removeClass('changing');
    $('#btnChangePicture').attr('value', 'Change');
    $('#btnDiscard').addClass('d-none');
    $('#imgProfile').attr('src', $imgSrc);
    $('#profilePicture').val('');
    // }
  });
});


function copyToClipboard(element) {
  var $temp = $("<input>");
  $("body").append($temp);
  $temp.val($(element).text()).select();
  document.execCommand("copy");
  $temp.remove();
}

$(document).ready(function() {
  $("#formButton").click(function() {
    $("#form1").toggle();
    $("#button1").toggle();
    var $this = $(this);
    $this.toggleClass('formButton');
    if($this.hasClass('formButton')){
      $this.text('Dodaj nowy utwór');
    } else {
      $this.text('Anuluj');
    }
  });
});


