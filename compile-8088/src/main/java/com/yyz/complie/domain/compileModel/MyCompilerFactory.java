package com.yyz.complie.domain.compileModel;


import org.springframework.stereotype.Component;

/**
 * @author yangyizhou
 * @create 2022/3/7 16:39
 */
@Component
public class MyCompilerFactory {
    static final String JAVA = "java";
    static final String GCC = "gcc";
    static final String PYTHON_3 = "python3";
    public static MyCompilerI newInstance(String type){
        if(JAVA.equals(type)){
            return new MyJavaCompiler();
        }else if(GCC.equals(type)){
            return new MyGccCompiler();
        }else if(PYTHON_3.equals(type)){
            return new MyPythonCompiler();
        }

        return null;
    }
}
