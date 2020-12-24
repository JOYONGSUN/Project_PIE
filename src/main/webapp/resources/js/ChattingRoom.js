var websocket;
function connect(){
   //7. 소켓 연결시 chatting.do 가 servlet-context 에 매핑시켜 놓은 주소와 맞으면 연결이 됨 이때도 방 번호를 파라미터로 들고감 들고 echoHandler 로 감 근데 가기전에 인터셉터 있음 
   //    그 인터셉터가 socketInterceptor
   websocket = new WebSocket('ws://localhost:8090/WebJSP_JDBC_1Team_MVC_MODEL2_EMP/chatting.do?roomNo='+$('#roomNo').val());
   websocket.onopen =(evt) =>{
      };

    websocket.onclose =(evt) =>{
    }; 
    
   //11. 전송을 한 메시지를 받는게 아니라 handler에서 보내주는 메시지를 받는 것 ..들어왔다, .. 나갔다 등등
   websocket.onmessage=(evt)=>{
      writeMsg(evt);
   }
}; 

function disconnect(){
   websocket.close();
}


//들어오는 메시지의 형태에 따라 뿌려주는 방식이 다름
function writeMsg(evt){
   let html = evt.data;
   console.log(html);
   
   if(typeof(html) !="object" && html.indexOf('|')==-1){
      html+='<br>';
      $('#textarea').append(html);
   }else if (typeof(html) != "object" && html.includes('|')){
      let sessionid = html.split('|')[0];
      let message = html.split('|')[1];

      if(sessionid==$('#userName').val()){
      html = "<div class='myId'>"+sessionid+"<div>";
      html +="<div class='myMessage badge bg-primary text-wrap'>"+message+"</div>";
      }else{
         html = "<div class='others'>"+sessionid+" <div>";
         html +="<div class='othersMsg badge bg-dark text-wrap'>"+message+"</div>";
      }
      if(message != ''){
          console.log(message);
         $('#textarea').append(html);
         }
         $('#msg_content').val('');
   }else if(typeof(html)=="object") {
      let url = URL.createObjectURL(html);
      console.log(url);
      html = '<div style="width:40%"><img src='+url+' class="w-100"></div>';
      $('#textarea').append(html);
      $('#msg_file').val('');
   } 
   //스크롤 밑으로 내려주는거 
   $('#box').scrollTop($('#box')[0].scrollHeight);
}


$(function(){
   //6. 들어오자마자 바로 connect 함수 통해 소켓 연결 함
   connect();
   getEmoticons($('.emoji-area'));
   
   //12. 메시지를 적어서 전송 버튼을 눌렀을때 실행 되는 것
    $('#send').click(()=>{
      //보내는거
      
      if($('#msg_content').val()){
          }
      console.log($('#msg_content').val());
       websocket.send($('#msg_content').val());
      
       if($('#msg_file').val() != ''){
          console.log($('#msg_file').val());
         console.log($('#msg_file')[0].files[0]);
         websocket.send($('#msg_file')[0].files[0]);    

         }
      //받는거 
      websocket.onmessage=(evt)=>{
         writeMsg(evt);
      }
   });

   //엔터치면 전송되는거 
   $('#msg_content').on("keyup", (event) => {
      if (event.keyCode === 13) {
         event.preventDefault();
         $('#send').click();
      }

   });

   $('#fileSend').click(()=>{
      $('#msg_file').click();
   });

   let emoji_content = $('.emoji-content');

   // 모달처럼 이모티콘 닫기
   $(document).on('click', (ev) => {
      if(emoji_content.hasClass('appear')){
         switchAnimation(emoji_content);
      }
   });

   // 이모티콘 태그 토글 : 캡쳐링 방지
   $('.smile-o').on('click', (ev) =>{
      ev.stopPropagation();
      
      switchAnimation(emoji_content);

      
   });

   // 캡쳐링 방지
   emoji_content.on('click', (ev) => {
      ev.stopPropagation();
   });

       
});

function getEmoticons(target){
   $.ajax(
      {
         url : "resources/json/emoticon.json",
         type : "POST",
         dataType : "json",
         beforeSend : () => {
            // 보내기 전
         },
         
         complete: (jqXHR, textStatus) => {
            console.log(jqXHR + "/" + textStatus);
         },

         success : (data) => {
            
            console.log( Object.keys(data) );
            
            $.each(data, (key, value) => {
               
               console.log("테마 : " + key);
               console.log("표시 : " + value[0].emoji);   // HTML 엔티티(&#과 10진수;)
               console.log("코드 : " + value[0].code + " / " + "설명 : " + value[0].description);
               console.log("테마별 길이 : " + value.length);
               console.log("------------------------------");

               // 이모티콘 테마 태그
               let emojiBlock = '<hr class="hr-small">'
                           + '<div class="emoji-head" title="'+ key + '">'
                           + '<h5 class="emoji-theme">' + key + '</h5>'
                           + '</div>'
                           + '<hr class="hr-small">'
                           + '<div class="each-list">';

               for(let seq in value){
                  emojiBlock += '<div class="list-item"'
                             + 'id="' + value[seq].no + '" title="' + value[seq].description + '">' 
                             + '<span class="emoticon" onclick="inputEmoji(this)">' + value[seq].emoji + '</span>'
                             + '</div>';
               }
               
               emojiBlock += '<hr class="hr-small">';
               
               target.append(emojiBlock);
            });
         },

         error : (xhr) => {
            console.log("상태코드 : " + xhr.status + " ERROR");
         }
      }
   );
}

// 이모티콘 커서 위치 조정 함수
$.fn.setCursorPosition = function( pos )
{
  this.each( function( index, elem ) {
    if( elem.setSelectionRange ) {
      elem.setSelectionRange(pos, pos);
    } else if( elem.createTextRange ) {
      var range = elem.createTextRange();
      range.collapse(true);
      range.moveEnd('character', pos);
      range.moveStart('character', pos);
      range.select();
    }
  });

  return this;
};


// 이모티콘 리스너
function inputEmoji(me){
   let element = document.getElementById('msg_content');
   let strOriginal = element.value;
   let iStartPos = element.selectionStart;
   let iEndPos = element.selectionEnd;
   let strFront = "";
   let strEnd = "";
   console.log(iStartPos + " / " + iEndPos);
   if(iStartPos == iEndPos) {
      
      strFront = strOriginal.substring(0, iStartPos);
      strEnd = strOriginal.substring(iStartPos, strOriginal.length);

      console.log(strFront + " / " + strEnd);
   } else return;
   element.value = strFront + me.innerHTML + strEnd;
   console.log(strFront.length + " |자리| " + me.innerHTML.length)
   $('#msg_content').focus().setCursorPosition(strFront.length + me.innerHTML.length);
}

// 이모티콘 켜기/끄기
function switchAnimation(target) {
   if(target.hasClass('appear')){
      target.addClass('disappear');
      setTimeout( () => { 
         target.removeClass('appear'); 
         target.css('display','none'); 
      }, 580 );
   }else {
      target.removeClass('disappear').addClass('appear');
      target.css('display','block');
   }
}