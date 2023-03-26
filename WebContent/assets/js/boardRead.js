/**
 * 
 */

let $listBtn = $('.list-btn');
let $modifyBtn = $('.modify-btn');
let $deleteBtn = $('.delete-btn');

// 자바스크립트에서 data 속성을 소문자로 인식함**
let boardNumber = $('.list-btn').data("boardnumber");
console.log(boardNumber);

$listBtn.on('click', ()=>{
	window.location.href = '/board/boardListOk.bo';
});

$modifyBtn.on('click', ()=>{
	window.location.href = `/board/boardUpdate.bo?boardNumber=${boardNumber}`;
});

$deleteBtn.on('click', ()=>{
	window.location.href = `/board/boardDeleteOk.bo?boardNumber=${boardNumber}`;
});

console.log("aaa");

replyAjax();

function replyAjax(){
	$.ajax({
		url : "/reply/replyListOk.re",
		type : "get",
		data : {boardNumber : boardNumber}, //이전에 만들어놓은 변수
		dataType : "json",
		success : showReply,
		error : function(a,b,c){
			console.log(c);
		}
	});
}


// 모듈화
function showReply(replies){
	let text = "";
	
	replies.forEach(reply => {
		//console.log(reply.memberId);
		text += `
		<ul id="comment-list">
				<li>
					<div class="comment-info">
						<span class="writer">${reply.memberId}</span> <span
							class="date">${reply.replyDate}</span>
					</div>
					<div class="comment-content-wrap">
						<div class="comment-content">
							<p>${reply.replyContent}</p>
						</div>`
		
		if(memberNumber == reply.memberNumber){				
			text +=`
						<div class="comment-btn-group">
							<button type=button class="comment-modify-ready">수정</button>
							<button type=button class="comment-delete" data-number=${reply.replyNumber}>삭제</button>
						</div>
						<div class="comment-btn-group none">
							<button type=button class="comment-modify" data-number=${reply.replyNumber}>수정 완료</button>
						</div>`
		}
		
		text +=				`
					</div>
				</li>
		</ul>
		`;
	
	});
	$('#comment-list').html(text);
	
	
	
}



//댓글 작성
$('.submit-btn').on('click', send);

function send() {
	console.log('send!!!')
	console.log($('#content').val());
	$.ajax({
		url : "/reply/replyWriteOk.re",
		type : "post",
		data : {
			boardNumber : boardNumber,
			memberNumber : memberNumber,
			replyContent : $('#content').val()
		},
		success : function() {
			replyAjax();
			$('#content').val('');
		}
	});
}

// 댓글 삭제
$('.comment-delete').on("click", function(){
	let replyNumber = $(this).data('number');
	console.log(replyNumber);
	$.ajax({
		url : "/reply/replyDeleteOk.re",
		type : "get",
		data : {replyNumber : replyNumber},
		success: function() {
			replyAjax();
		}
	});
});

// 이벤트 위임
// 이벤트를 등록할 때 처음부터 존재하지 않는 요소는 이벤트 등록(바인딩)이 되지않기 때문에
// 이벤트를 거는 시점을 잘 생각해야한다.
// 결국 동적으로 생기는 요소는 생기는 시점에서 이벤트를 바인딩 하는데 이렇게 되면 코드가 복잡해진다.
// 이럴 때 이벤트 위임 이라는 것을 이용한다.

// 이벤트 위임이란? 
// 부모 요소에 이벤트를 바인딩하면 자식 요소의 이벤트를 처리할 수 있다.(버블링)
// 하나의 이벤트로 여러 요소의 이벤트를 처리할 수 있어 효율적이며 성능을 향상시킬 수 있다.
// 그리고 동적 요소의 이벤트 처리를 간편하게 할 수 있다.

// jquery를 사용하면 다음과 같이 처리 가능하다. (on()만 가능 click()같은 메소드는 불가능)
// 부모요소인 .comment-list에 이벤트를 걸고 위임한다.(이미 존재하는 요소로 위임해야함)
$('.comment-list').on("click", '.comment-delete', function(){
	let replyNumber = $(this).data('number');
	
	$.ajax({
		url : "/reply/replyDeleteOk.re",
		type : "get",
		data : {replyNumber : replyNumber},
		success: function() {
			console.log('성공!!!');
			replyAjax();
		}
	});
});


// 수정 버튼
$('.comment-list').on('click', '.comment-modify-ready', function(){
	// closest()는 조상 요소 중에서 찾는다.
	// find()는 자손 요소 중에서 찾는다.
	let $parent = $(this).closest('#comment-list');
	console.log($parent);
	
	let $children = $parent.find('.comment-btn-group');
	console.log($children);
	
	$children.eq(0).hide();
	$children.eq(1).show();
	
	// 현재 수정 버튼 요소에서 .... 부모의 이전 형제의 자식 == 댓글 내용을 가진 p태그
	let $content = $(this).parent().prev().children();
	console.log($content);
	
	// 기존 요소를 교체한다.
	$content.replaceWith(`<textarea class='modify-content'>${$content.text()}</textarea>`);
});

// 수정 완료 버튼
$('.comment-list').on('click', '.comment-modify', function(){
	console.log('modify~~');
	let replyNumber = $(this).data('number');
	
	$.ajax({
		url : '/reply/replyUpdateOk.re',
		type : 'get',
		data : {
			replyNumber : replyNumber,
			replyContent : $('.modify-content').val()
		},
		success : function(){
			console.log('성공!@#@!#!@#');
			replyAjax();
		}
	});
});






