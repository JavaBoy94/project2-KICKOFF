const acUpdateForm = document.querySelector('#acUpdateForm');
const acTitle = document.querySelector('#acTitle');
let acContent = document.querySelector('.acContent');
let acIncome = document.querySelector('.acIncome');
let acExpend = document.querySelector('.acExpend');
const insertBtn = document.querySelector('.insertBtn');
/* 목록 추가 관련 */
const incomeExpend = document.querySelector('.income-expend');
const incomeExpendTb = document.querySelector('.income-expend table');
const plusBtn = document.querySelector('.plusBtn');

let acTotalIncome = document.querySelector('.acTotalIncome');
let acTotalExpend = document.querySelector('.acTotalExpend');
let acTotalPay = document.querySelector('.acTotalPay');
let acSurplus = document.querySelector('.acSurplus');


/* 목록 추가 */
/* let acContentAll;
let acExpendAll; */
plusBtn.addEventListener('click', function (e) {
    /* acIncome = document.querySelectorAll('.acIncome'); */
    /* acContentAll = document.querySelectorAll('.acContent');
    acExpendAll = document.querySelectorAll('.acExpend'); */

    let trTag = document.createElement("tr");

    trTag.innerHTML =          
    `<td width="60%"><input type="text" th:field="*{acContent}" class="acContent" placeholder="내역을 입력하세요"></td>
    <td width="15%"><input type="text" th:field="*{acIncome}" class="acIncome" value="0"></td>
    <td width="15%"><input type="text" th:field="*{acExpend}" class="acExpend" value="0"></td>
    <td width="10%">
        <input type="button" value="-" class="minusBtn">
    </td>`;

    incomeExpendTb.appendChild(trTag);

    plusFn();
})


/* 해당목록 제거 */
let minus;
function plusFn(){
    minus = document.querySelectorAll('.minusBtn');
    minus.forEach((el, idx) =>{
        el.addEventListener('click', function(e){
            e.target.parentElement.parentElement.style.display = "none";
        })
    })
}

/* 수입 실시간 변화 */
function total(){
    /* 이월금 */
    acSurplus = document.querySelector('.acSurplus').value;

    /* 총수입 */
    acIncome = document.querySelector('.acIncome').value;
    document.querySelector('.acTotalIncome').value = acIncome;

    /* 총 지출 */
    acExpend = document.querySelector('.acExpend').value;
    document.querySelector('.acTotalExpend').value = acExpend;

    /* 합산 */
    acTotalIncome = document.querySelector('.acTotalIncome').value;
    acTotalExpend = document.querySelector('.acTotalExpend').value;
    document.querySelector('.acTotalPay').value = parseInt(acSurplus)+parseInt(acTotalIncome)-parseInt(acTotalExpend);
};


/* 게시글 작성 경고창 */
/* insertBtn.addEventListener('click', function (event) {

    event.preventDefault();

    if (acTitle.value == null || acTitle.value.length < 1) {
        alert("제목을 입력해주세요.");
        acTitle.focus();
        return false;
    }

    if (acContent.value == null || acContent.value.length < 1) {
        alert("내용을 입력해주세요.");
        acTitle.focus();
        return false;
    }

    if (acIncome.value == null || acIncome.value.length < 1) {
        alert("수입을 입력해주세요.");
        acTitle.focus();
        return false;
    }

    if (acExpend.value == null || acExpend.value.length < 1) {
        alert("지출을 입력해주세요.");
        acTitle.focus();
        return false;
    }

    acUpdateForm.submit();
}); */

/* 계산 항목 */
/* 수입 */
/* let sum = 0; */
/* acIncomeAll.forEach((el, idx)=>{
    sum += parseInt(el.value);
    console.log(el.value);
}) */


/* 계산 관련 */
/* document.querySelector('.incomeSum').value = sum; */

/* 지출 */


