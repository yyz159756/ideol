let editor;

window.onload = function() {
    editor = ace.edit("editor");
    editor.setTheme("ace/theme/monokai");
    editor.session.setMode("ace/mode/c_cpp");
    
    editor.setValue("#include <iostream>\r\nusing namespace std;\r\n \r\nint main() \r\n{\r\n    cout << \"Hello, World!\";\r\n    return 0;\r\n}")
    document.getElementById('filename').value = "HelloWorld.cpp";
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
    // 构造请求参数
    var filename = document.getElementById('filename').value;

    var j = {
        type: $("#languages").val(),
        code: editor.getSession().getValue(),
        filename: filename
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
        cache:false
    }).done(function (data) {
        var jsonData = JSON.stringify(data);// 转成JSON格式
        var result = $.parseJSON(jsonData);// 转成JSON对象

        var code = result['code']
        var message = result['message']
        console.log(code)
        console.log(message)
        // 如果成功
    if (code == 20000){
        $(".output").text(message)

        $("#success_info").text('发送成功 O(∩_∩)O' );
        $("#myAlertSuccess").modal("show"); 
        window.setTimeout(function(){
            $("#myAlertSuccess").modal('hide');  //手动关闭success模态框
            $("#myModal").modal('hide');  //手动关闭注册模态框
        },1000); 
    } else{
        $("#warning_info").text("编译出错 TAT");
        $("#myAlert").modal("show"); 
        // 1.5秒自动关闭        
        window.setTimeout(function(){
            $("#myAlert").modal('hide');  //手动关闭danger模态框
        },1000); 
    }
    }).fail(function (data){
        // 弹框
        $("#warning_info").text('服务器发送错误 TAT' );
        $("#myAlert").modal("show"); 
        // 1.5秒自动关闭        
        window.setTimeout(function(){
        $("#myAlert").modal('hide');  //手动关闭danger模态框
        },1000); 
    }); 
    
}
