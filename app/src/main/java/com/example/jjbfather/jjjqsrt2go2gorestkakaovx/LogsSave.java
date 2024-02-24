package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

public class LogsSave {
    public static String str_paymentKind = "";
    public static String[][] logContentsArr = {
            {"버튼이 위치한 페이지 이름", "버튼이름", "버튼 실행내용 (한글)", "버튼 실행내용(ENG)"}, // 0
            {"버튼이 위치한 페이지 이름", "버튼이름", "버튼 실행내용 (한글)", "버튼 실행내용(ENG)"}, // 1
            {"로그인", "COMPACT DB DEL", "컴팩트 DB 삭제", "Deleted Compact DB"},           // 2
            {"로그인", "", "SHUT DOWN : 프로그램 종료", "Shut down program"},                // 3
            {"로그인","","CANCEL : 취소","Canceled shutdown popup"},
            {"로그인","","RESTART : 프로그램 재 시작","Restarted program"},
            {"로그인","","좌측상단 나가기 버튼 : 프로그램 종료, 재 시작 화면 이동","Exit/Restart Program Popup opened"},
            {"로그인","","숫자패드 숫자 : 숫자 입력","Numpad (Login):(number) input"},
            {"로그인","","숫자패드 백스페이스 : 입력 값 삭제","Numpad (Login): backspace"},
            {"로그인","","숫자패드 x 버튼 : ??","Numpad (Login): delete"},
            {"로그인","","숫자패드 DONE 버튼 : 로그인 시 Enter 기능","Numbad (Login): Done"},
            {"로그인","","i 버튼 : 프로그램 정보화면 이동","i: Moved to program information screen"},
            {"로그인","","->프로그램 정보화면 X : 화면 닫기","-> Program information screen X: Closed information screen"},
            {"로그인_COMMAND","","COMPACT DB DEL. : 컴팩트 DB 삭제 기능","Deleted Compact DB"},
            {"로그인","","DB COMPACT : DB컴팩트 기능 이동","DB COMPACT: Moved to DB Compact Function"},
            {"로그인","","COMMAND : 컴맨드 화면 이동","COMMAND: Moved to command screen"},
            {"로그인","","ADMIN LOGIN : 관리자 로그인 이동","ADMIN LOGIN"},
            {"로그인","","CLOCK IN / OUT : 출퇴근 기능 이동","CLOCK IN/OUT: Moved to clock/in out screen"},
            {"로그인","","END OF DAY : EOD 기능 이동","END OF DAY: Moved to EOD screen"},
            {"로그인_COMMAND","","","DATA: Download/Upload"},
            {"로그인_COMMAND","","","DATA: Bakcup Database"},
            {"로그인_COMMAND","","","DATA: Restore Database"},
            {"로그인_COMMAND","","","SETTING: Setting System"},
            {"로그인_COMMAND","","","SETTING: Cloud Back Office"},
            {"로그인_COMMAND","","","POS PROCESS: Cash In/Out"},
            {"로그인_COMMAND","","","POS PROCESS: Cash Drawer"},
            {"로그인_COMMAND","","","POS PROCESS: Gift Card Balance Check"},
            {"로그인_COMMAND","","","SALES HISTORY: POS Sales History"},
            {"로그인_COMMAND","","","SALES HISTORY: Online Sales History"},
            {"로그인_COMMAND","","","SALES HISTORY: End of Day"},
            {"로그인_COMMAND","","","SALES HISTORY: Batch summary"},
            {"로그인_COMMAND","","SALES DATA UPLOAD : 세일기록을 클라우드로 업로드","SALES DATA UPLOAD"},
            {"로그인_COMMAND","","확인 (V) : 체크된 데이터를 클라우드로부터 다운로드","SELECTED DATA DOWNLOAD"},
            {"로그인_COMMAND","","닫기 (X) : 화면 닫기","CLOSE"},
            {"CLOCK_INOUT","","Clock In : 출근","Clock in"},
            {"CLOCK_INOUT","","Clock Out : 퇴근","Clock Out"},
            {"CLOCK_INOUT","","Break In : 휴게시간 시작","Break In"},
            {"CLOCK_INOUT","","Break Out : 휴게시간 종료","Break Out"},
            {"CLOCK_INOUT","","돋보기 : 찾기","Clock In_Out: Search"},
            {"CLOCK_INOUT","","TODAY&OUT : 금일 출퇴근 현황 찾기","Search by Today (Clock In_Out)"},
            {"CLOCK_INOUT","","새로 고침 : 새로 고침","Refresh (Clock In_Out)"},
            {"END_OF_DAY","","돋보기 : 찾기","End of day: Search"},
            {"END_OF_DAY","","PRINT : EOD 현황 출력","EOD Report Print"},
            {"END_OF_DAY","","END OF DAY : EOD 실행","Process EOD"},
            {"CASH_INOUT","","INITIALIZE : 금액별 화폐 초기화","Initialize Bill Count (Cash In_Out)"},
            {"CASH_INOUT","","ADD : 출납 현황 추가","Added Cash In_Out entry"},
            {"CASH_INOUT","","DELETE : 현재 출납 현황 삭제","Deleted Cash In_Out entry"},
            {"CASH_INOUT","","DETAIL VIEW : 현재 출납 현황 자세히 보기","Detail View Cash In_Out"},
            {"CASH_INOUT","","CASH DRAWER : 돈통 열기","Opened Cash Drawer"},
            {"CASH_INOUT","","GO TO SALES : 세일화면 이동","Go To Sales"},
            {"CASH_INOUT","","CASH OUT : 캐쉬 아웃","Processed Cash Out"},
            {"세일_메뉴박스_모디파이어1","","VIEW ALL : 각 옵션 별 모디파이어 확장","View All Modifier"},
            {"세일_메뉴박스_모디파이어1","","HIDE ALL : 각 옵션 별 모디파이어 숨김","Hide All Modifier"},
            {"세일_메뉴박스_모디파이어1","","모디파이어 박스 : 모디파이어 수량 및 메모 입력 보임","Modifier box: Modifier count and memo listed"},
            {"세일_메뉴박스_모디파이어1","","->  + : 선택한 모디파이어 수량 증가","Incremented Selected Modifier"},
            {"세일_메뉴박스_모디파이어1","","- : 선택한 모디파이어 수량 감소","Decremented Selected Modifier"},
            {"세일_메뉴박스_모디파이어1","","휴지통 : 선택한 모디파이어 삭제","Trashcan: Deleted Selected Modifier"},
            {"세일_메뉴박스_모디파이어1","","+ : 해당 모디파이어 그룹의 수량 증가","+: incremented selected modifier group"},
            {"세일_메뉴박스_모디파이어1","","- : 해당 모디파이어 그룹의 수량 감소","-: decremented selected modifier group"},
            {"세일_메뉴박스_모디파이어1","","DEL ALL MODIFIER : 모든 모디파이어 삭제","Deleted all modifier"},
            {"세일_메뉴박스_모디파이어1","","확인 (V) : 저장","Confirm: Save modifier"},
            {"세일_메뉴박스_모디파이어1","","X : 화면 닫기","Cancel: Closed modifier screen"},
            {"세일_DISCOUNT","","% / $ : %, $ 변환","%/S: %, $ switch"},
            {"세일_DISCOUNT","","키패드 : 숫자 입력","Keypad (Discount): (Number) Input"},
            {"세일_DISCOUNT","","백스페이스 : 입력한 값 삭제","Backspace (Discount): Delete Input"},
            {"세일_DISCOUNT","","확인 (V) : 적용 및 화면 닫기","Confirm: Apply Discount and Close Screen"},
            {"세일_QUICKSALE","","카테고리 박스 : 카테고리 선택","Category Box: Select category"},
            {"세일_QUICKSALE","","키패드 : 숫자 입력","Keypad: Number Input (Quicksale)"},
            {"세일_QUICKSALE","","백스페이스 : 입력한 값 삭제","Backspace: Delete Input (Quicksale)"},
            {"세일_QUICKSALE","","확인 (V) : 적용 및 화면 닫기","Confirm: Apply and Close Screen (Quicksale)"},
            {"세일_PRODUCT","","X : 화면 닫기","X: Closed Screen (Product)"},
            {"세일_PRODUCT","","돋보기 : 상품 찾기","Search: Search Product (Product)"},
            {"세일_GIFTCARD","","BALANCE CHECK : 상품권 잔액 확인 화면 이동","Gift card balance check"},
            {"세일_GIFTCARD","","X : 화면 닫기","X: Closed Screen (Gift Card)"},
            {"세일_GIFTCARD","","돋보기 : 상품권 찾기","Search: Search Gift Card"},
            {"세일_GIFTCARD","","상품권 메뉴박스 : 상품권 선택","Giftcard menu box: select giftcard"},
            {"세일_GIFTCARD","","키패드 : 숫자 입력","Keypad: number Input (Giftcard)"},
            {"세일_GIFTCARD","","백스페이스 : 입력한 값 삭제","Backsapce: Delete Input (Giftcard)"},
            {"세일_GIFTCARD","","확인 (V) : 적용 및 화면 닫기","Confirm: Apply and Close Screen (Giftcard)"},
            {"세일_MENUSEARCH","","X : 화면 닫기","X: Closed Screen (Food Search)"},
            {"세일_MENUSEARCH","","돋보기 : 메뉴 찾기","Search: Search Menu"},
            {"sale","","CUSTOMER : 고객 리스트 화면","Customer: Customer List Screen"},
            {"sale","","Customer Numbers : 고객명수 수정화면 이동","EDIT GUEST NUMBERS"},
            {"sale","","구름 : 클라우드 이동","Cloud: Moved to Cloud"},
            {"sale","","점표 : 컴맨드 화면 이동","Command: Moved to Command Screen"},
            {"sale","","톱니바퀴 : 설정화면 이동","Gear: Moved to Options Screen"},
            {"sale","","X : 로그아웃 및 프로그램 종료","X: Logout and close aprogram"},
            {"sale","","DEL : 선택한 주문목록 삭제","DEL: Delete selected order item"},
            {"sale","","CANCEL : 현재 주문 취소","CANCEL: Delete current order"},
            {"sale","","TO GO : 선택한 주문목록 주문타입 TOGO 로 변경","TO GO: Changed selected item's order type to TOGO"},
            {"sale","","BILL PRINT : 주문전표 출력","BILL PRINT:  Print Order Slip"},
            {"sale","","+ : 선택한 주문의 개수 증가","+: increment selected order item"},
            {"sale","","- : 선택한 주문의 개수 감소","-: decrement selected order item"},
            {"sale","","SEND TO KITCHEN : 현재 주문내용 주방으로 전송","SEND TO KITCHEN: Send Current Order to Kitchen"},
            {"sale","","GENERAL MODIFIER : 선택한 주문에 일반 모디파이어 적용","GENERAL MODIFIER: Apply General Modifier to Selected Order"},
            {"sale","","ADD DIVIDER : 주문목록 분리","ADD DIVIDER: Divide Order List"},
            {"sale","","ADD GRATUITY : 팁 금액 추가","ADD GRATUITY: Add tip amount"},
            {"sale","","PAYMENT : 결제화면 이동","PAYMENT: Move to payment screen"},
            {"sale","","카테고리 박스 : 카테고리 별 메뉴 표시","Category Box: Show menu items in selected category"},
            {"sale","","메뉴 박스 : 해당 메뉴를 주문목록에 추가","Menu Box: Added selected menu to order list"},
            {"sale","","CLOSE : 테이블 선택화면 이동","CLOSE: Moved to table selection screen"},
            {"sale","","SERVER : 서버 선택화면 이동","SERVER: Moved to server selection screen"},
            {"sale","","DISCOUNT : 디스카운트 화면 이동","DISCOUNT: Moved to discount screen"},
            {"sale","","QUICK : 퀵 메뉴 입력화면 이동","QUICK: Moved to quick menu screen"},
            {"sale","","PRODUCTD : 상품 선택화면 이동","PRODUCT: Moved to product screen"},
            {"sale","","GIFT CARD : 상품권 선택화면 이동","GIFT CARD: Moved to Gift Card screen"},
            {"sale","","TIME MENU : 타임메뉴 선택화면 이동","TIME MENU: Move to Time menu screen"},
            {"sale","","MENU SEARCH : 메뉴 찾기 화면 이동","MENU SEARCH: Moved to menu search screen"},
            {"sale","","LOG OUT : 로그아웃","LOG OUT: log out"},
            {"세일_프로그램정보","","i 버튼 : 프로그램 정보화면 이동","SOFTWARE INFORMATION"},
            {"세일_프로그램정보","","-> 프로그램 정보화면의 X : 화면 닫기","X: Closed Program info screen"},
            {"sale1","","SPECIAL REQ : 해당 메뉴의 스페셜 요청사항 입력화면 이동","SPECIAL REQ: Moved to special request screen for selected order item"},
            {"sale_special_request","","X : 화면 닫기","X: Closed Screen (Speical request)"},
            {"sale_special_request","","ENTER : 입력한 요청사항 적용","ENTER: Applied special request input"},
            {"sale_special_request","","CLEAR : 입력한 내용 지우기","CLEAR: Deleted input (special request)"},
            {"sale_special_request","","NEXT LINE : 다음 줄로 커서 이동","NEXT LINE: Moved cursor to next line"},
            {"세일_SETTING_SYSTEM1","","Sync. Real Sale Date : 세일데이터 동기화","SYNC REAL SALE DATA"},
            {"세일_SETTING_SYSTEM1","","(==> Date 가 아니라 Data 로 수정해야 함.)","DATA"},
            {"세일_SETTING_SYSTEM1","","SYSTEM RESET : 시스템 초기화","SYSTEM RESET"},
            {"세일_SETTING_SYSTEM1","","INITIALIZE DB : 데이터베이스 초기화","INITIALIZE DB"},
            {"세일_SETTING_SYSTEM1","","확인 (V) : 저장","DATA SAVE"},
            {"세일_SETTING_SYSTEM1","About QSR t","","About QSR t (i): Moved to software info screen"},
            {"세일_SETTING_SYSTEM1","DB Server Sync: Yes No","","DB is synched to server set to: yes/no"},
            {"세일_SETTING_SYSTEM1","DB Server IP","","DB Server's IP is : (ip address)"},
            {"세일_SETTING_SYSTEM1","Splash Use","","Splash on/off"},
            {"세일_SETTING_SYSTEM1","Forward/Reverse","","Forward/Reverse set to: auto rotation/forward/reverse"},
            {"세일_SETTING_SYSTEM1","Clock In-Out","","Clock In-Out set to: Online/offline"},
            {"세일_SETTING_SYSTEM1","Download Data","","Donwnload Data set to: Only Touching Download Button/"},
            {"세일_SETTING_SYSTEM1","Database Backup","","Database Backup set to: No/In Tender/In Closing App"},
            {"세일_SETTING_SYSTEM1","Cloud Backup In Tender Backup","","Cloud Backup In Tender Backup set to: on/off"},
            {"세일_SETTING_SYSTEM1","Gmail Account","","Gmail Account: ID or Password changed"},
            {"세일_SETTING_SYSTEM1","Membership/Emp. Card Setting","","Membership/Emp. Card Setting: Start: (Start) Count:(Count)"},
            {"세일_SETTING_SYSTEM1","Gift Card Setting","","Gift Card Setting: Start:(Start) Count:(Count)"},
            {"세일_SETTING_SYSTEM2","Pager No. Header (Priting)","","Pager No. Header set to: (Number)"},
            {"세일_SETTING_SYSTEM2","Alert Product Min.","","Alert Product Min. set to: on/off"},
            {"세일_SETTING_SYSTEM2","Days To Upload Data","","Days to upload data set to: (count) days"},
            {"세일_SETTING_SYSTEM2","In Cash Out Batch Y/N","","In Cash Out Batch set to: Yes/No"},
            {"세일_SETTING_SYSTEM2","Printing In Starting Cash","","Printing In Starting Cash: On/Off"},
            {"세일_SETTING_SYSTEM2","Sale Date Modify","","Sale Date Modify: On/Off"},
            {"세일_SETTING_SYSTEM2","Pos Sales Data(s)","","POS sales data count per page set to: (Count)"},
            {"세일_SETTING_SYSTEM2","Price/Add/Amount On Button Modifier","","Price/Add/Amount On Button Modifier set to: On/Off"},
            {"세일_SETTING_SYSTEM2","Modifier Price View","","Modifier price view set to: On/Off"},
            {"세일_SETTING_SYSTEM2","Modifer Qty View","","Modifier Qty view set to: On/Off"},
            {"세일_SETTING_SYSTEM2","Price $ D/P","","Price $ D/P set to: Yes/No"},
            {"세일_SETTING_SYSTEM2","Thank You Page View","","Thank you page view set to: On/Off"},
            {"세일_SETTING_SYSTEM3","Server Code Use","","Server code use set to: On/Off"},
            {"세일_SETTING_SYSTEM3","Server Password Use","","Server password use set to: On/Off"},
            {"세일_SETTING_SYSTEM3","Pickup Type Select Popup","","Pickup Type Select  Popup set to: On/Off"},
            {"세일_SETTING_SYSTEM3","Categoty Printing","","Category Printing set to: On/Off"},
            {"세일_SETTING_SYSTEM3","Open K/P msg. in deleting item","","Open K/P msg. in deleting item set to: On/Off"},
            {"세일_SETTING_SYSTEM3","Print receipt in other payment","","Print receipt in other payment set to: On/Off"},
            {"세일_SETTING_SYSTEM3","print tip line in togo","","Print tip line in togo set to: On/Off"},
            {"세일_SETTING_SYSTEM3","Bill Print Popup","","Bill Print Popup set to: On/Off"},
            {"세일_SETTING_SYSTEM3","Custom Bill Split","","Custom Bill Split set to: On/Off"},
            {"세일_SETTING_SYSTEM3","System Reset","","System Reset button pressed (Settings)"},
            {"세일_SETTING_SYSTEM3","Initialize DB","","Initilize DB button pressed (settings)"},
            {"세일_SETTING_SYSTEM3","Confirm","","Changed settings saved"},
            {"세일_SETTING_RECEIPT1","Printer Name","","Printer Name set to: (Printer name)"},
            {"세일_SETTING_RECEIPT1","Test Print (Connect Printer)","","Test Print: attempted to connect printer"},
            {"세일_SETTING_RECEIPT1","Test Print","","Test Print executed (Receipt printer)"},
            {"세일_SETTING_RECEIPT1","Receipt Paper Count","","Receipt paper count set to: (count)"},
            {"세일_SETTING_RECEIPT1","Network IP","","Network IP (Receipt Printer): (IP address)"},
            {"세일_SETTING_RECEIPT1","Receipt Footer","","Receipt footer set to: (footer)"},
            {"세일_SETTING_RECEIPT2","Cash Drawer On/Off on Command","","Cash Drawer On/Off on Command set to: On/Off"},
            {"세일_SETTING_RECEIPT2","Cash Drawer On/Off on Receipt","","Cash Drawer On/Off on Receipt set to: On/Off"},
            {"세일_SETTING_RECEIPT2","Signed Print Y/N","","Signed Print set to: On/Off"},
            {"세일_SETTING_RECEIPT2","Siened Print Separately","","Signed Print Separately: On/Off"},
            {"세일_SETTING_RECEIPT2","Modifier Print Y/N","","Modifier Print set to: On/Off"},
            {"세일_SETTING_RECEIPT2","Card Merchant Receipt","","Card Merchant Receipt set to: On/Off"},
            {"세일_SETTING_RECEIPT2","Order No.Section View","","Order No. Section View set to: On/Off"},
            {"세일_SETTING_RECEIPT2","Tax Exempt Text(T/E) Use","","Tax Exempt Text Use set to: On/Off"},
            {"세일_SETTING_RECEIPT2","Employee Info Print Y/N","","Employee info print set to: On/Off"},
            {"세일_SETTING_RECEIPT2","Menu List Print Y/N","","Menu List print set to: On/Off"},
            {"세일_SETTING_RECEIPT2","Logo Printing On Receipt","","Logo printing on receipt set to: On/Off"},
            {"세일_SETTING_RECEIPT2","Confirm button","","Saved receipt settings"},
            {"세일_SETTING_KITCHEN_주방프린터설정","Printer Name","","Kitchen printer name set to: (Printer name)"},
            {"세일_SETTING_KITCHEN_주방프린터설정","Connect Printer","","Attempted to connect to a kitchen printer"},
            {"세일_SETTING_KITCHEN_주방프린터설정","Test Print","","Test print executed (kitchen printer)"},
            {"세일_SETTING_KITCHEN_주방프린터설정","Printing Paper Count","","Printing paper count set to: (count)"},
            {"세일_SETTING_KITCHEN_주방프린터설정","Network IP","","Network IP (kitchen printer): (IP address)"},
            {"세일_SETTING_KITCHEN_주방프린터설정","Receipt Footer","","Receipter Footer (kitchen printer) set to: (footer)"},
            {"세일_SETTING_KITCHEN_주방프린터설정","Kitchen Printing","","Kitchen printing set to: on/off"},
            {"세일_SETTING_KITCHEN_주방프린터설정2","Confirm","","Kitchen printer setting saved"},
            {"세일_SETTING_RECEIPT1","","Connected Printer : 프린터 연결","CONNECT PRINTER"},
            {"세일_SETTING_RECEIPT1","","TEST PRINT : 출력 테스트","TEST PRINTING"},
            {"세일_SETTING_RECEIPT1","","확인 (V) : 저장","RECEIPT PRINTER SAVE"},
            {"세일_SETTING_RECEIPT1","","- LABEL PRINTER","LABEL PRINTER"},
            {"세일_SETTING_RECEIPT1","","LABEL1~5 : 레이블 선택","LABEL 1-5"},
            {"세일_SETTING_RECEIPT1","","확인 (V) : 저장","LABEL PRINTER SAVE"},
            {"세일_SETTING_KITCHEN","","KITCHEN1~N : 해당 주방 프린트 설정화면 이동","KITCHEN 1-N"},
            {"세일_SETTING_KITCHEN","","-> 주방 프린트 설정화면","KITCHEN PRINTER SETTING"},
            {"세일_SETTING_KITCHEN","","Connected Printer : 프린터 연결","KITCHEN PRINTER CONNECT"},
            {"세일_SETTING_KITCHEN","","TEST PRINT : 출력 테스트","KITCHEN PRINTER TEST PRINT"},
            {"세일_SETTING_KITCHEN","","확인 (V) : 저장","KITCHEN PRINTER SAVE"},
            {"세일_SETTING_KITCHEN_주방프린터설정","","","KITCHEN PRINTER 2 SETTING"},
            {"세일_SETTING_KITCHEN_주방프린터설정2","","","KITCHEN PRINTER 3 SETTING"},
            {"세일_SETTING_PAYMENT1","","X : 화면 닫기","PAYMENT SETTING CLOSE"},
            {"세일_SETTING_PAYMENT2","","CALCULATOR : 계산기 화면 이동","CALCULATIOR"},
            {"","","BILL SPLIT : 선택한 테이블의 전표 분리 및 병합 화면 이동","BILL SPLIT ON PAYMENT SCREEN"},
            {"","","키패드 : 숫자 입력","KEYPAD INPUT ON PAYMENT SCREEN"},
            {"","","백스페이스 : 입력한 값 삭제","DELETE INPUT VALUE ON PAYMENT SCREEN"},
            {"","","확인 (V) : 적용 및 화면 닫기","CLOSE PAYMENT SCREEN"},
            {"세일_PAYMENT","","X : 화면 닫기","X: Closed screen (Sale Payment)"},
            {"세일_PAYMENT","","CALCULATOR : 계산기 화면 이동","CALCULATOR: Moved to calculator screen"},
            {"세일_PAYMENT","","BILL SPLIT : 선택한 테이블의 전표 분리 및 병합 화면 이동","BILL SPLIT: Moved to selected table's bill split screen"},
            {"세일_PAYMENT","","키패드 : 숫자 입력","KEYPAD: Inputted number (Sale Payment)"},
            {"세일_PAYMENT","","백스페이스 : 입력한 값 삭제","BACKSPACE: deleted input (Sale Payment)"},
            {"세일_PAYMENT","","확인 (V) : 적용 및 화면 닫기","CONFIRM: Applied and closed screen (Sale Payment)"},
            {"세일_COMMAND","","TABLE BOARD : 테이블 선택 화면 이동","NOVE TO TABLE SCREEN"},
            {"세일_COMMAND","","DOWLOAD / UPLOAD : Download / Upload 화면 이동","DOWNLOAD/UPLOAD: moved to download/Upload screen"},
            {"세일_COMMAND","","POS SALES : 포스의 세일 히스토리 화면 이동","POS SALES: Moved to POS sales history screen"},
            {"세일_COMMAND","","ONLINE SALES : 온라인 주문의 세일 히스토리 화면 이동","ONLINE SALES: Moved to online sales history screen"},
            {"세일_COMMAND","","TAX EXEMPT : 세금 면제","TAX EXEMPT"},
            {"세일_COMMAND","","UNDO TAX EXEMPT : 세금 면제 취소","UNDO TAX EXEMPT"},
            {"세일_COMMAND","","BATCH SUMMARY : 카드 배치 화면 이동","BATCH SUMMARY: move to card batch summary screen"},
            {"세일_COMMAND","","HOLD : 주문 홀드","HOLD: Hold orders"},
            {"세일_COMMAND","","RECALL : 홀드된 주문목록 화면 이동","RECALL: Moved to hold order list screen"},
            {"세일_COMMAND","","CLOUD BACK OFFICE : 클라우드 화면 이동","CLOUD BACK OFFICE: Moved to cloud screen"},
            {"세일_COMMAND","","CLOCK IN / OUT : 출퇴근 화면 이동","CLOCK IN/OUT: moved to clock in_out screen"},
            {"세일_COMMAND","","BACK UP DATABASE : DB 백업기능 이동","BACKED UP DATABASE"},
            {"세일_COMMAND","","RESTORE DATABASE : DB 복원기능 이동","RESTORED DATABASE"},
            {"세일_COMMAND","","CASH IN / OUT : 캐쉬 인 아웃 기능 이동","CASH IN/OUT : Moved to cash in/out screen"},
            {"세일_COMMAND","","CASH DRAWER : 돈통 열기","CASH DRAWER: Opened cash drawer"},
            {"세일_CUSTOMER","","X : 화면 닫기","X: Closed Screen (Customer)"},
            {"세일_CUSTOMER","","돋보기 : 고객 찾기","Search: Searched Customer"},
            {"세일_CUSTOMER","","Add Cust : 고객 등록화면 이동","Add cust: Moved to add customer screen"},
            {"세일_CUSTOMER","","-> 고객 등록화면","ADD cust: add customer screen"},
            {"세일_CUSTOMERSEARCH","","X : 화면 닫기","X: Closed Screen (Customer Search)"},
            {"세일_CUSTOMERSEARCH","","LIST VIEW : 이전화면 (고객 찾기/목록) 이동","LIST VIEW: Returned to customer search/list screen"},
            {"세일_CUSTOMERSEARCH","","확인 (V) : 저장","Confirm: Saved new customer info"},
            {"세일_CUSTOMER","","연필 : 해당 고객의 정보 수정화면 이동","Pencil: Edited selected customer info"},
            {"세일_CUSTOMER","","-> 위의 Add Cust 와 같은 화면","ADD cust: add customer screen"},
            {"테이블선택","","각 테이블 : 선택한 테이블 값을 가지고 세일화면으로 이동","Individual Table: Moved to sales screen with selected table cost"},
            {"테이블선택","","CHECK LIST : 주문목록 화면 이동","CHECK LIST: Moved to Order list screen"},
            {"테이블선택","","COMMAND : 컴맨드 화면 이동","COMMAND: Moved to command screen"},
            {"테이블선택","","TO GO : TO GO 선택 화면 이동","TO GO: Moved to TO GO select screen"},
            {"테이블선택","","우측 사이드 메뉴 확장 버튼 : 사이드 메뉴 확장","Right side menu tab: Expanded side menu"},
            {"테이블선택_CHECKLIST","","X : 화면 닫기","X: closed screen (Table check list)"},
            {"테이블선택_CHECKLIST","","ALL CHECKS : 전체 전표 화면출력","OPEN ALL CHECKS SCREEN"},
            {"테이블선택_CHECKLIST","","테이블 ZONE 및 TO GO : 해당 값에 대한 전표 출력","TABLE ZONE"},
            {"테이블선택_CHECKLIST","","CLEAR CHECKED : 선택된 전표의 선택 취소","CLEAR CHECKED: Cleared selection of selected checks"},
            {"테이블선택_CHECKLIST","","MASTER : 선택된 전표의 마스터 영수증 출력","MASTER: Printed selected check's master receipt"},
            {"테이블선택_CHECKLIST","","KITCHEN : 선택된 전표를 주방으로 출력","KITCHEN: printed selected check to kitchen"},
            {"테이블선택_사이드메뉴2","","X : 사이드 메뉴 확장 닫기","X: Collpased side menu"},
            {"테이블선택_사이드메뉴2","","BILL PRINT : 선택한 테이블의 주문전표 출력","BILL PRINT:  Prinedt selected table's bill"},
            {"테이블선택_사이드메뉴2","","BILL SPLIT / MERGE /PRINT : 선택한 테이블의 전표 분리 및 병합 화면 이동","SPLIT/MERGE/PRINT:  Moved to bill split screen for selected table"},
            {"테이블선택_사이드메뉴2","","","DETAIL INFO: displayed detail for selected table"},
            {"테이블선택_사이드메뉴2","","MOVE TABLE : 선택한 테이블을 이동","MOVE TABLE: Moved selected table"},
            {"테이블선택_사이드메뉴2","","TABLE SPLIT : 선택한 테이블 분리","TABLE SPLIT: Split selected table"},
            {"테이블선택_사이드메뉴2","","TABLE MERGE : 선택한 테이블을 병합","TABLE MERGE: Merged selected tables"},
            {"테이블선택_사이드메뉴3","","TABLE CLEAR : 선택한 테이블 주문 취소","TABLE CLEAR: Cleared selected table's order"},
            {"테이블선택_BILLSPLIT1","","X : 화면 닫기","X: Closed screen (Table Bill Split)"},
            {"테이블선택_BILLSPLIT1","","SPLIT SELECTED  : 선택한 메뉴 별로 전표 분리","SPLIT SELECTED: Split selected menu"},
            {"테이블선택_BILLSPLIT2","","SPLIT EVENLY : 선택한 개수대로 동일한 금액으로 전표 분리","SPLIT EVENLY: Split order by selected number"},
            {"테이블선택_BILLSPLIT1","","MERGE SELECTED : 선택한 전표 병합","MERGE SELECTED: Merged selected splits"},
            {"테이블선택_BILLSPLIT1","","RESET SELECTED : 선택한 전표 초기화","RESET SELECTED: Reset selected splits"},
            {"테이블선택_BILLSPLIT1","","RESET ALL : 전체 전표 초기화","RESET ALL: Reset split"},
            {"테이블선택_BILLSPLIT1","","SELECTED PRINTING : 선택한 전표 출력","SELECTED PRINTING: Printed selected split"},
            {"테이블선택_BILLSPLIT1","","VOID BY BILLS : 결제된 전표의 결제 취소","VOID BY BILLS: Voided selected split"},
            {"테이블선택_BILLSPLIT2","","PAY BY BILLS : 선택된 전표에 대한 결제화면 이동","PAY BY BILLS: Moved to payment screen for selected split"},
            {"Empty","Empty","Empty","Empty"},
            {"","SaleHistory VOID","","View Open"},
            {"","SaleHistory VOID YES","","YES"},
            {"","SaleHistory RETURN","","View Open"},
            {"","SaleHistory Delivering","",""},
            {"","SaleHistory Done","",""},
            {"","SaleHistory Reprint","",""},
            {"","SaleHistory Tip Adjust","",""},
            {"","SaleHistory Close","",""},
            {"","SaleHistory Repay","",""},
            {"","SaleHistory Send Message","",""},
            {"","SaleHistory RETURN YES","","YES"}
    };

    public static void saveLogsInDB(int paramArrIndex) {
        String[] getLogValues = logContentsArr[paramArrIndex];
        if (getLogValues != null && getLogValues.length > 0) {
            String get_btnPage = "";
            String get_btnName = "";
            String get_btnContentsKor = "";
            String get_btnContentsEng = "";

            get_btnPage = getLogValues[0];

            if (getLogValues.length > 1) {
                get_btnName = getLogValues[1];
            }
            if (getLogValues.length > 2) {
                get_btnContentsKor = getLogValues[2];
            }
            if (getLogValues.length > 3) {
                get_btnContentsEng = getLogValues[3];
            }

            if (paramArrIndex == 99){   // 메뉴 선택시
                GlobalMemberValues.str_logsaveIn_MenuNames = GlobalMemberValues.str_logsaveIn_MenuNames.replaceAll("\n"," ");
                get_btnContentsKor = GlobalMemberValues.str_logsaveIn_MenuNames + " : 메뉴를 주문목록에 추가";
                get_btnContentsEng = GlobalMemberValues.str_logsaveIn_MenuNames + " : Added selected menu to order list";
                GlobalMemberValues.str_logsaveIn_MenuNames = "";
            }

            if (paramArrIndex == 91){ // +
                TemporarySaleCart tempSaleCartInstance = MainMiddleService.mGeneralArrayList.get(MainMiddleService.selectedPosition);
                String temp_menuName = tempSaleCartInstance.mSvcName;
                String temp_menuQty = tempSaleCartInstance.mSQty;
                temp_menuName = temp_menuName.replaceAll("\n"," ");
                get_btnContentsKor = temp_menuName + " + : 선택한 주문의 개수 증가 (" + temp_menuQty + ")";
                get_btnContentsEng = temp_menuName + " + : increment selected order item (" + temp_menuQty + "ea)";
            }
            if (paramArrIndex == 92){ // +
                TemporarySaleCart tempSaleCartInstance = MainMiddleService.mGeneralArrayList.get(MainMiddleService.selectedPosition);
                String temp_menuName = tempSaleCartInstance.mSvcName;
                String temp_menuQty = tempSaleCartInstance.mSQty;
                temp_menuName = temp_menuName.replaceAll("\n"," ");
                get_btnContentsKor = temp_menuName + " - : 선택한 주문의 개수 감소 (" + temp_menuQty + ")";
                get_btnContentsEng = temp_menuName + " - : decrement selected order item (" + temp_menuQty + "ea)";
            }
            if (paramArrIndex == 207){
                get_btnContentsKor = "확인 (V) : 적용 및 화면 닫기 + (" + str_paymentKind + ")";
                get_btnContentsEng = "CONFIRM: Applied and closed screen (Sale Payment : "+ str_paymentKind + ")";

                str_paymentKind = "";
            }
            if (paramArrIndex == 98){
                get_btnContentsKor = "카테고리 박스 : 카테고리 별 메뉴 표시 (" + GlobalMemberValues.str_logsaveIn_CategoryNames + ")";
                get_btnContentsEng = "Category Box: Show menu items in selected category ("+ GlobalMemberValues.str_logsaveIn_CategoryNames + ")";
                GlobalMemberValues.str_logsaveIn_CategoryNames = "";
            }


            // 20230112 추가
            String str_holdcode = "";
            if (MainMiddleService.mHoldCode.equals("") || MainMiddleService.mHoldCode == null){
            } else {
                str_holdcode = MainMiddleService.mHoldCode;
            }
            // 20230112 추가

            // 20231017 추가
            String str_empIdx = "";
            String str_empName = "";
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO != null){
                if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx.equals("") || GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx == null){
                } else {
                    str_empIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
                }

                if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName.equals("") || GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName == null){
                } else {
                    str_empName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
                }
            }


            // 20231017 추가

            Vector<String> strInsertQueryVec = new Vector<String>();
            String strQuery = "";
            strQuery = " insert into btn_logs ( " +
                    " scode, sidx, stcode, btnpagename, btnname, btnlogkor, btnlogeng, employeeIdx, employeeName, holdcode " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.SALON_CODE + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                    " '" + get_btnPage + "', " +
                    " '" + get_btnName + "', " +
                    " '" + get_btnContentsKor + "', " +
                    " '" + get_btnContentsEng + "', " +
                    " '" + str_empIdx + "', " +
                    " '" + str_empName + "', " +
                    " '" + str_holdcode + "'" +
                    " ) ";

            strInsertQueryVec.addElement(strQuery);
            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("logsavesjjjlog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResultOnlySqllite(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                //GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            } else {

                // 날짜별로 txt 파일로 아래 내용으로 저장
                String logWriteDate = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select strftime('%m-%d-%Y %H:%M:%S', wdate) from btn_logs order by idx desc limit 1 ");
                String logData = "";
                logData = GlobalMemberValues.SALON_CODE + "," +
                        GlobalMemberValues.STORE_INDEX + "," +
                        GlobalMemberValues.STATION_CODE.toUpperCase() + "," +
                        get_btnPage + "," +
                        get_btnName + "," +
                        get_btnContentsKor + "," +
                        get_btnContentsEng + "," +
                        str_empIdx + "," +
                        str_empName + "," +
                        str_holdcode +","+
                        logWriteDate;
                setWriteLogsOnFiles(logData);

                // 아래에 30일 이전의 로그 txt 파일삭제기능 추가

            }
        }
    }

    public static void saveLogsInDB(int paramArrIndex, String salescode) {
        String[] getLogValues = logContentsArr[paramArrIndex];
        if (getLogValues != null && getLogValues.length > 0) {
            String get_btnPage = "";
            String get_btnName = "";
            String get_btnContentsKor = "";
            String get_btnContentsEng = "";

            get_btnPage = getLogValues[0];

            if (getLogValues.length > 1) {
                get_btnName = getLogValues[1];
            }
            if (getLogValues.length > 2) {
                get_btnContentsKor = getLogValues[2];
            }
            if (getLogValues.length > 3) {
                get_btnContentsEng = getLogValues[3];
            }

            // 20230112 추가
            String str_holdcode = "";
            if (MainMiddleService.mHoldCode.equals("") || MainMiddleService.mHoldCode == null){
            } else {
                str_holdcode = MainMiddleService.mHoldCode;
            }
            // 20230112 추가

            // 20231017 추가
            String str_empIdx = "";
            String str_empName = "";
            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx.equals("") || GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx == null){
            } else {
                str_empIdx = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empIdx;
            }

            if (GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName.equals("") || GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName == null){
            } else {
                str_empName = GlobalMemberValues.GLOBAL_EMPLOYEEINFO.empName;
            }

            // 20231017 추가

            Vector<String> strInsertQueryVec = new Vector<String>();
            String strQuery = "";
            strQuery = " insert into btn_logs ( " +
                    " scode, sidx, stcode, btnpagename, btnname, btnlogkor, btnlogeng, employeeIdx, employeeName, salescode, holdcode " +
                    " ) values ( " +
                    " '" + GlobalMemberValues.SALON_CODE + "', " +
                    " '" + GlobalMemberValues.STORE_INDEX + "', " +
                    " '" + GlobalMemberValues.STATION_CODE.toUpperCase() + "', " +
                    " '" + get_btnPage + "', " +
                    " '" + get_btnName + "', " +
                    " '" + get_btnContentsKor + "', " +
                    " '" + get_btnContentsEng + "', " +
                    " '" + str_empIdx + "', " +
                    " '" + str_empName + "', " +
                    " '" + salescode + "', " +
                    " '" + str_holdcode + "' " +
                    " ) ";

            strInsertQueryVec.addElement(strQuery);
            for (String tempQuery : strInsertQueryVec) {
                GlobalMemberValues.logWrite("logsavesjjjlog", "query : " + tempQuery + "\n");
            }
            // 트랜잭션으로 DB 처리한다.
            String returnResult = MainActivity.mDbInit.dbExecuteWriteForTransactionReturnResultOnlySqllite(strInsertQueryVec);
            if (returnResult == "N" || returnResult == "") {
                //GlobalMemberValues.displayDialog(MainActivity.mContext, "Warning", "Database Error", "Close");
            } else {

                // 날짜별로 txt 파일로 아래 내용으로 저장
                String logWriteDate = MainActivity.mDbInit.dbExecuteReadReturnString(
                        " select strftime('%m-%d-%Y %H:%M:%S', wdate) from btn_logs order by idx desc limit 1 ");
                String logData = "";
                logData = GlobalMemberValues.SALON_CODE + "," +
                        GlobalMemberValues.STORE_INDEX + "," +
                        GlobalMemberValues.STATION_CODE.toUpperCase() + "," +
                        get_btnPage + "," +
                        get_btnName + "," +
                        get_btnContentsKor + "," +
                        get_btnContentsEng + "," +
                        str_empIdx + "," +
                        str_empName + "," +
                        salescode + "," +
                        str_holdcode +","+
                        logWriteDate;
                setWriteLogsOnFiles(logData);

                // 아래에 30일 이전의 로그 txt 파일삭제기능 추가

            }
        }
    }

    public static void setWriteLogsOnFiles(String paramData) {
        try {
            if (!GlobalMemberValues.isStrEmpty(paramData)) {
                String logFileName = GlobalMemberValues.STR_NOW_DATE + ".txt";
                String logFilePath = MainActivity.mContext.getFilesDir().getAbsolutePath() + "/" + logFileName;

                File file = new File(logFilePath);
                if (!file.exists()) {
                    file.createNewFile();
                } else {
                    paramData = "\n" + paramData;
                }

                FileWriter fwr = new FileWriter(file, true);
                BufferedWriter bwriter = new BufferedWriter(fwr);

                bwriter.append(paramData);
                bwriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setInitDB() {
        // 60 일 이전의 로그데이터는 삭제한다.
        String tempDate = DateMethodClass.getCustomEditDate(GlobalMemberValues.STR_NOW_DATE, 0, 0, -60);
        String strQuery = " delete from btn_logs " +
                " where wdate between '1900-01-01' and '" + tempDate + "' ";

        MainActivity.mDbInit.dbExecuteWriteReturnValue(strQuery);
    }

    public static void setPaymentKind(double cash, double card, double giftcard, double point, double check, boolean isCardFinish){
        String temp_paymentKinds = "";
        if (cash != 0){
            temp_paymentKinds = "Cash";
        }
        if (card != 0){
            if (temp_paymentKinds != ""){
                temp_paymentKinds = temp_paymentKinds + ",Card";
            } else {
                temp_paymentKinds = "Card";
            }
        } else if (isCardFinish){
            if (temp_paymentKinds != ""){
                temp_paymentKinds = temp_paymentKinds + ",Card";
            } else {
                temp_paymentKinds = "Card";
            }
        }
        if (giftcard != 0){
            if (temp_paymentKinds != ""){
                temp_paymentKinds = temp_paymentKinds + ",Gift Card";
            } else {
                temp_paymentKinds = "Gift Card";
            }

        }
        if (point != 0){
            if (temp_paymentKinds != ""){
                temp_paymentKinds = temp_paymentKinds + ",Point";
            }else {
                temp_paymentKinds = "Point";
            }
        }
        if (check != 0){
            if (temp_paymentKinds != ""){
                temp_paymentKinds = temp_paymentKinds + ",Check";
            }else{
                temp_paymentKinds = "Check";
            }

        }
        str_paymentKind = temp_paymentKinds;


    }
}


// 날짜를 확인하고 파일 지우는것
// 메소드를 만들어서