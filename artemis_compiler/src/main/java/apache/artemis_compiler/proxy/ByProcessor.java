package apache.artemis_compiler.proxy;

import com.apache.artemis_annotation.ByView;
import com.apache.artemis_annotation.DIActivity;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * author: 01370340
 * data: 2019/6/27
 * description:
 */
@AutoService(Processor.class)
public class ByProcessor extends ABaseProcessor {

    public static final String Activity = "android.app.Activity";




    @Override
    public Class[] getgetSupportedAnnotationTypesName() {
        return new Class[]{DIActivity.class};
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(DIActivity.class);
        if (elements != null){
            //判断注解的节点是否为Activity
            TypeElement activityTypeElement = getmElementUtils().getTypeElement(Activity);
            for (Element element : elements) {
                //类
                TypeMirror typeMirror =  element.asType();

                //判断注解的节点是否为Activity
                if (getTypes().isSubtype(typeMirror, activityTypeElement.asType())){
                    TypeElement typeElement = (TypeElement) element;

                    //MainActivity activity
                    ParameterSpec parameterSpec = ParameterSpec.builder(ClassName.get(typeMirror), "activity").build();

                    //public static void findById
                    MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("findViewById")
                            .addModifiers(Modifier.PUBLIC,Modifier.STATIC).returns(TypeName.VOID)
                            .addParameter(parameterSpec);

                    //获取所有用 ByView 注解的成员变量元素
                    Set<? extends Element> byViewElements = roundEnvironment.getElementsAnnotatedWith(ByView.class);
                    for (Element byViewElement : byViewElements) {

                        ByView byView = byViewElement.getAnnotation(ByView.class);
                        methodBuilder.addStatement(String.format("activity.%s = (%s)activity.findViewById(%s)", byViewElement.getSimpleName(),
                                ClassName.get(byViewElement.asType()).toString(), byView.value()));
                    }

                    String className = element.getSimpleName() +"$$Proxy";
                    //定义类  public final class 类名+ $$Proxy
                    TypeSpec typeSpec = TypeSpec.classBuilder(className).addModifiers(Modifier.PUBLIC,Modifier.FINAL)
                            .addMethod(methodBuilder.build())
                            .build();

                    JavaFile javaFile = JavaFile.builder(getPackageName(typeElement), typeSpec).build();
                    try {
                        javaFile.writeTo(getFile());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        return true;
    }


//    private String getPackageName(Element typeElement){
//        return getmElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
//    }

}
