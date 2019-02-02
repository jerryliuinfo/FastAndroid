package apache.artemis_compiler;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

public class ProxyInfo {
    //代表类
    private TypeElement typeElement;

    private int value;

    private String pkgName;
    /**
     * key为id，也就是成员变量注解的值，value为对应的成员变量
     */
    private Map<Integer,VariableElement> injectElements = new HashMap<>();


    public TypeElement getTypeElement() {
        return typeElement;
    }

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Map<Integer, VariableElement> getInjectElements() {
        return injectElements;
    }

    public void setInjectElements(Map<Integer, VariableElement> injectElements) {
        this.injectElements = injectElements;
    }

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

    public String packageName;

    public String generateJavaCode(){
        StringBuilder builder = new StringBuilder();
        builder.append("//自动生成的注解类，勿动!!!\n");
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import example.tb.com.module_api.*;\n");
        builder.append("import android.support.annotation.Keep;\n");
        builder.append('\n');

        builder.append("@Keep").append("\n");//禁止混淆，否则被当作无用代码优化掉，反射的时候找不到该类
        builder.append("public class ").append(getClassName()).append(" implements " + ProxyInfo.PROXY + "<" + typeElement.getQualifiedName() + ">");
        builder.append(" {\n");

        generateMethod(builder);

        builder.append('\n');

        builder.append("}\n");
        return builder.toString();
    }


    private void generateMethod(StringBuilder builder) {
        builder.append("@Override\n ");
        builder.append("public void inject(" + typeElement.getQualifiedName() + " host, Object object ) {\n");

        if (value > 0) {
            builder.append("host.setContentView(" + value + ");\n");
        }
        for (int id : getInjectElements().keySet()) {
            VariableElement variableElement = getInjectElements().get(id);
            String name = variableElement.getSimpleName().toString();
            String type = variableElement.asType().toString();

            //这里object如果不为空，则可以传入view等对象
            builder.append(" if(object instanceof android.view.View)");
            builder.append("\n{\n");
            builder.append("host." + name).append(" = ");
            builder.append("(" + type + ")((android.view.View)object).findViewById(" + id + ");");
            builder.append("\n}\n").append("else").append("\n{\n");
            builder.append("host." + name).append(" = ");
            builder.append("(" + type + ")host.findViewById(" + id + ");");
            builder.append("\n}\n");
        }

        builder.append("  }\n");
    }

}
