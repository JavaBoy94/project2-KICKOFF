const deleteBtn = document.querySelector(".deleteBtn");
const deleteForm = document.querySelector("#deleteForm");

deleteBtn.addEventListener('click', function(event){

    event.preventDefault();

    if(confirm("삭제하시겠습니까?") == true){
        deleteForm.submit();
        alert("게시글이 삭제되었습니다.")
    }else{
        return;
    }
})