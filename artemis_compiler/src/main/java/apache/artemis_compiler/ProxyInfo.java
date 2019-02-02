package apache.artemis_compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static javax.lang.model.element.Modifier.PUBLIC;
public class ProxyInfo {
    /**
     * 采用类名方式不能被混淆(否则编译阶段跟运行阶段，该字符串会不一样)，或者采用字符串方式
     */
    public static final String PROXY = "TA";
    public static final String ClassSuffix = "_" + PROXY;



    //代表类
    private TypeElement typeElement;
    /**
     * 类注解的值(布局id)
     */
    private int value;

    private String pkgName;
    /**
     * key为id，也就是成员变量注解的值，value为对应的成员变量
     */
    private Map<Integer, VariableElement> mInjectElements = new HashMap<>();

    private Map<Integer, ExecutableElement> mInjectMethods = new HashMap<>();


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
        return mInjectElements;
    }

    public void setInjectElements(Map<Integer, VariableElement> injectElements) {
        mInjectElements = injectElements;
    }

    public Map<Integer, ExecutableElement> getInjectMethods() {
        return mInjectMethods;
    }

    public void setInjectMethods(Map<Integer, ExecutableElement> injectMethods) {
        mInjectMethods = injectMethods;
    }

    public String getProxyClassFullName() {
        return typeElement.getQualifiedName().toString() + ClassSuffix;
    }

    public String getClassName() {
        return typeElement.getSimpleName().toString() + ClassSuffix;
    }

    public String packageName;

    public String generateJavaCode(){
        ClassName viewClass = ClassName.get("android.view", "View");
        ClassName keepClass = ClassName.get("android.support.annotation", "Keep");
        ClassName clickClass = ClassName.get("android.view", "View.OnClickListener");
        String qualifiedName = typeElement.getQualifiedName().toString();
        String simpleName = typeElement.getSimpleName().toString();
        ClassName typeClass = ClassName.get(qualifiedName.replace("."+ simpleName, ""), simpleName);
        MethodSpec.Builder builder = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC)
                .addParameter(typeClass,"host",Modifier.PUBLIC)
                .addParameter(typeClass,"object",Modifier.PUBLIC);

        if (value > 0){
            builder.addStatement("host.setContentView($L)", value);
        }

        //findViewById
        for (Integer id : mInjectElements.keySet()) {
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            String type = variableElement.asType().toString();
            //这里object如果不为空，则可以传入view等对象
            builder.addStatement("host.$L=($L)object.findViewById($L)", name, type, id);

        }
        for (int id : mInjectMethods.keySet()) {
            /*host.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    host.clickTest(host.tv);
                }
            });*/
            ExecutableElement executableElement = mInjectMethods.get(id);
            VariableElement variableElement = mInjectElements.get(id);
            String name = variableElement.getSimpleName().toString();
            TypeSpec comparator = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(clickClass)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                            .addAnnotation(Override.class)
                            .addModifiers(PUBLIC)
                            .addParameter(viewClass, "view")
                            .addStatement("host.$L(host.$L)", executableElement.getSimpleName().toString(), name)
                            .returns(void.class)
                            .build())
                    .build();
            builder.addStatement("host.$L.setOnClickListener($L)", name, comparator);
        }
        MethodSpec methodSpec = builder.build();
        TypeSpec typeSpec = TypeSpec.classBuilder(getClassName())
                .addModifiers(PUBLIC)
                .addAnnotation(keepClass)
                .addMethod(methodSpec)
                .build();
        JavaFile javaFile = JavaFile.builder(packageName, typeSpec).build();
        return javaFile.toString();
    }



}
