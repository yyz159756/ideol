let editor;

window.onload = function() {
    editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");
    editor.session.setMode("ace/mode/c_cpp");
    
    editor.setValue("#include <iostream>\r\nusing namespace std;\r\n \r\nint main() \r\n{\r\n    cout << \"Hello, World!\";\r\n    return 0;\r\n}")
    document.getElementById('filename').value = "HelloWorld.cpp";
}

function see_save_history(){
    $("#histroyAlert").modal("show"); 
    var serial = localStorage.getItem('serial')
    var saveCache = JSON.parse(localStorage.getItem(serial + "save"));
    if (saveCache == ""){
        return
    }
    creatTab()

    function creatTab(){
        div=document.getElementById('history_modal_body')

        var tab='<table class="table">'
        tab += '<thead><tr> <th scope="col">#</th><th scope="col">filename</th><th scope="col">type</th><th scope="col">Handle</th></tr></thead>'
        tab += '<tbody>'
        // 格式
        // <tr>
        //     <th scope="row">0</th>
        //     <td>filename</td>
        //     <td>type</td>
        //     <td> <button/> </td>
        // </tr>
    
        for(var i = 0; i < saveCache.length; i++) {
            tab += '<tr>'
            tab += '<th scope="row">' + i + '</th>'
            tab += '<td>' + saveCache[i]['filename'] + '</td>'
            tab += '<td>' + saveCache[i]['type'] + '</td>'
            // tab += '<td>' + '<button id=\'select_save_file_btn\' value=\'' +  saveCache[i]['filename'] +'\'>选择</button>' + '</td>'
            tab += '<td>' + '<button id=\'select_save_file_btn\' onclick="turn_to_save_file(\''+saveCache[i]['filename'] +'\')">选择</button>' + '</td>'

            tab += '</tr>'
        }
        
        tab += '</tbody>'
        tab +='</table>';
        div.innerHTML=tab
    }
}

function turn_to_save_file(filename){
    // 关闭模态窗口
    $("#histroyAlert").modal('hide');  //手动关闭保存记录模态框

    // 查询记录
    var serial = localStorage.getItem('serial')

    var saveCache = JSON.parse(localStorage.getItem(serial + "save"));
    
    var isFind = false
    for(var i = 0; i < saveCache.length; i++) {
        // 找到文件数据，更新editor
        if (saveCache[i]['filename'] == filename){
            code = saveCache[i]['code']
            type = saveCache[i]['type']
            isUpdate = true
            // 动态修改编译器类型select多选框的值
            var select = document.getElementById("languages");  
            for (var i = 0; i < select.options.length; i++){  
                if (select.options[i].value == type){  
                    select.options[i].selected = true;  
                    break;  
                }  
            }
            // 修改编辑器model
            changeLanguage()
            // 修改文件名
            document.getElementById('filename').value = filename
            // 修改编辑器内代码
            editor.setValue(code)

            // 清除输出栏
            $(".output").text("")

        }
    }

    // 未知错误，请排查
    if (isFind == false){
        
    }

}

function save_file(){
    var type = $("#languages").val();
    var code = editor.getSession().getValue();
    var filename = document.getElementById('filename').value;
    var serial = localStorage.getItem('serial');

    var data = {
        type: type,
        code: code,
        filename: filename,
        serial: localStorage.getItem('serial')
    }

    var saveCache = JSON.parse(localStorage.getItem(serial + "save"));

    
    if (saveCache == null){
        localStorage.setItem(serial + "save", JSON.stringify([data]));
    } else{
        if (saveCache.length >= 3){
            // 弹框
            $("#warning_info").text('对不起，当前您只能保存3条数据，后续功能敬请期待' );
            $("#myAlert").modal("show"); 
            // 2秒自动关闭        
            window.setTimeout(function(){
              $("#myAlert").modal('hide');  //手动关闭danger模态框
            },2000); 
            return
        }

        var isUpdate = false
        for(var i = 0; i < saveCache.length; i++) {
            // 如果已经存在该文件了，就更新就好
            if (saveCache[i]['filename'] == filename){
                saveCache[i]['serial'] = serial
                saveCache[i]['code'] = code
                saveCache[i]['type'] = type
                isUpdate = true
            }
        }

        // 如果不存在文件，则插入数组
        if (isUpdate == false){
            saveCache.push(data)
        }


        localStorage.setItem(serial + "save", JSON.stringify(saveCache));

        console.log(saveCache)
        $("#success_info").text('保存成功 O(∩_∩)O' );
        $("#myAlertSuccess").modal("show"); 
        window.setTimeout(function(){
          $("#myAlertSuccess").modal('hide');  //手动关闭success模态框
        },1500); 
    }

    // localStorage.clear(serial + "save")
    

}

function changeLanguage() {

    let language = $("#languages").val();
    console.log(language)
    if(language == 'gcc' ){
        editor.session.setMode("ace/mode/c_cpp");
        editor.setValue("#include <iostream>\r\nusing namespace std;\r\n \r\nint main() \r\n{\r\n    cout << \"Hello, World!\";\r\n    return 0;\r\n}")
        document.getElementById('filename').value = "HelloWorld.cpp";
    }
    else if(language == 'python3'){
        editor.session.setMode("ace/mode/python");
        editor.setValue("print('Hello World!')")
        document.getElementById('filename').value = "HelloWorld.py";
    }

    else if (language == 'java') {
        editor.session.setMode("ace/mode/java");
        editor.setValue("public class HelloWorld {\r\n    public static void main(String[] args){\r\n        System.out.println(\"Hello World!\");\r\n    }\r\n}");
        document.getElementById('filename').value = "HelloWorld.java";

    }
}

function executeCode() {
    var filename = document.getElementById('filename').value;
    var serial = localStorage.getItem('serial')

    // 构造请求参数
    var j = {
        type: $("#languages").val(),
        code: editor.getSession().getValue(),
        filename: filename
    }
   

    if (serial != null){
        j['serial'] = serial
    }
    
    // post请求api
    $.ajax({ 
        url:'http://localhost:8088/onlineIde/api/v1/compile/', 
        data:JSON.stringify(j), 
        type:'post', 
        dataType:'json', 
        headers:{ 
        Accept:"application/json", 
        "Content-Type":"application/json"
         }, 
        processData:false, 
        cache:false,
        beforeSend:function(){
            showModal();
        }
    }).done(function (data) {
        hideModal();

        var jsonData = JSON.stringify(data);// 转成JSON格式
        var result = $.parseJSON(jsonData);// 转成JSON对象

        var code = result['code']
        var message = result['message']
        console.log(code)
        console.log(message)
        // 如果成功
    if (code == 20000){
        $(".output").text(message)

        $("#success_info").text('消息成功返回 O(∩_∩)O' );
        $("#myAlertSuccess").modal("show"); 
        window.setTimeout(function(){
            $("#myAlertSuccess").modal('hide');  //手动关闭success模态框
            $("#myModal").modal('hide');  //手动关闭注册模态框
        },1500); 
    } else{
        $(".output").text(message)
        $("#warning_info").text("编译出错 TAT");
        $("#myAlert").modal("show"); 
        // 1.5秒自动关闭        
        window.setTimeout(function(){
            $("#myAlert").modal('hide');  //手动关闭danger模态框
        },1500); 
    }
    }).fail(function (data){
        hideModal();
        // 弹框
        $("#warning_info").text('服务器发送错误 TAT' );
        $("#myAlert").modal("show"); 
        // 1.5秒自动关闭        
        window.setTimeout(function(){
        $("#myAlert").modal('hide');  //手动关闭danger模态框
        },1500); 
    }); 
    
}


function hideModal(){
    $('#waitModal').modal('hide');
}

function showModal(){
    $('#waitModal').modal({backdrop:'static',keyboard:false});
}
