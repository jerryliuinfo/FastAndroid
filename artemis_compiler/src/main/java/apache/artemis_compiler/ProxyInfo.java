package apache.artemis_compiler;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * @auther tb
 * @time 2018/3/27 下午2:44
 * @desc 对应需要生成某个类的全部相关信息
 */
public class ProxyInfo {
    /**
     * 类
     */
    public TypeElement typeElement;
    /**
     * 类注解的值(布局id)
     */
    public int value;
    public String packageName;
    /**
     * key为id，也就是成员变量注解的值，value为对应的成员变量
     */
    public Map<Integer, VariableElement> mInjectElements = new HashMap<>();
    
    public Map<Integer, ExecutableElement> mInjectMethods = new HashMap<>();
    
    /**
     * 采用类名方式不能被混淆(否则编译阶段跟运行阶段，该字符串会不一样)，或者采用字符串方式
     */
    public static final String PROXY = "TA";
    public static final String ClassSuffix = "_" + PROXY;
    
    public String getProxyClassFullName() {
        return typeElement.getQualifiedName().toString() + ClassSuffix;
    }
    
    public String getClassName() {
        return typeElement.getSimpleName().toString() + ClassSuffix;
    }
    
    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("//自动生成的注解类，勿动!!!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import example.tb.com.module_api.*;\n");
        builder.append("import android.support.annotation.Keep;\n");
        builder.append("import android.view.View;\n");
        builder.append("import " + typeElement.getQualifiedName() + ";\n");
        builder.append('\n');
        
        builder.append("@Keep").append("\n");//禁止混淆，否则反射的时候找不到该类
        builder.append("public class ").append(getClassName());
        builder.append(" {\n");
        
        generateMethod(builder);
        
        builder.append("}\n");
        return builder.toString();
    }
    
    private void generateMethod(StringBuilder builder) {
        builder.append("    public " + getClassName() + "(final " + typeElement.getSimpleName() + " host, View object) {\n");
        
        if (value > 0) {
            builder.append("        host.setContentView(" + value + ");\n");
        }
        for (int id : mInjectElements.keySet()) {
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            String type = variableElement.asType().toString();
            
            //这里object如果不为空，则可以传入view等对象
            builder.append("        host." + name).append(" = ");
            builder.append("(" + type + ")object.findViewById(" + id + ");\n");
        }
        
        for (int id : mInjectMethods.keySet()) {
            ExecutableElement executableElement = mInjectMethods.get(id);
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            builder.append("        host." + name + ".setOnClickListener(new View.OnClickListener(){\n");
            builder.append("            @Override\n");
            builder.append("            public void onClick(View v) {\n");
            builder.append("                host." + executableElement.getSimpleName().toString() + "(host." + name + ");\n");
            builder.append("            }\n");
            builder.append("        });\n");
        }
        
        builder.append("    }\n");
    }
    
}
