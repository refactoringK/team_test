/**
 * 
 */

let $checkMsg = $("#check-id-msg");
let $checkPwMsg = $("#check-pw-msg");
let $idInput = $("#id");
let $pwInput = $("#password");


$idInput.on('blur', function() {
	if ($(this).val() == '') {
		//console.log('id 입력 안함!')
		$checkMsg.text('아이디를 입력하세요!')
	}
});

//중복 검사를 위한 ajax
let checkId = function() {
	$.ajax({
		url: '/member/checkIdOk.me',
		type: 'get',
		data: { memberId: $idInput.val() },
		success: function(result) {
			//console.log(result);
			$checkMsg.text(result);
		},
		error: function(xhr, status, error) {
			console.log(error);
		}
	})
};

$idInput.on('change', checkId);


// 영어 대소문자를 구분하지 않음
// 영어, 숫자, 특수문자로 이루어진 비밀번호 8글자 이상
const regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])[a-zA-Z\d!@#$%^&*()_+]{8,}$/;

$pwInput.on('blur', function() {
	if (regex.test($pwInput.val())) {
		//console.log("비밀번호가 유효합니다.");
		$(this).text("사용 가능한 비밀번호입니다.")
	} else {
		//console.log("올바른 비밀번호를 입력해주세요.");
		$(this).html("사용 불가능한 비밀번호입니다.<br>영어, 숫자, 특수문자를 포함하여 8글자 이상 작성하세요")
	}
});

$('form').on('submit', function(e) {
	e.preventDefault(); // 기본 이벤트를 막아주는 명령어(지금은 submit 이벤트)
	
	//console.log($('#agree').prop('checked'));
	
	if($('#agree').prop('checked')){
		this.submit(); // 서브밋 이벤트를 발생시키는 메소드(폼 요소에 사용해야 함)
	}else{
		alert('약관에 동의해주세요!')
	}
});


