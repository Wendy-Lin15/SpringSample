//設定開關變數，設 0 為關閉狀態 / 設 1 為開啟狀態
let burger_toggle = 0;

//函數：要執行時，需在 HTML 中要點及的元素上，設 onclick 事件來觸發函式執行
function meun_touch() {
  if (burger_toggle == 0) {
    //console.log("測試");
    //用 id 的方式抓元素 / 在元素上增加 class 的屬性名稱
    document.getElementById("navbar").classList.add("rwd-open-navbar");
    //navbar 已經改為開啟狀態，將 開關變數 設為 1 即開啟狀態
    burger_toggle = 1;
  } else {
    //用 id 的方式抓元素 / 在元素上移除 class 的屬性名稱
    document.getElementById("navbar").classList.remove("rwd-open-navbar");
    //navbar 已經改為關閉狀態，將 開關變數 設為 0 即關閉狀態
    burger_toggle = 0;
  }
}
