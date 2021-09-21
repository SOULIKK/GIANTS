function reportLink(rcpNo) {
    const link = 'http://dart.fss.or.kr/dsaf001/main.do?rcpNo='+rcpNo;
    window.open(link, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=50,left=50,width=1024,height=768");
}


const today = new Date().toISOString().slice(0, 10);

document.getElementById("searchEnd").value = today;
document.getElementById("searchEnd").value = today;

function searchValidation() {
    const searchStart = document.getElementById("searchStart").value;
    const searchEnd = document.getElementById("searchEnd").value;

    if (searchStart == "" || searchEnd == "") {
        alert("검색일자를 입력해주세요");
        return false;
    }
}
