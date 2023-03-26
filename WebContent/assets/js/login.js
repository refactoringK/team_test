/**
 * 
 */

// window.location.search 에 쿼리스트링이 저장되어 있다.
const queryString = window.location.search;

//URLSearchParams 객체는 쿼리스트링을 해석해준다.
const urlParams = new URLSearchParams(queryString);

const result = urlParams.get('result');

if(result == 'fail'){
	alert('잘못된 정보입니다.');
}